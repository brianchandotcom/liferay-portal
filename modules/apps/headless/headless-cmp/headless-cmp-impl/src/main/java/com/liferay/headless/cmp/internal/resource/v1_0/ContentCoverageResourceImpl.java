/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.cmp.dto.v1_0.ContentCoverage;
import com.liferay.headless.cmp.dto.v1_0.ContentCoverageEntry;
import com.liferay.headless.cmp.dto.v1_0.FunnelStage;
import com.liferay.headless.cmp.dto.v1_0.Persona;
import com.liferay.headless.cmp.resource.v1_0.ContentCoverageResource;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregationResult;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.site.cms.site.initializer.constants.CMSWorkflowConstants;
import com.liferay.site.cms.site.initializer.util.AssetTagUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Fábio Alves
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-coverage.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentCoverageResource.class
)
public class ContentCoverageResourceImpl
	extends BaseContentCoverageResourceImpl {

	@Override
	public ContentCoverage getProjectContentCoverage(Long projectId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-58677")) {

			throw new UnsupportedOperationException();
		}

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			projectId);

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getCategories(
				objectEntry.getModelClassName(), projectId);

		ContentCoverage contentCoverage = new ContentCoverage();

		List<AssetCategory> funnelStageCategories = _getAssetCategories(
			assetCategories, "L_CMP_FUNNEL_STAGE");

		contentCoverage.setFunnelStages(
			() -> _getFunnelStages(
				funnelStageCategories,
				contextAcceptLanguage.getPreferredLocale()
			).toArray(
				new FunnelStage[0]
			));

		List<AssetCategory> personaCategories = _getAssetCategories(
			assetCategories, "L_CMP_PERSONAS");

		contentCoverage.setPersonas(
			() -> _getPersonas(
				personaCategories, contextAcceptLanguage.getPreferredLocale()
			).toArray(
				new Persona[0]
			));

		Set<String> assetTagNames = AssetTagUtil.getRelatedAssetTagNames(
			_assetTagLocalService, _objectDefinitionLocalService, objectEntry,
			_objectEntryLocalService,
			_objectRelationshipLocalService.
				fetchObjectRelationshipByExternalReferenceCode(
					"L_CMP_PROJECT_TO_L_CMP_TASKS",
					objectEntry.getObjectDefinitionId()));

		if (assetTagNames.isEmpty()) {
			contentCoverage.setContentCoverageEntries(
				() -> new ContentCoverageEntry[0]);
			contentCoverage.setTotalAssetCount(() -> 0L);

			return contentCoverage;
		}

		SearchResponse searchResponse = _search(
			assetTagNames, funnelStageCategories, personaCategories);

		contentCoverage.setContentCoverageEntries(
			() -> _toContentCoverageEntries(searchResponse));
		contentCoverage.setTotalAssetCount(searchResponse::getCount);

		return contentCoverage;
	}

	private FiltersAggregation _createFiltersAggregation(
		List<AssetCategory> assetCategories, String name) {

		FiltersAggregation filtersAggregation = _aggregations.filters(
			name, Field.ASSET_INTERNAL_CATEGORY_IDS);

		for (AssetCategory assetCategory : assetCategories) {
			filtersAggregation.addKeyedQuery(
				String.valueOf(assetCategory.getCategoryId()),
				QueriesUtil.term(
					Field.ASSET_INTERNAL_CATEGORY_IDS,
					assetCategory.getCategoryId()));
		}

		filtersAggregation.setOtherBucket(true);
		filtersAggregation.setOtherBucketKey("-1");

		return filtersAggregation;
	}

	private List<AssetCategory> _getAssetCategories(
		List<AssetCategory> assetCategories,
		String vocabularyExternalReferenceCode) {

		return ListUtil.filter(
			assetCategories,
			assetCategory -> StringUtil.startsWith(
				assetCategory.getExternalReferenceCode(),
				vocabularyExternalReferenceCode));
	}

	private List<FunnelStage> _getFunnelStages(
		List<AssetCategory> assetCategories, Locale locale) {

		List<FunnelStage> funnelStages = new ArrayList<>();

		for (AssetCategory assetCategory : assetCategories) {
			FunnelStage funnelStage = new FunnelStage();

			funnelStage.setDescription(
				() -> assetCategory.getDescription(locale));
			funnelStage.setExternalReferenceCode(
				assetCategory::getExternalReferenceCode);
			funnelStage.setId(
				() -> String.valueOf(assetCategory.getCategoryId()));
			funnelStage.setName(() -> assetCategory.getTitle(locale));

			funnelStages.add(funnelStage);
		}

		return funnelStages;
	}

	private List<Persona> _getPersonas(
		List<AssetCategory> assetCategories, Locale locale) {

		List<Persona> personas = new ArrayList<>();

		for (AssetCategory assetCategory : assetCategories) {
			Persona persona = new Persona();

			persona.setDescription(() -> assetCategory.getDescription(locale));
			persona.setExternalReferenceCode(
				assetCategory::getExternalReferenceCode);
			persona.setId(() -> String.valueOf(assetCategory.getCategoryId()));
			persona.setName(() -> assetCategory.getTitle(locale));

			personas.add(persona);
		}

		return personas;
	}

	private SearchResponse _search(
		Set<String> assetTagNames, List<AssetCategory> funnelStageCategories,
		List<AssetCategory> personaCategories) {

		TermsQuery assetTagNamesQuery = QueriesUtil.terms(
			"assetTagNames.lowercase");

		for (String assetTagName : assetTagNames) {
			assetTagNamesQuery.addValue(assetTagName);
		}

		TermsQuery cmsSectionQuery = QueriesUtil.terms("cms_section");

		cmsSectionQuery.addValues("contents", "files");

		TermsQuery statusQuery = QueriesUtil.terms(Field.STATUS);

		statusQuery.addValues(
			(Object[])ArrayUtil.toStringArray(CMSWorkflowConstants.STATUSES));

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			assetTagNamesQuery, cmsSectionQuery, statusQuery,
			QueriesUtil.term("rootDescendantNode", false));

		FiltersAggregation filtersAggregation = _createFiltersAggregation(
			personaCategories, _PERSONAS_AGGREGATION_NAME);

		filtersAggregation.addChildAggregation(
			_createFiltersAggregation(
				funnelStageCategories, _FUNNEL_STAGES_AGGREGATION_NAME));

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				contextCompany.getCompanyId()
			).emptySearchEnabled(
				true
			).query(
				booleanQuery
			).size(
				0
			);

		searchRequestBuilder.addAggregation(filtersAggregation);

		return _searcher.search(searchRequestBuilder.build());
	}

	private ContentCoverageEntry[] _toContentCoverageEntries(
		SearchResponse searchResponse) {

		FiltersAggregationResult personasAggregationResult =
			(FiltersAggregationResult)searchResponse.getAggregationResult(
				_PERSONAS_AGGREGATION_NAME);

		if (personasAggregationResult == null) {
			return new ContentCoverageEntry[0];
		}

		List<ContentCoverageEntry> contentCoverageEntries = new ArrayList<>();

		for (Bucket personaBucket : personasAggregationResult.getBuckets()) {
			FiltersAggregationResult funnelStagesAggregationResult =
				(FiltersAggregationResult)
					personaBucket.getChildAggregationResult(
						_FUNNEL_STAGES_AGGREGATION_NAME);

			if (funnelStagesAggregationResult == null) {
				continue;
			}

			for (Bucket funnelStageBucket :
					funnelStagesAggregationResult.getBuckets()) {

				if (funnelStageBucket.getDocCount() == 0) {
					continue;
				}

				ContentCoverageEntry contentCoverageEntry =
					new ContentCoverageEntry();

				contentCoverageEntry.setFunnelStageId(
					funnelStageBucket::getKey);
				contentCoverageEntry.setPersonaId(personaBucket::getKey);
				contentCoverageEntry.setTotalCount(
					funnelStageBucket::getDocCount);

				contentCoverageEntries.add(contentCoverageEntry);
			}
		}

		return contentCoverageEntries.toArray(new ContentCoverageEntry[0]);
	}

	private static final String _FUNNEL_STAGES_AGGREGATION_NAME =
		"funnelStages";

	private static final String _PERSONAS_AGGREGATION_NAME = "personas";

	@Reference
	private Aggregations _aggregations;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}