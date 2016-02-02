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

package com.liferay.search.web.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletURLUtil;
import com.liferay.search.facet.SearchFacet;
import com.liferay.search.facet.util.SearchFacetTracker;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SearchDisplayContext {

	public SearchDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences)
		throws Exception {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_portletPreferences = portletPreferences;

		if (Validator.isNull(getKeywords())) {
			_hits = null;
			_searchContext = null;
			_searchContainer = null;

			return;
		}

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_renderRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultMessage = LanguageUtil.format(
			request, "no-results-were-found-that-matched-the-keywords-x",
			"<strong>" + HtmlUtil.escape(getKeywords()) + "</strong>", false);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, emptyResultMessage);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setQueryConfig(getQueryConfig());
		searchContext.setStart(searchContainer.getStart());

		Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

		assetEntriesFacet.setStatic(true);

		searchContext.addFacet(assetEntriesFacet);

		Facet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(true);

		searchContext.addFacet(scopeFacet);

		for (SearchFacet searchFacet : getEnabledSearchFacets()) {
			searchFacet.init(
				themeDisplay.getCompanyId(), getSearchConfiguration(),
				searchContext);

			Facet facet = searchFacet.getFacet();

			if (facet == null) {
				continue;
			}

			searchContext.addFacet(facet);
		}

		Indexer<?> indexer = FacetedSearcher.getInstance();

		Hits hits = indexer.search(searchContext);

		searchContainer.setTotal(hits.getLength());
		searchContainer.setResults(hits.toList());

		_hits = hits;
		_searchContext = searchContext;
		_searchContainer = searchContainer;
	}

	public String checkViewURL(String viewURL, String currentURL) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNotNull(viewURL) &&
			viewURL.startsWith(themeDisplay.getURLPortal())) {

			viewURL = HttpUtil.setParameter(
				viewURL, "inheritRedirect", isViewInContext());

			if (!isViewInContext()) {
				viewURL = HttpUtil.setParameter(
					viewURL, "redirect", currentURL);
			}
		}

		return viewURL;
	}

	public int getCollatedSpellCheckResultDisplayThreshold() {
		if (_collatedSpellCheckResultDisplayThreshold != null) {
			return _collatedSpellCheckResultDisplayThreshold;
		}

		_collatedSpellCheckResultDisplayThreshold = GetterUtil.getInteger(
			_portletPreferences.getValue(
				"collatedSpellCheckResultDisplayThreshold", null),
			PropsValues.
				INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD);

		if (_collatedSpellCheckResultDisplayThreshold < 0) {
			_collatedSpellCheckResultDisplayThreshold =
				PropsValues.
					INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD;
		}

		return _collatedSpellCheckResultDisplayThreshold;
	}

	public List<SearchFacet> getEnabledSearchFacets() {
		if (_enabledSearchFacets != null) {
			return _enabledSearchFacets;
		}

		_enabledSearchFacets = ListUtil.filter(
			SearchFacetTracker.getSearchFacets(),
			new PredicateFilter<SearchFacet>() {

				@Override
				public boolean filter(SearchFacet searchFacet) {
					return isDisplayFacet(searchFacet.getClassName());
				}

			});

		return _enabledSearchFacets;
	}

	public Hits getHits() throws Exception {
		return _hits;
	}

	public String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	public PortletURL getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		return PortletURLUtil.clone(portletURL, _renderResponse);
	}

	public QueryConfig getQueryConfig() {
		if (_queryConfig != null) {
			return _queryConfig;
		}

		_queryConfig = new QueryConfig();

		_queryConfig.setCollatedSpellCheckResultEnabled(
			isCollatedSpellCheckResultEnabled());
		_queryConfig.setCollatedSpellCheckResultScoresThreshold(
			getCollatedSpellCheckResultDisplayThreshold());
		_queryConfig.setQueryIndexingEnabled(isQueryIndexingEnabled());
		_queryConfig.setQueryIndexingThreshold(getQueryIndexingThreshold());
		_queryConfig.setQuerySuggestionEnabled(isQuerySuggestionsEnabled());
		_queryConfig.setQuerySuggestionScoresThreshold(
			getQuerySuggestionsDisplayThreshold());
		_queryConfig.setQuerySuggestionsMax(getQuerySuggestionsMax());

		return _queryConfig;
	}

	public int getQueryIndexingThreshold() {
		if (_queryIndexingThreshold != null) {
			return _queryIndexingThreshold;
		}

		_queryIndexingThreshold = GetterUtil.getInteger(
			_portletPreferences.getValue("queryIndexingThreshold", null),
			PropsValues.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD);

		if (_queryIndexingThreshold < 0) {
			_queryIndexingThreshold =
				PropsValues.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD;
		}

		return _queryIndexingThreshold;
	}

	public int getQuerySuggestionsDisplayThreshold() {
		if (_querySuggestionsDisplayThreshold != null) {
			return _querySuggestionsDisplayThreshold;
		}

		_querySuggestionsDisplayThreshold = GetterUtil.getInteger(
			_portletPreferences.getValue(
				"querySuggestionsDisplayThreshold", null),
			PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD);

		if (_querySuggestionsDisplayThreshold < 0) {
			_querySuggestionsDisplayThreshold =
				PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD;
		}

		return _querySuggestionsDisplayThreshold;
	}

	public int getQuerySuggestionsMax() {
		if (_querySuggestionsMax != null) {
			return _querySuggestionsMax;
		}

		_querySuggestionsMax = GetterUtil.getInteger(
			_portletPreferences.getValue("querySuggestionsMax", null),
			PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_MAX);

		if (_querySuggestionsMax <= 0) {
			_querySuggestionsMax =
				PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_MAX;
		}

		return _querySuggestionsMax;
	}

	public String[] getQueryTerms() throws Exception {
		Hits hits = getHits();

		return hits.getQueryTerms();
	}

	public String getSearchConfiguration() {
		if (_searchConfiguration != null) {
			return _searchConfiguration;
		}

		_searchConfiguration = _portletPreferences.getValue(
			"searchConfiguration", StringPool.BLANK);

		return _searchConfiguration;
	}

	public SearchContainer getSearchContainer() throws Exception {
		return _searchContainer;
	}

	public SearchContext getSearchContext() throws Exception {
		return _searchContext;
	}

	public String getSearchScope() {
		if (_searchScope != null) {
			return _searchScope;
		}

		_searchScope = _portletPreferences.getValue(
			"searchScope", StringPool.BLANK);

		return _searchScope;
	}

	public boolean isCollatedSpellCheckResultEnabled() {
		if (_collatedSpellCheckResultEnabled != null) {
			return _collatedSpellCheckResultEnabled;
		}

		_collatedSpellCheckResultEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"collatedSpellCheckResultEnabled", null),
			PropsValues.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED);

		return _collatedSpellCheckResultEnabled;
	}

	public boolean isDisplayFacet(String className) {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(className, null), true);
	}

	public boolean isDisplayMainQuery() {
		if (_displayMainQuery != null) {
			return _displayMainQuery;
		}

		_displayMainQuery = GetterUtil.getBoolean(
			_portletPreferences.getValue("displayMainQuery", null));

		return _displayMainQuery;
	}

	public boolean isDisplayOpenSearchResults() {
		if (_displayOpenSearchResults != null) {
			return _displayOpenSearchResults;
		}

		_displayOpenSearchResults = GetterUtil.getBoolean(
			_portletPreferences.getValue("displayOpenSearchResults", null));

		return _displayOpenSearchResults;
	}

	public boolean isDisplayResultsInDocumentForm() {
		if (_displayResultsInDocumentForm != null) {
			return _displayResultsInDocumentForm;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_displayResultsInDocumentForm = GetterUtil.getBoolean(
			_portletPreferences.getValue("displayResultsInDocumentForm", null));

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			_displayResultsInDocumentForm = false;
		}

		return _displayResultsInDocumentForm;
	}

	public boolean isDLLinkToViewURL() {
		if (_dlLinkToViewURL != null) {
			return _dlLinkToViewURL;
		}

		_dlLinkToViewURL = false;

		return _dlLinkToViewURL;
	}

	public boolean isHighlightEnabled() {
		QueryConfig queryConfig = getQueryConfig();

		return queryConfig.isHighlightEnabled();
	}

	public boolean isIncludeSystemPortlets() {
		if (_includeSystemPortlets != null) {
			return _includeSystemPortlets;
		}

		_includeSystemPortlets = false;

		return _includeSystemPortlets;
	}

	public boolean isQueryIndexingEnabled() {
		if (_queryIndexingEnabled != null) {
			return _queryIndexingEnabled;
		}

		_queryIndexingEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue("queryIndexingEnabled", null),
			PropsValues.INDEX_SEARCH_QUERY_INDEXING_ENABLED);

		return _queryIndexingEnabled;
	}

	public boolean isQuerySuggestionsEnabled() {
		if (_querySuggestionsEnabled != null) {
			return _querySuggestionsEnabled;
		}

		_querySuggestionsEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue("querySuggestionsEnabled", null),
			PropsValues.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED);

		return _querySuggestionsEnabled;
	}

	public boolean isShowMenu() {
		for (SearchFacet searchFacet : SearchFacetTracker.getSearchFacets()) {
			if (isDisplayFacet(searchFacet.getClassName())) {
				return true;
			}
		}

		return false;
	}

	public boolean isViewInContext() {
		if (_viewInContext != null) {
			return _viewInContext;
		}

		_viewInContext = GetterUtil.getBoolean(
			_portletPreferences.getValue("viewInContext", null), true);

		return _viewInContext;
	}

	private Integer _collatedSpellCheckResultDisplayThreshold;
	private Boolean _collatedSpellCheckResultEnabled;
	private Boolean _displayMainQuery;
	private Boolean _displayOpenSearchResults;
	private Boolean _displayResultsInDocumentForm;
	private Boolean _dlLinkToViewURL;
	private List<SearchFacet> _enabledSearchFacets;
	private final Hits _hits;
	private Boolean _includeSystemPortlets;
	private final PortletPreferences _portletPreferences;
	private QueryConfig _queryConfig;
	private Boolean _queryIndexingEnabled;
	private Integer _queryIndexingThreshold;
	private Integer _querySuggestionsDisplayThreshold;
	private Boolean _querySuggestionsEnabled;
	private Integer _querySuggestionsMax;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String _searchConfiguration;
	private final SearchContainer _searchContainer;
	private final SearchContext _searchContext;
	private String _searchScope;
	private Boolean _viewInContext;

}