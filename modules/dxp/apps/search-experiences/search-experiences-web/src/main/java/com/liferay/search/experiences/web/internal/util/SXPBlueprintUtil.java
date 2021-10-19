/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.util;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.service.SXPElementService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SXPBlueprintUtil {

	public static void populateSXPBlueprintSearchContainer(
		PortletRequest portletRequest, long groupId, int status,
		SearchContainer<SXPBlueprint> searchContainer, String orderByCol,
		String orderByType) {

		_populateSXPBlueprintSearchContainer(
			portletRequest, groupId, status, searchContainer, orderByCol,
			orderByType);
	}

	public static void populateSXPElementSearchContainer(
		PortletRequest portletRequest, long groupId, int status,
		SearchContainer<SXPElement> searchContainer, String orderByCol,
		String orderByType) {

		_populateSXPElementSearchContainer(
			portletRequest, groupId, status, searchContainer, orderByCol,
			orderByType);
	}

	@Reference(unbind = "-")
	protected void setQueries(Queries queries) {
		_queries = queries;
	}

	@Reference(unbind = "-")
	protected void setSearcher(Searcher searcher) {
		_searcher = searcher;
	}

	@Reference(unbind = "-")
	protected void setSearchRequestBuilderFactory(
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setSorts(Sorts sorts) {
		_sorts = sorts;
	}

	@Reference(unbind = "-")
	protected void setSXPBlueprintService(
		SXPBlueprintService sxpBlueprintService) {

		_sxpSXPBlueprintService = sxpBlueprintService;
	}

	@Reference(unbind = "-")
	protected void setSXPElementService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private static void _addGroupFilterClause(
		BooleanQuery booleanQuery, long groupId) {

		TermQuery groupQuery = _queries.term(Field.GROUP_ID, groupId);

		booleanQuery.addFilterQueryClauses(groupQuery);
	}

	private static void _addReadOnlyFilterClause(
		BooleanQuery booleanQuery, PortletRequest portletRequest) {

		if (!Validator.isBlank(
				ParamUtil.getString(portletRequest, "readOnly"))) {

			booleanQuery.addFilterQueryClauses(
				_queries.term(
					"readOnly",
					ParamUtil.getBoolean(portletRequest, "readOnly")));
		}
	}

	private static void _addSearchClauses(
		BooleanQuery booleanQuery, String keywords, String languageId) {

		if (Validator.isBlank(keywords)) {
			booleanQuery.addMustQueryClauses(_queries.matchAll());
		}
		else {
			booleanQuery.addMustQueryClauses(
				_queries.multiMatch(keywords, _getSearchFields(languageId)));
		}
	}

	private static void _addStatusFilterClause(
		BooleanQuery booleanQuery, int status) {

		booleanQuery.addFilterQueryClauses(_queries.term(Field.STATUS, status));
	}

	private static void _addVisibilityFilterClause(
		BooleanQuery booleanQuery, PortletRequest portletRequest) {

		if (ParamUtil.getString(portletRequest, "hidden") != null) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					"hidden", ParamUtil.getBoolean(portletRequest, "hidden")));
		}
	}

	private static SearchRequest _createElementSearchRequest(
		PortletRequest portletRequest, long groupId, int status, int type,
		int start, int size, String orderByCol, String orderByType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		return _searchRequestBuilderFactory.builder(
		).companyId(
			themeDisplay.getCompanyId()
		).from(
			start
		).modelIndexerClasses(
			SXPElement.class
		).query(
			_getSXPElementSearchQuery(
				portletRequest, type, groupId, status, languageId)
		).size(
			size
		).addSort(
			_getSort(orderByCol, orderByType, languageId)
		).build();
	}

	private static SearchRequest _createSXPBlueprintSearchRequest(
		PortletRequest portletRequest, long groupId, int status, int start,
		int size, String orderByCol, String orderByType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		return _searchRequestBuilderFactory.builder(
		).companyId(
			themeDisplay.getCompanyId()
		).from(
			start
		).modelIndexerClasses(
			SXPBlueprint.class
		).query(
			_getSXPBlueprintSearchQuery(
				ParamUtil.getString(portletRequest, "keywords"), groupId,
				status, languageId)
		).size(
			size
		).addSort(
			_getSort(orderByCol, orderByType, languageId)
		).build();
	}

	private static long _getEntryClassPK(SearchHit searchHit) {
		Document document = searchHit.getDocument();

		return document.getLong(Field.ENTRY_CLASS_PK);
	}

	private static Set<String> _getSearchFields(String languageId) {
		Set<String> fields = new HashSet<>();

		fields.add(_getTitleField(languageId));
		fields.add(
			LocalizationUtil.getLocalizedName(Field.DESCRIPTION, languageId));

		return fields;
	}

	private static Sort _getSort(
		String orderByCol, String orderByType, String languageId) {

		SortOrder sortOrder = SortOrder.ASC;

		if (orderByType.equals("desc")) {
			sortOrder = SortOrder.DESC;
		}

		if (Objects.equals(orderByCol, Field.TITLE)) {
			return _sorts.field(
				_getTitleField(languageId) + "_String_sortable", sortOrder);
		}

		return _sorts.field(orderByCol, sortOrder);
	}

	private static Query _getSXPBlueprintSearchQuery(
		String keywords, long groupId, int status, String languageId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		_addGroupFilterClause(booleanQuery, groupId);

		_addStatusFilterClause(booleanQuery, status);

		_addSearchClauses(booleanQuery, keywords, languageId);

		return booleanQuery;
	}

	private static Query _getSXPElementSearchQuery(
		PortletRequest portletRequest, long type, long groupId, int status,
		String languageId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		_addGroupFilterClause(booleanQuery, groupId);

		if (type > 0) {
			booleanQuery.addFilterQueryClauses(_queries.term(Field.TYPE, type));
		}

		if (status != WorkflowConstants.STATUS_ANY) {
			_addStatusFilterClause(booleanQuery, status);
		}

		_addVisibilityFilterClause(booleanQuery, portletRequest);

		_addReadOnlyFilterClause(booleanQuery, portletRequest);

		_addSearchClauses(
			booleanQuery, ParamUtil.getString(portletRequest, "keywords"),
			languageId);

		return booleanQuery;
	}

	private static String _getTitleField(String languageId) {
		return LocalizationUtil.getLocalizedName(
			"localized_" + Field.TITLE, languageId);
	}

	private static void _populateSXPBlueprintSearchContainer(
		PortletRequest portletRequest, long groupId, int status,
		SearchContainer<SXPBlueprint> searchContainer, String orderByCol,
		String orderByType) {

		SearchRequest searchRequest = _createSXPBlueprintSearchRequest(
			portletRequest, groupId, status, searchContainer.getStart(),
			searchContainer.getDelta(), orderByCol, orderByType);

		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		searchContainer.setTotal(
			GetterUtil.getInteger(searchHits.getTotalHits()));

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		searchContainer.setResults(
			stream.map(
				searchHit -> _toSXPBlueprintOptional(searchHit)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			));
	}

	private static void _populateSXPElementSearchContainer(
		PortletRequest portletRequest, long groupId, int status,
		SearchContainer<SXPElement> searchContainer, String orderByCol,
		String orderByType) {

		SearchRequest searchRequest = _createElementSearchRequest(
			portletRequest, groupId, status,
			ParamUtil.getInteger(portletRequest, "sxpElementType"),
			searchContainer.getStart(), searchContainer.getDelta(), orderByCol,
			orderByType);

		SearchResponse searchResponse = _searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		searchContainer.setTotal(
			GetterUtil.getInteger(searchHits.getTotalHits()));

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		searchContainer.setResults(
			stream.map(
				searchHit -> _toSXPElementOptional(searchHit)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			));
	}

	private static Optional<SXPBlueprint> _toSXPBlueprintOptional(
		SearchHit searchHit) {

		long entryClassPK = _getEntryClassPK(searchHit);

		try {
			return Optional.of(
				_sxpSXPBlueprintService.getSXPBlueprint(entryClassPK));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Search index is stale and contains a non-existent " +
						"SXPBlueprint entry " + entryClassPK);
			}
		}

		return Optional.empty();
	}

	private static Optional<SXPElement> _toSXPElementOptional(
		SearchHit searchHit) {

		long entryClassPK = _getEntryClassPK(searchHit);

		try {
			return Optional.of(_sxpElementService.getSXPElement(entryClassPK));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Search index is stale and contains a non-existent " +
						"SXPElement entry " + entryClassPK);
			}
		}

		return Optional.empty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintUtil.class);

	private static Queries _queries;
	private static Searcher _searcher;
	private static SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private static Sorts _sorts;
	private static SXPElementService _sxpElementService;
	private static SXPBlueprintService _sxpSXPBlueprintService;

}