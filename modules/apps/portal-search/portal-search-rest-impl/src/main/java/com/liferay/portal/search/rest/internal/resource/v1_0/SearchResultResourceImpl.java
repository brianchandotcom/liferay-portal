/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.resource.v1_0;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
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
import com.liferay.portal.kernel.service.LayoutLocalService;
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
					searchRequestBody.getAttributes(), filter, search,
					searchContext, sorts)
			);

		String[] entryClassNamesArray = _toArray(entryClassNames);

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
		Map<String, Object> attributes, Filter filter, String search,
		SearchContext searchContext, Sort[] sorts) {

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

	private Object _fetchObject(String entryClassName, Long entryClassPK) {
		if (entryClassName.equals(Layout.class.getName())) {
			return _layoutLocalService.fetchLayout(entryClassPK);
		}
		else if (entryClassName.startsWith(ObjectDefinition.class.getName())) {
			return _objectEntryLocalService.fetchObjectEntry(entryClassPK);
		}

		return null;
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

	private String _getDTOClassName(String entryClassName) {
		if (entryClassName.startsWith(ObjectDefinition.class.getName())) {
			return ObjectEntry.class.getName();
		}

		return entryClassName;
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

	private boolean _isEmptyOrContains(List<String> list, String... strings) {
		if (list.isEmpty()) {
			return true;
		}

		for (String s : strings) {
			if (list.contains(s)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isObjectToDTOEntryClassName(String entryClassName) {
		if (entryClassName.equals(Layout.class.getName()) ||
			entryClassName.startsWith(ObjectDefinition.class.getName())) {

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

	private void _setDateModified(
		Document document, List<String> fields, SearchResult searchResult) {

		if (!_isEmptyOrContains(fields, "dateModified")) {
			return;
		}

		String modifiedDate = document.getString(
			com.liferay.portal.kernel.search.Field.MODIFIED_DATE);

		if (modifiedDate != null) {
			searchResult.setDateModified(
				_parseDateStringFieldValue(
					document.getString(
						com.liferay.portal.kernel.search.Field.MODIFIED_DATE)));
		}
	}

	private void _setDescription(
		AssetRenderer<?> assetRenderer, List<String> fields,
		SearchResult searchResult) {

		if (!_isEmptyOrContains(fields, "description")) {
			return;
		}

		searchResult.setDescription(
			assetRenderer.getSearchSummary(
				contextAcceptLanguage.getPreferredLocale()));
	}

	@SuppressWarnings("rawtypes")
	private void _setDTOFields(
		boolean embedded, String entryClassName, Long entryClassPK,
		List<String> fields, SearchResult searchResult) {

		DTOConverter dtoConverter = null;

		if (embedded || _isEmptyOrContains(fields, "itemURL")) {
			dtoConverter = _dtoConverterRegistry.getDTOConverter(
				_getDTOClassName(entryClassName));
		}

		if (dtoConverter == null) {
			return;
		}

		if (embedded) {
			_setEmbedded(
				dtoConverter, entryClassPK, entryClassName, searchResult);
		}

		_setItemURL(dtoConverter, entryClassPK, fields, searchResult);
	}

	@SuppressWarnings("rawtypes")
	private void _setEmbedded(
		DTOConverter dtoConverter, Long entryClassPK, String entryClassName,
		SearchResult searchResult) {

		try {
			if (_isObjectToDTOEntryClassName(entryClassName)) {
				Object object = _fetchObject(entryClassName, entryClassPK);

				if (object == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"No DTO converter found for " + entryClassName);
					}
				}

				searchResult.setEmbedded(
					dtoConverter.toDTO(
						new DefaultDTOConverterContext(
							contextAcceptLanguage.isAcceptAllLanguages(),
							new HashMap<>(), _dtoConverterRegistry,
							contextHttpServletRequest, entryClassPK,
							contextAcceptLanguage.getPreferredLocale(),
							contextUriInfo, contextUser),
						object));
			}
			else {
				searchResult.setEmbedded(
					dtoConverter.toDTO(
						new DefaultDTOConverterContext(
							contextAcceptLanguage.isAcceptAllLanguages(),
							new HashMap<>(), _dtoConverterRegistry,
							contextHttpServletRequest, entryClassPK,
							contextAcceptLanguage.getPreferredLocale(),
							contextUriInfo, contextUser)));
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _setFetchSourceIncludes(
		List<String> fields, SearchRequestBuilder searchRequestBuilder) {

		if (!_isEmptyOrContains(fields, "dateModified")) {
			searchRequestBuilder.fetchSourceIncludes(
				new String[] {
					"com.liferay.portal.kernel.search.Field.MODIFIED_DATE"
				});
		}
	}

	@SuppressWarnings("rawtypes")
	private void _setItemURL(
		DTOConverter dtoConverter, Long entryClassPK, List<String> fields,
		SearchResult searchResult) {

		if (!_isEmptyOrContains(fields, "itemURL")) {
			return;
		}

		String jaxRsLink = dtoConverter.getJaxRsLink(
			entryClassPK, contextUriInfo);

		if (!Validator.isBlank(jaxRsLink)) {
			searchResult.setItemURL(jaxRsLink);
		}
	}

	private void _setScore(
		List<String> fields, SearchHit searchHit, SearchResult searchResult) {

		if (!_isEmptyOrContains(fields, "score")) {
			return;
		}

		searchResult.setScore(searchHit.getScore());
	}

	private void _setTitle(
		AssetRenderer<?> assetRenderer, List<String> fields,
		SearchResult searchResult) {

		if (!_isEmptyOrContains(fields, "title")) {
			return;
		}

		searchResult.setTitle(
			assetRenderer.getTitle(contextAcceptLanguage.getPreferredLocale()));
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

	private String[] _toArray(String csvString) {
		if (Validator.isBlank(csvString)) {
			return new String[0];
		}

		csvString = StringUtil.trim(csvString);

		return csvString.split("\\s*,\\s*");
	}

	private SearchPage<SearchResult> _toSearchPage(
		FacetConfiguration[] facetConfigurations, List<String> fields,
		Pagination pagination, SearchResponse searchResponse) {

		List<SearchResult> searchResults = new ArrayList<>();

		SearchHits searchHits = searchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			SearchResult searchResult = new SearchResult();

			Document document = searchHit.getDocument();
			boolean embedded = _isEmbedded();
			String entryClassName = _getEntryClassName(document);
			Long entryClassPK = _getEntryClassPK(document);

			AssetRenderer<?> assetRenderer = null;

			if (embedded ||
				_isEmptyOrContains(fields, "description", "title")) {

				assetRenderer = _getAssetRenderer(entryClassName, entryClassPK);
			}

			if (assetRenderer != null) {
				_setDescription(assetRenderer, fields, searchResult);
				_setDTOFields(
					embedded, entryClassName, entryClassPK, fields,
					searchResult);
				_setTitle(assetRenderer, fields, searchResult);
			}

			_setDateModified(document, fields, searchResult);
			_setScore(fields, searchHit, searchResult);

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
	private LayoutLocalService _layoutLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private final SearchResultEntityModel _searchResultEntityModel =
		new SearchResultEntityModel();

}