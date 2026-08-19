/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.internal.resource.v1_0;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.headless.cms.dto.v1_0.SimilarityCluster;
import com.liferay.headless.cms.dto.v1_0.SimilarityClusterAsset;
import com.liferay.headless.cms.internal.similarity.SimilarityClusterUtil;
import com.liferay.headless.cms.resource.v1_0.SimilarityClusterResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Mikel Lorza
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/similarity-cluster.properties",
	scope = ServiceScope.PROTOTYPE, service = SimilarityClusterResource.class
)
public class SimilarityClusterResourceImpl
	extends BaseSimilarityClusterResourceImpl {

	@Override
	public Page<SimilarityCluster> getSimilarityClustersPage(
			Long assetLibraryId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-82226")) {

			return Page.of(new ArrayList<>(), pagination, 0);
		}

		List<ObjectDefinition> objectDefinitions = _getCMSObjectDefinitions();

		Long[] groupIds = _getGroupIds(assetLibraryId);

		if (ArrayUtil.isEmpty(groupIds) || objectDefinitions.isEmpty()) {
			return Page.of(new ArrayList<>(), pagination, 0);
		}

		String[] entryClassNames = ArrayUtil.toStringArray(
			ListUtil.toList(objectDefinitions, ObjectDefinition::getClassName));
		String languageId = contextAcceptLanguage.getPreferredLanguageId();

		List<String> sharedSimilarityKeys = _searchSharedSimilarityKeys(
			entryClassNames, groupIds, languageId);

		Map<Long, List<Long>> objectEntryIdsByClusterId =
			SimilarityClusterUtil.getClusters(
				_searchClusteredDocuments(
					entryClassNames, groupIds, sharedSimilarityKeys),
				new HashSet<>(sharedSimilarityKeys));

		long totalCount = 0;

		for (List<Long> objectEntryIds : objectEntryIdsByClusterId.values()) {
			totalCount += objectEntryIds.size();
		}

		Map<Long, ObjectDefinition> objectDefinitionsById = new HashMap<>();

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			objectDefinitionsById.put(
				objectDefinition.getObjectDefinitionId(), objectDefinition);
		}

		return Page.of(
			_getSimilarityClusters(
				languageId, objectDefinitionsById, objectEntryIdsByClusterId,
				pagination),
			pagination, totalCount);
	}

	private List<ObjectDefinition> _getCMSObjectDefinitions() throws Exception {
		return _objectDefinitionService.getCMSObjectDefinitions(
			contextCompany.getCompanyId(),
			new String[] {
				ObjectFolderConstants.
					EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
				ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
			});
	}

	private Long[] _getGroupIds(Long assetLibraryId) {
		List<Long> depotEntryGroupIds =
			_depotEntryService.getDepotEntryGroupIds(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				DepotConstants.TYPE_SPACE);

		if (assetLibraryId == null) {
			return depotEntryGroupIds.toArray(new Long[0]);
		}

		Long groupId = GroupUtil.getDepotGroupId(
			String.valueOf(assetLibraryId), contextCompany.getCompanyId(),
			_depotEntryLocalService, groupLocalService);

		if ((groupId == null) || !depotEntryGroupIds.contains(groupId)) {
			return new Long[0];
		}

		return new Long[] {groupId};
	}

	private Consumer<SearchContext> _getSearchContextConsumer(Long[] groupIds) {
		long[] scopedGroupIds = ArrayUtil.toArray(groupIds);

		return searchContext -> {
			searchContext.setAttribute(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED);
			searchContext.setGroupIds(scopedGroupIds);

			searchContext.setUserId(contextUser.getUserId());
		};
	}

	private List<SimilarityCluster> _getSimilarityClusters(
			String languageId,
			Map<Long, ObjectDefinition> objectDefinitionsById,
			Map<Long, List<Long>> objectEntryIdsByClusterId,
			Pagination pagination)
		throws Exception {

		List<SimilarityCluster> similarityClusters = new ArrayList<>();

		int endPosition = -1;
		int startPosition = -1;

		if (pagination != null) {
			endPosition = pagination.getEndPosition();
			startPosition = pagination.getStartPosition();
		}

		int position = 0;

		for (List<Long> objectEntryIds : objectEntryIdsByClusterId.values()) {
			int clusterStartPosition = position;

			position += objectEntryIds.size();

			List<Long> pageObjectEntryIds = objectEntryIds;

			if ((endPosition >= 0) && (startPosition >= 0)) {
				if (position <= startPosition) {
					continue;
				}

				if (clusterStartPosition >= endPosition) {
					break;
				}

				pageObjectEntryIds = objectEntryIds.subList(
					Math.max(startPosition - clusterStartPosition, 0),
					Math.min(
						endPosition - clusterStartPosition,
						objectEntryIds.size()));
			}

			similarityClusters.add(
				_toSimilarityCluster(
					languageId, objectDefinitionsById, pageObjectEntryIds,
					objectEntryIds.size()));
		}

		return similarityClusters;
	}

	private List<Document> _searchClusteredDocuments(
		String[] entryClassNames, Long[] groupIds,
		List<String> sharedSimilarityKeys) {

		List<Document> documents = new ArrayList<>();

		if (sharedSimilarityKeys.isEmpty()) {
			return documents;
		}

		TermsQuery termsQuery = QueriesUtil.terms(
			SimilarityClusterUtil.FIELD_TEXT_SIMILARITY_KEYS);

		termsQuery.addValues(sharedSimilarityKeys.toArray());

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).addComplexQueryPart(
				_complexQueryPartBuilderFactory.builder(
				).occur(
					"must"
				).query(
					termsQuery
				).build()
			).companyId(
				contextCompany.getCompanyId()
			).emptySearchEnabled(
				true
			).entryClassNames(
				entryClassNames
			).fetchSourceIncludes(
				new String[] {
					"objectEntryId",
					SimilarityClusterUtil.FIELD_TEXT_SIMILARITY_KEYS
				}
			).size(
				_MAX_CLUSTERED_DOCUMENTS
			).withSearchContext(
				_getSearchContextConsumer(groupIds)
			).build());

		SearchHits searchHits = searchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			documents.add(searchHit.getDocument());
		}

		return documents;
	}

	private List<String> _searchSharedSimilarityKeys(
		String[] entryClassNames, Long[] groupIds, String languageId) {

		List<String> sharedSimilarityKeys = new ArrayList<>();

		TermsAggregation termsAggregation = _aggregations.terms(
			_SIMILARITY_KEYS_AGGREGATION_NAME,
			SimilarityClusterUtil.FIELD_TEXT_SIMILARITY_KEYS);

		termsAggregation.setMinDocCount(2);
		termsAggregation.setIncludeExcludeClause(
			new IncludeExcludeClauseImpl(languageId + "_.*", null));
		termsAggregation.setSize(_MAX_SIMILARITY_KEYS);

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
			).addAggregation(
				termsAggregation
			).companyId(
				contextCompany.getCompanyId()
			).emptySearchEnabled(
				true
			).entryClassNames(
				entryClassNames
			).size(
				0
			).withSearchContext(
				_getSearchContextConsumer(groupIds)
			).build());

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)searchResponse.getAggregationResult(
				_SIMILARITY_KEYS_AGGREGATION_NAME);

		if (termsAggregationResult == null) {
			return sharedSimilarityKeys;
		}

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			sharedSimilarityKeys.add(bucket.getKey());
		}

		return sharedSimilarityKeys;
	}

	private SimilarityCluster _toSimilarityCluster(
			String languageId,
			Map<Long, ObjectDefinition> objectDefinitionsById,
			List<Long> objectEntryIds, int size)
		throws Exception {

		SimilarityCluster similarityCluster = new SimilarityCluster();

		SimilarityClusterAsset[] similarityClusterAssets = transformToArray(
			objectEntryIds,
			objectEntryId -> {
				ObjectEntry objectEntry =
					_objectEntryLocalService.fetchObjectEntry(objectEntryId);

				if (objectEntry == null) {
					return null;
				}

				SimilarityClusterAsset similarityClusterAsset =
					new SimilarityClusterAsset();

				similarityClusterAsset.setDateModified(
					objectEntry::getModifiedDate);
				similarityClusterAsset.setId(() -> objectEntryId);
				similarityClusterAsset.setTitle(
					() -> objectEntry.getTitleValue(languageId, true));

				ObjectDefinition objectDefinition = objectDefinitionsById.get(
					objectEntry.getObjectDefinitionId());

				if (objectDefinition != null) {
					similarityClusterAsset.setContentType(
						() -> objectDefinition.getLabel(languageId, true));
				}

				return similarityClusterAsset;
			},
			SimilarityClusterAsset.class);

		similarityCluster.setSimilarityClusterAssets(
			() -> similarityClusterAssets);

		similarityCluster.setSize(() -> size);

		return similarityCluster;
	}

	private static final int _MAX_CLUSTERED_DOCUMENTS = 10000;

	private static final int _MAX_SIMILARITY_KEYS = 10000;

	private static final String _SIMILARITY_KEYS_AGGREGATION_NAME =
		"similarityKeys";

	@Reference
	private Aggregations _aggregations;

	@Reference
	private ComplexQueryPartBuilderFactory _complexQueryPartBuilderFactory;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private static class IncludeExcludeClauseImpl
		implements IncludeExcludeClause {

		public IncludeExcludeClauseImpl(
			String includeRegex, String excludeRegex) {

			_includeRegex = includeRegex;
			_excludeRegex = excludeRegex;
		}

		@Override
		public String[] getExcludedValues() {
			return null;
		}

		@Override
		public String getExcludeRegex() {
			return _excludeRegex;
		}

		@Override
		public String[] getIncludedValues() {
			return null;
		}

		@Override
		public String getIncludeRegex() {
			return _includeRegex;
		}

		private final String _excludeRegex;
		private final String _includeRegex;

	}

}