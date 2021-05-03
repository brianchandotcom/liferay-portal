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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.constants.KeywordIndexPortletKeys;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.DocumentToKeywordEntryTranslator;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordEntryFields;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.request.SearchKeywordEntryRequest;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.request.SearchKeywordEntryResponse;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewKeywordEntriesDisplayContext {

	public ViewKeywordEntriesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DocumentToKeywordEntryTranslator documentToKeywordEntryTranslator,
		Portal portal, Queries queries,
		KeywordIndexNameBuilder keywordIndexNameBuilder,
		SearchEngineAdapter searchEngineAdapter, Sorts sorts,
		KeywordEntryStatus status) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_documentToKeywordEntryTranslator = documentToKeywordEntryTranslator;
		_portal = portal;
		_queries = queries;
		_keywordIndexNameBuilder = keywordIndexNameBuilder;
		_searchEngineAdapter = searchEngineAdapter;
		_sorts = sorts;
		_status = status;

		_httpServletRequest = _liferayPortletRequest.getHttpServletRequest();
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
	}

	public List<String> getAvailableActions(KeywordEntry keywordEntry)
		throws PortalException {

		return Collections.singletonList("deleteEntries");
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		String preferenceName =
			"entries-display-style-" + StringUtil.toLowerCase(_status.name());

		if (Validator.isNull(displayStyle)) {
			return _portalPreferences.getValue(
				KeywordIndexPortletKeys.KEYWORD_INDEX_ADMIN, preferenceName,
				"descriptive");
		}

		_portalPreferences.setValue(
			KeywordIndexPortletKeys.KEYWORD_INDEX_ADMIN, preferenceName,
			displayStyle);

		_httpServletRequest.setAttribute(
			WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);

		return displayStyle;
	}

	public SearchContainer<KeywordEntry> getSearchContainer()
		throws PortalException, PortletException {

		SearchContainer<KeywordEntry> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _getIteratorURL(), null,
			"there-are-no-query-strings");

		searchContainer.setOrderByCol(
			ParamUtil.getString(
				_httpServletRequest, "orderByCol", KeywordEntryFields.CREATED));

		searchContainer.setOrderByType(
			ParamUtil.getString(_httpServletRequest, "orderByType", "desc"));

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_populateResults(searchContainer);

		return searchContainer;
	}

	private PortletURL _getIteratorURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setProperty(
			"mvcRenderCommandName",
			KeywordIndexMVCCommandNames.VIEW_KEYWORD_ENTRIES);

		portletURL.setParameter("tabs", _status.name());

		return portletURL;
	}

	private void _populateResults(SearchContainer<KeywordEntry> searchContainer)
		throws PortalException {

		SearchKeywordEntryRequest searchKeywordEntryRequest =
			new SearchKeywordEntryRequest(
				_keywordIndexNameBuilder.getKeywordIndexName(
					_portal.getCompanyId(_liferayPortletRequest)),
				_httpServletRequest, _queries, searchContainer,
				_searchEngineAdapter, _sorts, _status);

		SearchKeywordEntryResponse searchKeywordEntryResponse =
			searchKeywordEntryRequest.search();

		searchContainer.setResults(
			_documentToKeywordEntryTranslator.translateAll(
				searchKeywordEntryResponse.getSearchHits()));

		searchContainer.setSearch(true);
		searchContainer.setTotal(searchKeywordEntryResponse.getTotalHits());
	}

	private final DocumentToKeywordEntryTranslator
		_documentToKeywordEntryTranslator;
	private final HttpServletRequest _httpServletRequest;
	private final KeywordIndexNameBuilder _keywordIndexNameBuilder;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Portal _portal;
	private final PortalPreferences _portalPreferences;
	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final Sorts _sorts;
	private final KeywordEntryStatus _status;

}