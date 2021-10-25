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

/**
 * @author Petteri Karttunen
 */
public class SXPBlueprintUtil {

	public static void populateSXPBlueprintSearchContainer(
		long groupId, String orderByCol, String orderByType,
		PortletRequest portletRequest, Queries queries, Searcher searcher,
		SearchContainer<SXPBlueprint> searchContainer,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, SXPBlueprintService sxpBlueprintService) {

		_populateSXPBlueprintSearchContainer(
			groupId, orderByCol, orderByType, portletRequest, queries, searcher,
			searchContainer, searchRequestBuilderFactory, sorts, status,
			sxpBlueprintService);
	}

	public static void populateSXPElementSearchContainer(
		long groupId, String orderByCol, String orderByType,
		PortletRequest portletRequest, Queries queries, Searcher searcher,
		SearchContainer<SXPElement> searchContainer,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, SXPElementService sxpElementService) {

		_populateSXPElementSearchContainer(
			groupId, orderByCol, orderByType, portletRequest, queries, searcher,
			searchContainer, searchRequestBuilderFactory, sorts, status,
			sxpElementService);
	}

	private static void _addGroupFilterClause(
		BooleanQuery booleanQuery, long groupId, Queries queries) {

		TermQuery groupQuery = queries.term(Field.GROUP_ID, groupId);

		booleanQuery.addFilterQueryClauses(groupQuery);
	}

	private static void _addReadOnlyFilterClause(
		BooleanQuery booleanQuery, PortletRequest portletRequest,
		Queries queries) {

		if (!Validator.isBlank(
				ParamUtil.getString(portletRequest, "readOnly"))) {

			booleanQuery.addFilterQueryClauses(
				queries.term(
					"readOnly",
					ParamUtil.getBoolean(portletRequest, "readOnly")));
		}
	}

	private static void _addSearchClauses(
		BooleanQuery booleanQuery, String keywords, String languageId,
		Queries queries) {

		if (Validator.isBlank(keywords)) {
			booleanQuery.addMustQueryClauses(queries.matchAll());
		}
		else {
			booleanQuery.addMustQueryClauses(
				queries.multiMatch(keywords, _getSearchFields(languageId)));
		}
	}

	private static void _addStatusFilterClause(
		BooleanQuery booleanQuery, Queries queries, int status) {

		booleanQuery.addFilterQueryClauses(queries.term(Field.STATUS, status));
	}

	private static void _addVisibilityFilterClause(
		BooleanQuery booleanQuery, PortletRequest portletRequest,
		Queries queries) {

		if (ParamUtil.getString(portletRequest, "hidden") != null) {
			booleanQuery.addFilterQueryClauses(
				queries.term(
					"hidden", ParamUtil.getBoolean(portletRequest, "hidden")));
		}
	}

	private static SearchRequest _createElementSearchRequest(
		PortletRequest portletRequest, long groupId, Queries queries,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, int type, int start, int size, String orderByCol,
		String orderByType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		return searchRequestBuilderFactory.builder(
		).companyId(
			themeDisplay.getCompanyId()
		).from(
			start
		).modelIndexerClasses(
			SXPElement.class
		).query(
			_getSXPElementSearchQuery(
				portletRequest, type, groupId, queries, status, languageId)
		).size(
			size
		).addSort(
			_getSort(orderByCol, orderByType, languageId, sorts)
		).build();
	}

	private static SearchRequest _createSXPBlueprintSearchRequest(
		PortletRequest portletRequest, long groupId, Queries queries,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, int start, int size, String orderByCol,
		String orderByType) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		return searchRequestBuilderFactory.builder(
		).companyId(
			themeDisplay.getCompanyId()
		).from(
			start
		).modelIndexerClasses(
			SXPBlueprint.class
		).query(
			_getSXPBlueprintSearchQuery(
				ParamUtil.getString(portletRequest, "keywords"), groupId,
				queries, status, languageId)
		).size(
			size
		).addSort(
			_getSort(orderByCol, orderByType, languageId, sorts)
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
		String orderByCol, String orderByType, String languageId, Sorts sorts) {

		SortOrder sortOrder = SortOrder.ASC;

		if (orderByType.equals("desc")) {
			sortOrder = SortOrder.DESC;
		}

		if (Objects.equals(orderByCol, Field.TITLE)) {
			return sorts.field(
				_getTitleField(languageId) + "_String_sortable", sortOrder);
		}

		return sorts.field(orderByCol, sortOrder);
	}

	private static Query _getSXPBlueprintSearchQuery(
		String keywords, long groupId, Queries queries, int status,
		String languageId) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		_addGroupFilterClause(booleanQuery, groupId, queries);

		_addStatusFilterClause(booleanQuery, queries, status);

		_addSearchClauses(booleanQuery, keywords, languageId, queries);

		return booleanQuery;
	}

	private static Query _getSXPElementSearchQuery(
		PortletRequest portletRequest, long type, long groupId, Queries queries,
		int status, String languageId) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		_addGroupFilterClause(booleanQuery, groupId, queries);

		if (type > 0) {
			booleanQuery.addFilterQueryClauses(queries.term(Field.TYPE, type));
		}

		if (status != WorkflowConstants.STATUS_ANY) {
			_addStatusFilterClause(booleanQuery, queries, status);
		}

		_addVisibilityFilterClause(booleanQuery, portletRequest, queries);

		_addReadOnlyFilterClause(booleanQuery, portletRequest, queries);

		_addSearchClauses(
			booleanQuery, ParamUtil.getString(portletRequest, "keywords"),
			languageId, queries);

		return booleanQuery;
	}

	private static String _getTitleField(String languageId) {
		return LocalizationUtil.getLocalizedName(
			"localized_" + Field.TITLE, languageId);
	}

	private static void _populateSXPBlueprintSearchContainer(
		long groupId, String orderByCol, String orderByType,
		PortletRequest portletRequest, Queries queries, Searcher searcher,
		SearchContainer<SXPBlueprint> searchContainer,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, SXPBlueprintService sxpBlueprintService) {

		SearchRequest searchRequest = _createSXPBlueprintSearchRequest(
			portletRequest, groupId, queries, searchRequestBuilderFactory,
			sorts, status, searchContainer.getStart(),
			searchContainer.getDelta(), orderByCol, orderByType);

		SearchResponse searchResponse = searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		searchContainer.setTotal(
			GetterUtil.getInteger(searchHits.getTotalHits()));

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		searchContainer.setResults(
			stream.map(
				searchHit -> _toSXPBlueprintOptional(
					searchHit, sxpBlueprintService)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			));
	}

	private static void _populateSXPElementSearchContainer(
		long groupId, String orderByCol, String orderByType,
		PortletRequest portletRequest, Queries queries, Searcher searcher,
		SearchContainer<SXPElement> searchContainer,
		SearchRequestBuilderFactory searchRequestBuilderFactory, Sorts sorts,
		int status, SXPElementService sxpElementService) {

		SearchRequest searchRequest = _createElementSearchRequest(
			portletRequest, groupId, queries, searchRequestBuilderFactory,
			sorts, status,
			ParamUtil.getInteger(portletRequest, "sxpElementType"),
			searchContainer.getStart(), searchContainer.getDelta(), orderByCol,
			orderByType);

		SearchResponse searchResponse = searcher.search(searchRequest);

		SearchHits searchHits = searchResponse.getSearchHits();

		searchContainer.setTotal(
			GetterUtil.getInteger(searchHits.getTotalHits()));

		List<SearchHit> hits = searchHits.getSearchHits();

		Stream<SearchHit> stream = hits.stream();

		searchContainer.setResults(
			stream.map(
				searchHit -> _toSXPElementOptional(searchHit, sxpElementService)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			));
	}

	private static Optional<SXPBlueprint> _toSXPBlueprintOptional(
		SearchHit searchHit, SXPBlueprintService sxpBlueprintService) {

		long entryClassPK = _getEntryClassPK(searchHit);

		try {
			return Optional.of(
				sxpBlueprintService.getSXPBlueprint(entryClassPK));
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
		SearchHit searchHit, SXPElementService sxpElementService) {

		long entryClassPK = _getEntryClassPK(searchHit);

		try {
			return Optional.of(sxpElementService.getSXPElement(entryClassPK));
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

}