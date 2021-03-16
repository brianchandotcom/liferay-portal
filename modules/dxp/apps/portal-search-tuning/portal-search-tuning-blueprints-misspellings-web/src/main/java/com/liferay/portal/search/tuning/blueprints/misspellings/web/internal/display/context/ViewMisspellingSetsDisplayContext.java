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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.DocumentToMisspellingSetTranslator;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingSetFields;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.request.SearchMisspellingSetRequest;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.request.SearchMisspellingSetResponse;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class ViewMisspellingSetsDisplayContext {

	public ViewMisspellingSetsDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DocumentToMisspellingSetTranslator documentToMisspellingSetTranslator,
		MisspellingsIndexNameBuilder misspellingsIndexNameBuilder,
		Portal portal, Queries queries, SearchEngineAdapter searchEngineAdapter,
		Sorts sorts) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_documentToMisspellingSetTranslator =
			documentToMisspellingSetTranslator;
		_misspellingsIndexNameBuilder = misspellingsIndexNameBuilder;
		_portal = portal;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);

		_queries = queries;
		_searchEngineAdapter = searchEngineAdapter;
		_sorts = sorts;

		_httpServletRequest = _liferayPortletRequest.getHttpServletRequest();
	}

	public List<String> getAvailableActions(MisspellingSet misspellingSet)
		throws PortalException {

		return Collections.singletonList("deleteBlueprintEntries");
	}

	public String getDisplayStyle() {
		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			return _portalPreferences.getValue(
				MisspellingsPortletKeys.MISSPELLINGS, "entries-display-style",
				"descriptive");
		}

		_portalPreferences.setValue(
			MisspellingsPortletKeys.MISSPELLINGS, "entries-display-style",
			displayStyle);

		_httpServletRequest.setAttribute(
			WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);

		return displayStyle;
	}

	public SearchContainer<MisspellingSet> getSearchContainer()
		throws PortalException, PortletException {

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setProperty(
			"mvcRenderCommandName",
			MisspellingsMVCCommandNames.VIEW_MISSPELLING_SETS);

		SearchContainer<MisspellingSet> searchContainer = new SearchContainer<>(
			_liferayPortletRequest,
			PortletURLUtil.clone(portletURL, _liferayPortletResponse), null,
			"there-are-no-misspelling-sets");

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", MisspellingSetFields.PHRASE);

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		searchContainer.setOrderByType(orderByType);

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		_populateResults(searchContainer);

		return searchContainer;
	}

	private void _populateResults(
			SearchContainer<MisspellingSet> searchContainer)
		throws PortalException {

		SearchMisspellingSetRequest searchMisspellingSetRequest =
			new SearchMisspellingSetRequest(
				_misspellingsIndexNameBuilder.getMisspellingsIndexName(
					_portal.getCompanyId(_liferayPortletRequest)),
				_httpServletRequest, _queries, _sorts, searchContainer,
				_searchEngineAdapter);

		SearchMisspellingSetResponse searchMisspellingSetResponse =
			searchMisspellingSetRequest.search();

		searchContainer.setResults(
			_documentToMisspellingSetTranslator.translateAll(
				searchMisspellingSetResponse.getSearchHits()));

		searchContainer.setSearch(true);
		searchContainer.setTotal(searchMisspellingSetResponse.getTotalHits());
	}

	private final DocumentToMisspellingSetTranslator
		_documentToMisspellingSetTranslator;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;
	private final Portal _portal;
	private final PortalPreferences _portalPreferences;
	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final Sorts _sorts;

}