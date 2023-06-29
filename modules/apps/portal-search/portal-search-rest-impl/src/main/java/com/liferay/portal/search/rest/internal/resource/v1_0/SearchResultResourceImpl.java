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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.rest.dto.v1_0.FacetConfiguration;
import com.liferay.portal.search.rest.dto.v1_0.SearchRequestBody;
import com.liferay.portal.search.rest.dto.v1_0.SearchResult;
import com.liferay.portal.search.rest.internal.facet.FacetRequestContributor;
import com.liferay.portal.search.rest.internal.facet.FacetResponseProcessor;
import com.liferay.portal.search.rest.internal.odata.entity.v1_0.SearchResultEntityModel;
import com.liferay.portal.search.rest.internal.pagination.SearchPage;
import com.liferay.portal.search.rest.resource.v1_0.SearchResultResource;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/search-result.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResultResource.class
)
public class SearchResultResourceImpl extends BaseSearchResultResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _searchResultEntityModel;
	}

	@Override
	public Page<SearchResult> postSearchPage(
			String entryClassNames, String search, Filter filter,
			Pagination pagination, Sort[] sorts,
			SearchRequestBody searchRequestBody)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-179669")) {
			throw new NotFoundException();
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
					filter, search, searchContext,
					searchRequestBody.getAttributes(), sorts)
			);

		String[] entryClassNamesArray = _csvStringToStringArray(
			entryClassNames);

		if (!ArrayUtil.isEmpty(entryClassNamesArray)) {
			searchRequestBuilder.entryClassNames(entryClassNamesArray);
			searchRequestBuilder.modelIndexerClassNames(entryClassNamesArray);
		}

		List<String> fields = Arrays.asList(
			ParamUtil.getStringValues(contextHttpServletRequest, "fields"));

		_setFetchSourceIncludes(fields, searchRequestBuilder);

		if (!Validator.isBlank(search)) {
			searchRequestBuilder.queryString(search);
		}

		if (ArrayUtil.isNotEmpty(searchRequestBody.getFacetConfigurations())) {
			_facetRequestContributor.contribute(
				searchRequestBody.getFacetConfigurations(),
				searchRequestBuilder);
		}

		return _toSearchPage(
			searchRequestBody.getFacetConfigurations(), fields, pagination,
			_searcher.search(searchRequestBuilder.build()));
	}

	private void _addSearchContextAttributes(
		Filter filter, String search, SearchContext searchContext,
		Map<String, Object> attributes, Sort[] sorts) {

		MapUtil.isNotEmptyForEach(
			attributes,
			(key, value) -> {
				if (_isAllowedSearchContextAttribute(key) && (value != null) &&
					(value instanceof Serializable)) {

					searchContext.setAttribute(key, (Serializable)value);
				}
			});

		if (searchContext.getAttribute("search.experiences.ip.address") ==
				null) {

			searchContext.setAttribute(
				"search.experiences.ip.address",
				contextHttpServletRequest.getRemoteAddr());
		}

		if (filter != null) {
			searchContext.setBooleanClauses(
				new BooleanClause[] {
					_getBooleanClause(
						booleanQuery -> {
						},
						filter)
				});
		}

		searchContext.setKeywords(search);
		searchContext.setLocale(contextAcceptLanguage.getPreferredLocale());

		if (!ArrayUtil.isEmpty(sorts)) {
			searchContext.setSorts(sorts);
		}

		searchContext.setTimeZone(contextUser.getTimeZone());
		searchContext.setUserId(contextUser.getUserId());
	}

	private String[] _csvStringToStringArray(String csvString) {
		if (Validator.isBlank(csvString)) {
			return new String[0];
		}

		csvString = StringUtil.trim(csvString);

		return csvString.split("\\s*,\\s*");
	}

	private AssetRenderer<?> _getAssetRenderer(
		String entryClassName, Long entryClassPK) {

		if ((entryClassName == null) || (entryClassPK == null)) {
			return null;
		}

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(entryClassName);

			if (assetRendererFactory == null) {
				return null;
			}

			return assetRendererFactory.getAssetRenderer(entryClassPK);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private BooleanClause<?> _getBooleanClause(
		UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
		Filter filter) {

		BooleanQuery booleanQuery = new BooleanQueryImpl() {
			{
				add(new MatchAllQuery(), BooleanClauseOccur.MUST);

				BooleanFilter booleanFilter = new BooleanFilter();

				if (filter != null) {
					booleanFilter.add(filter, BooleanClauseOccur.MUST);
				}

				setPreBooleanFilter(booleanFilter);
			}
		};

		try {
			booleanQueryUnsafeConsumer.accept(booleanQuery);

			return BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _getEntryClassName(Document document) {
		Map<String, Field> fields = document.getFields();

		Field entryClassNameField = fields.get(
			com.liferay.portal.kernel.search.Field.ENTRY_CLASS_NAME);

		if (entryClassNameField != null) {
			return GetterUtil.getString(entryClassNameField.getValue());
		}

		return document.getString(
			com.liferay.portal.kernel.search.Field.ENTRY_CLASS_NAME);
	}

	private Long _getEntryClassPK(Document document) {
		Map<String, Field> fields = document.getFields();

		Field entryClassNamePK = fields.get(
			com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK);

		if (entryClassNamePK != null) {
			return GetterUtil.getLong(entryClassNamePK.getValue());
		}

		return document.getLong(
			com.liferay.portal.kernel.search.Field.ENTRY_CLASS_PK);
	}

	private boolean _isAllowedSearchContextAttribute(String key) {
		if (key.startsWith("search.experiences.") ||
			key.equals("search.empty.search")) {

			return true;
		}

		return false;
	}

	private boolean _isEmbedded() {
		if (StringUtil.contains(
				ParamUtil.getString(contextHttpServletRequest, "nestedFields"),
				"embedded")) {

			return true;
		}

		return false;
	}

	private Date _parseDateStringFieldValue(String dateStringFieldValue) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			return dateFormat.parse(dateStringFieldValue);
		}
		catch (Exception exception) {
			throw new IllegalArgumentException(
				"Unable to parse date string: " + dateStringFieldValue,
				exception);
		}
	}

	private void _setFetchSourceIncludes(
		List<String> fields, SearchRequestBuilder searchRequestBuilder) {

		if (fields.isEmpty() || fields.contains("dateModified")) {
			searchRequestBuilder.fetchSourceIncludes(
				new String[] {
					"com.liferay.portal.kernel.search.Field.MODIFIED_DATE"
				});
		}
	}

	private Object _toAggregations(
		Map<String, AggregationResult> aggregationResultsMap) {

		if (aggregationResultsMap.isEmpty()) {
			return null;
		}

		Map<String, Object> aggregations = new HashMap<>();

		for (Map.Entry<String, AggregationResult> entry :
				aggregationResultsMap.entrySet()) {

			aggregations.put(entry.getKey(), (Object)entry.getValue());
		}

		return aggregations;
	}

	private SearchPage<SearchResult> _toSearchPage(
			FacetConfiguration[] facetConfigurations, List<String> fields,
			Pagination pagination, SearchResponse searchResponse)
		throws Exception {

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchResult> searchResults = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Document document = searchHit.getDocument();

			SearchResult searchResult = new SearchResult();

			String entryClassName = _getEntryClassName(document);

			Long entryClassPK = _getEntryClassPK(document);

			boolean embedded = _isEmbedded();

			AssetRenderer<?> assetRenderer = null;

			if (embedded || fields.isEmpty() ||
				fields.contains("description") || fields.contains("title")) {

				assetRenderer = _getAssetRenderer(entryClassName, entryClassPK);
			}

			if ((fields.isEmpty() || fields.contains("description")) &&
				(assetRenderer != null)) {

				searchResult.setDescription(
					assetRenderer.getSearchSummary(
						contextAcceptLanguage.getPreferredLocale()));
			}

			if ((fields.isEmpty() || fields.contains("title")) &&
				(assetRenderer != null)) {

				searchResult.setTitle(
					assetRenderer.getTitle(
						contextAcceptLanguage.getPreferredLocale()));
			}

			String modifiedDate = document.getString(
				com.liferay.portal.kernel.search.Field.MODIFIED_DATE);

			if ((fields.isEmpty() || fields.contains("dateModified")) &&
				(modifiedDate != null)) {

				searchResult.setDateModified(
					_parseDateStringFieldValue(
						document.getString(
							com.liferay.portal.kernel.search.Field.
								MODIFIED_DATE)));
			}

			DTOConverter<?, ?> dtoConverter = null;

			if (embedded || fields.isEmpty()) {
				dtoConverter = _dtoConverterRegistry.getDTOConverter(
					entryClassName);
			}

			if (fields.isEmpty() && (dtoConverter != null) &&
				(entryClassPK != null)) {

				String jaxRsLink = dtoConverter.getJaxRsLink(
					entryClassPK, contextUriInfo);

				if (!Validator.isBlank(jaxRsLink)) {
					searchResult.setItemURL(jaxRsLink);
				}
			}

			if (embedded && (dtoConverter != null) && (assetRenderer != null)) {
				searchResult.setEmbedded(
					dtoConverter.toDTO(
						new DefaultDTOConverterContext(
							contextAcceptLanguage.isAcceptAllLanguages(),
							new HashMap<>(), _dtoConverterRegistry,
							contextHttpServletRequest, entryClassPK,
							contextAcceptLanguage.getPreferredLocale(),
							contextUriInfo, contextUser)));
			}

			if (fields.isEmpty() || fields.contains("score")) {
				searchResult.setScore(searchHit.getScore());
			}

			searchResults.add(searchResult);
		}

		return SearchPage.of(
			null, _toAggregations(searchResponse.getAggregationResultsMap()),
			_facetResponseProcessor.getTermsMap(
				contextCompany.getCompanyId(), facetConfigurations,
				contextAcceptLanguage.getPreferredLocale(), searchResponse,
				contextUser.getUserId()),
			searchResults, pagination, searchHits.getTotalHits());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultResourceImpl.class);

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private FacetRequestContributor _facetRequestContributor;

	@Reference
	private FacetResponseProcessor _facetResponseProcessor;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private final SearchResultEntityModel _searchResultEntityModel =
		new SearchResultEntityModel();

}