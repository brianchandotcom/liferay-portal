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
import com.liferay.headless.cms.dto.v1_0.SimilarityClusterResult;
import com.liferay.headless.cms.internal.similarity.SimilarityClusterUtil;
import com.liferay.headless.cms.resource.v1_0.SimilarityClusterResultResource;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryLocalService;
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
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
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
	properties = "OSGI-INF/liferay/rest/v1_0/similarity-cluster-result.properties",
	scope = ServiceScope.PROTOTYPE,
	service = SimilarityClusterResultResource.class
)
public class SimilarityClusterResultResourceImpl
	extends BaseSimilarityClusterResultResourceImpl {

	@Override
	public SimilarityClusterResult getSimilarityCluster(
			Long assetLibraryId, Pagination pagination)
		throws Exception {

		List<ObjectDefinition> objectDefinitions = _getCMSObjectDefinitions();

		Long[] groupIds = _getGroupIds(assetLibraryId);

		if (ArrayUtil.isEmpty(groupIds) || objectDefinitions.isEmpty()) {
			return _toSimilarityClusterResult(new ArrayList<>(), 0);
		}

		String[] entryClassNames = ArrayUtil.toStringArray(
			ListUtil.toList(objectDefinitions, ObjectDefinition::getClassName));
		String languageId = contextAcceptLanguage.getPreferredLanguageId();

		List<String> sharedBands = _searchSharedBands(
			entryClassNames, groupIds, languageId);

		Map<Long, List<Long>> objectEntryIdsMap =
			SimilarityClusterUtil.getClusters(
				_FIELD_NAME_BANDS,
				_searchClusteredDocuments(
					entryClassNames, groupIds, sharedBands),
				new HashSet<>(sharedBands));

		long totalCount = 0;

		for (List<Long> objectEntryIds : objectEntryIdsMap.values()) {
			totalCount += objectEntryIds.size();
		}

		Map<Long, ObjectDefinition> objectDefinitionsMap = new HashMap<>();

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			objectDefinitionsMap.put(
				objectDefinition.getObjectDefinitionId(), objectDefinition);
		}

		return _toSimilarityClusterResult(
			_getSimilarityClusters(
				languageId, objectDefinitionsMap, objectEntryIdsMap,
				pagination),
			totalCount);
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

			_setPermissionFilterUserId(searchContext);
		};
	}

	private List<SimilarityCluster> _getSimilarityClusters(
			String languageId, Map<Long, ObjectDefinition> objectDefinitionsMap,
			Map<Long, List<Long>> objectEntryIdsMap, Pagination pagination)
		throws Exception {

		List<SimilarityCluster> similarityClusters = new ArrayList<>();

		int endPosition = -1;
		int startPosition = -1;

		if (pagination != null) {
			endPosition = pagination.getEndPosition();
			startPosition = pagination.getStartPosition();
		}

		int position = 0;

		for (List<Long> objectEntryIds : objectEntryIdsMap.values()) {
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
					languageId, objectDefinitionsMap, pageObjectEntryIds,
					objectEntryIds.size()));
		}

		return similarityClusters;
	}

	private List<Document> _searchClusteredDocuments(
		String[] entryClassNames, Long[] groupIds, List<String> sharedBands) {

		List<Document> documents = new ArrayList<>();

		if (sharedBands.isEmpty()) {
			return documents;
		}

		TermsQuery termsQuery = QueriesUtil.terms(_FIELD_NAME_BANDS);

		termsQuery.addValues(sharedBands.toArray());

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.addComplexQueryPart(
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
				SimilarityClusterUtil.FIELD_NAME_OBJECT_ENTRY_ID,
				_FIELD_NAME_BANDS
			}
		).size(
			_MAX_CLUSTERED_ASSETS
		).withSearchContext(
			_getSearchContextConsumer(groupIds)
		);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		SearchHits searchHits = searchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			documents.add(searchHit.getDocument());
		}

		return documents;
	}

	private List<String> _searchSharedBands(
		String[] entryClassNames, Long[] groupIds, String languageId) {

		List<String> sharedBands = new ArrayList<>();

		TermsAggregation termsAggregation = _aggregations.terms(
			_BANDS_AGGREGATION_NAME, _FIELD_NAME_BANDS);

		termsAggregation.setMinDocCount(2);
		termsAggregation.setIncludeExcludeClause(
			new IncludeExcludeClauseImpl(
				SimilarityClusterUtil.getTokenPrefix(languageId) + ".*", null));
		termsAggregation.setSize(_MAX_BANDS);

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
				_BANDS_AGGREGATION_NAME);

		if (termsAggregationResult == null) {
			return sharedBands;
		}

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			sharedBands.add(bucket.getKey());
		}

		return sharedBands;
	}

	private void _setPermissionFilterUserId(SearchContext searchContext) {
		searchContext.setUserId(contextUser.getUserId());
	}

	private SimilarityCluster _toSimilarityCluster(
			String languageId, Map<Long, ObjectDefinition> objectDefinitionsMap,
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

				ObjectDefinition objectDefinition = objectDefinitionsMap.get(
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

	private SimilarityClusterResult _toSimilarityClusterResult(
		List<SimilarityCluster> similarityClusters, long totalCount) {

		SimilarityCluster[] similarityClustersArray =
			similarityClusters.toArray(new SimilarityCluster[0]);

		SimilarityClusterResult similarityClusterResult =
			new SimilarityClusterResult();

		similarityClusterResult.setSimilarityClusters(
			() -> similarityClustersArray);
		similarityClusterResult.setTotalCount(() -> totalCount);

		return similarityClusterResult;
	}

	private static final String _BANDS_AGGREGATION_NAME = "bands";

	private static final String _FIELD_NAME_BANDS = "textSimilarityBands";

	private static final int _MAX_BANDS = 10000;

	private static final int _MAX_CLUSTERED_ASSETS = 10000;

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