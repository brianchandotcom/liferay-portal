/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.rest.internal.resource.v1_0;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.Facet;
import com.liferay.portal.search.rest.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.dto.v1_0.SearchResponse;
import com.liferay.portal.search.rest.internal.facet.FacetRequestContributor;
import com.liferay.portal.search.rest.internal.facet.FacetResponseContributor;
import com.liferay.portal.search.rest.resource.v1_0.SearchResponseResource;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/search-response.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResponseResource.class
)
public class SearchResponseResourceImpl extends BaseSearchResponseResourceImpl {

	@Override
	public SearchResponse postSearch(
			Boolean basicFacetSelection, String[] entryClassNames,
			Boolean explain, Long[] groupIds, Boolean includeAssetSearchSummary,
			Boolean includeAssetTitle, Boolean includeRequest,
			Boolean includeResponse, String keywords, String[] resultFields,
			Long scopeGroupId, Pagination pagination, Sort[] sorts,
			SearchRequestBody searchRequestBody)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-179669")) {
			return null;
		}

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				contextCompany.getCompanyId()
			).from(
				pagination.getStartPosition()
			).size(
				pagination.getPageSize()
			).withSearchContext(
				searchContext -> _addSearchContextAttributes(
					keywords, groupIds, scopeGroupId,
					searchRequestBody.getSearchContextAttributes(),
					searchContext, sorts)
			);

		if (basicFacetSelection != null) {
			searchRequestBuilder.basicFacetSelection(basicFacetSelection);
		}

		if (!ArrayUtil.isEmpty(entryClassNames)) {
			searchRequestBuilder.entryClassNames(entryClassNames);
			searchRequestBuilder.modelIndexerClassNames(entryClassNames);
		}

		if (GetterUtil.getBoolean(explain)) {
			searchRequestBuilder.explain(explain);
		}

		if (!ArrayUtil.isEmpty(resultFields)) {
			searchRequestBuilder.fields(resultFields);
			searchRequestBuilder.fetchSourceIncludes(resultFields);
		}

		if (!Validator.isBlank(keywords)) {
			searchRequestBuilder.queryString(keywords);
		}

		if (ArrayUtil.isNotEmpty(searchRequestBody.getFacets())) {
			_facetRequestContributor.contribute(
				searchRequestBody.getFacets(), searchRequestBuilder);
		}

		return _toSearchResponse(
			GetterUtil.getBoolean(explain), searchRequestBody.getFacets(),
			GetterUtil.getBoolean(includeAssetSearchSummary),
			GetterUtil.getBoolean(includeAssetTitle),
			GetterUtil.getBoolean(includeRequest),
			GetterUtil.getBoolean(includeResponse), Arrays.asList(resultFields),
			_searcher.search(searchRequestBuilder.build()));
	}

	private void _addSearchContextAttributes(
		String keywords, Long[] groupIds, Long scopeGroupId,
		Map<String, Object> searchContextAttributes,
		SearchContext searchContext, Sort[] sorts) {

		MapUtil.isNotEmptyForEach(
			searchContextAttributes,
			(key, value) -> {
				if ((value != null) && (value instanceof Serializable)) {
					searchContext.setAttribute(key, (Serializable)value);
				}
			});

		if (searchContext.getAttribute("search.experiences.ip.address") ==
				null) {

			searchContext.setAttribute(
				"search.experiences.ip.address",
				contextHttpServletRequest.getRemoteAddr());
		}

		if ((searchContext.getAttribute("search.experiences.scope.group.id") ==
				null) &&
			(scopeGroupId != null)) {

			searchContext.setAttribute(
				"search.experiences.scope.group.id", scopeGroupId);
		}

		if (ArrayUtil.isNotEmpty(groupIds)) {
			searchContext.setGroupIds(ArrayUtils.toPrimitive(groupIds));
		}

		searchContext.setKeywords(keywords);
		searchContext.setLocale(contextAcceptLanguage.getPreferredLocale());

		if (ArrayUtil.isEmpty(sorts)) {
			searchContext.setSorts(sorts);
		}

		searchContext.setTimeZone(contextUser.getTimeZone());
		searchContext.setUserId(contextUser.getUserId());
	}

	private JSONObject _createJSONObject(String string) {
		try {
			return _jsonFactory.createJSONObject(string);
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return null;
	}

	private AssetRenderer<?> _getAssetRenderer(
		Map<String, Field> documentFields) {

		try {
			Field entryClassNameField = documentFields.get(
				com.liferay.portal.kernel.search.Field.ENTRY_CLASS_NAME);

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						GetterUtil.getString(entryClassNameField.getValue()));

			if (assetRendererFactory == null) {
				return null;
			}

			Field entryClassPKField = documentFields.get(
				com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK);

			return assetRendererFactory.getAssetRenderer(
				GetterUtil.getLong(entryClassPKField.getValue()));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private int _getPage(int from, int size) {
		if ((from == 0) || (size == 0)) {
			return 1;
		}

		return (int)Math.ceil((double)(from + 1) / size);
	}

	private void _includeAssetFields(
		Document document, boolean includeAssetSearchSummary,
		boolean includeAssetTitle, JSONObject jsonObject, Locale locale) {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(
			document.getFields());

		if (assetRenderer == null) {
			return;
		}

		if (includeAssetSearchSummary) {
			jsonObject.put(
				"assetSearchSummary", assetRenderer.getSearchSummary(locale));
		}

		if (includeAssetTitle) {
			jsonObject.put("assetTitle", assetRenderer.getTitle(locale));
		}
	}

	private void _includeDocumentFields(
		Document document, JSONObject jsonObject,
		List<String> resultFieldNames) {

		MapUtil.isNotEmptyForEach(
			document.getFields(),
			(name, field) -> {
				if (ListUtil.isNotEmpty(resultFieldNames) &&
					!resultFieldNames.contains(name)) {

					return;
				}

				List<Object> valuesList = field.getValues();

				if (valuesList.size() == 1) {
					jsonObject.put(name, valuesList.get(0));
				}
				else {
					jsonObject.put(name, valuesList.toArray());
				}
			});
	}

	private void _includeSourceFields(
		JSONObject jsonObject, List<String> resultFieldNames,
		SearchHit searchHit) {

		MapUtil.isNotEmptyForEach(
			searchHit.getSourcesMap(),
			(name, obj) -> {
				if (ListUtil.isNotEmpty(resultFieldNames) &&
					!resultFieldNames.contains(name)) {

					return;
				}

				jsonObject.put(name, obj);
			});
	}

	private Map<String, Object> _toAggregationResults(
		Map<String, AggregationResult> aggregationResultsMap) {

		if (aggregationResultsMap.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<String, Object> aggregationResults = new HashMap<>();

		for (Map.Entry<String, AggregationResult> entry :
				aggregationResultsMap.entrySet()) {

			aggregationResults.put(entry.getKey(), (Object)entry.getValue());
		}

		return aggregationResults;
	}

	private Object[] _toDocuments(
			boolean explain, boolean includeAssetSearchSummary,
			boolean includeAssetTitle, Locale locale,
			List<String> resultFieldNames, SearchHits searchHits)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			if (explain && _portal.isCompanyAdmin(contextUser)) {
				jsonObject.put("explain", searchHit.getExplanation());
			}

			jsonObject.put(
				"id", searchHit.getId()
			).put(
				"score", searchHit.getScore()
			);

			Document document = searchHit.getDocument();

			if (includeAssetSearchSummary || includeAssetTitle) {
				_includeAssetFields(
					document, includeAssetSearchSummary, includeAssetTitle,
					jsonObject, locale);
			}

			_includeDocumentFields(document, jsonObject, resultFieldNames);

			_includeSourceFields(jsonObject, resultFieldNames, searchHit);

			jsonArray.put(jsonObject);
		}

		return JSONUtil.toObjectArray(jsonArray);
	}

	private SearchResponse _toSearchResponse(
			boolean explain, Facet[] facets, boolean includeAssetSearchSummary,
			boolean includeAssetTitle, boolean includeRequest,
			boolean includeResponse, List<String> resultFields,
			com.liferay.portal.search.searcher.SearchResponse
				portalSearchResponse)
		throws Exception {

		SearchRequest searchRequest = portalSearchResponse.getRequest();

		SearchHits searchHits = portalSearchResponse.getSearchHits();

		SearchResponse searchResponse = SearchResponse.unsafeToDTO(
			String.valueOf(
				new SearchResponse() {
					{
						List<SearchHit> searchHitsList =
							searchHits.getSearchHits();

						if (searchHitsList.isEmpty()) {
							maxScore = null;
						}
						else {
							maxScore = searchHits.getMaxScore();
						}

						page = _getPage(
							searchRequest.getFrom(), searchRequest.getSize());
						pageSize = searchRequest.getSize();

						if (includeRequest) {
							request = _createJSONObject(
								portalSearchResponse.getRequestString());
						}

						if (includeResponse) {
							response = _createJSONObject(
								portalSearchResponse.getResponseString());
						}

						totalHits = searchHits.getTotalHits();
					}
				}));

		Map<String, Object> aggregationResults = _toAggregationResults(
			portalSearchResponse.getAggregationResultsMap());

		if (MapUtil.isNotEmpty(aggregationResults)) {
			searchResponse.setAggregationResults(aggregationResults);
		}

		searchResponse.setDocuments(
			_toDocuments(
				explain, includeAssetSearchSummary, includeAssetTitle,
				contextAcceptLanguage.getPreferredLocale(), resultFields,
				searchHits));

		_facetResponseContributor.contribute(
			contextCompany.getCompanyId(), facets,
			contextAcceptLanguage.getPreferredLocale(), portalSearchResponse,
			searchResponse, contextUser.getUserId());

		return searchResponse;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResponseResourceImpl.class);

	@Reference
	private FacetRequestContributor _facetRequestContributor;

	@Reference
	private FacetResponseContributor _facetResponseContributor;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}