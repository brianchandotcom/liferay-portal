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

package com.liferay.portal.search.web.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.constants.SearchPortletParameterNames;
import com.liferay.portal.search.web.facet.SearchFacet;
import com.liferay.portal.search.web.facet.util.SearchFacetTracker;

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

	/**
	 * @deprecated As of 7.1.0, replaced by {@link SearchDisplayContextFactoryUtil#create(
	 * RenderRequest, RenderResponse, PortletPreferences)}
	 */
	@Deprecated
	public SearchDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences)
		throws Exception {

		this(
			renderRequest, renderResponse, portletPreferences,
			PortalUtil.getPortal(), HtmlUtil.getHtml(),
			LanguageUtil.getLanguage(), null, new IndexSearchPropsValuesImpl(),
			new PortletURLFactoryImpl());
	}

	public SearchDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			PortletPreferences portletPreferences, Portal portal, Html html,
			Language language, FacetedSearcherManager facetedSearcherManager,
			IndexSearchPropsValues indexSearchPropsValues,
			PortletURLFactory portletURLFactory)
		throws Exception {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_portletPreferences = portletPreferences;
		_indexSearchPropsValues = indexSearchPropsValues;
		_portletURLFactory = portletURLFactory;

		String keywords = getKeywords();

		if (keywords == null) {
			_hits = null;
			_searchContext = null;
			_searchContainer = null;

			return;
		}

		HttpServletRequest request = portal.getHttpServletRequest(
			_renderRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String emptyResultMessage = language.format(
			request, "no-results-were-found-that-matched-the-keywords-x",
			"<strong>" + html.escape(keywords) + "</strong>", false);

		SearchContainer<Document> searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultMessage);

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setAttribute("paginationType", "more");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setQueryConfig(getQueryConfig());
		searchContext.setStart(searchContainer.getStart());

		addAssetEntriesFacet(searchContext);

		addScopeFacet(searchContext);

		addEnabledSearchFacets(themeDisplay.getCompanyId(), searchContext);

		Hits hits = facetedSearcher.search(searchContext);

		searchContainer.setTotal(hits.getLength());
		searchContainer.setResults(hits.toList());
		searchContainer.setSearch(true);

		_hits = hits;
		_searchContext = searchContext;
		_searchContainer = searchContainer;
	}

	public String checkViewURL(String viewURL, String currentURL) {
		ThemeDisplay themeDisplay = getThemeDisplay();

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

		int collatedSpellCheckResultScoresThreshold =
			_indexSearchPropsValues.
				getCollatedSpellCheckResultScoresThreshold();

		_collatedSpellCheckResultDisplayThreshold = GetterUtil.getInteger(
			_portletPreferences.getValue(
				"collatedSpellCheckResultDisplayThreshold", null),
			collatedSpellCheckResultScoresThreshold);

		if (_collatedSpellCheckResultDisplayThreshold < 0) {
			_collatedSpellCheckResultDisplayThreshold =
				collatedSpellCheckResultScoresThreshold;
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
		return ParamUtil.getString(
			_renderRequest, SearchPortletParameterNames.KEYWORDS, null);
	}

	public PortletURL getPortletURL() throws PortletException {
		return _portletURLFactory.getPortletURL(
			_renderRequest, _renderResponse);
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
			_indexSearchPropsValues.getQueryIndexingThreshold());

		if (_queryIndexingThreshold < 0) {
			_queryIndexingThreshold =
				_indexSearchPropsValues.getQueryIndexingThreshold();
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
			_indexSearchPropsValues.getQuerySuggestionScoresThreshold());

		if (_querySuggestionsDisplayThreshold < 0) {
			_querySuggestionsDisplayThreshold =
				_indexSearchPropsValues.getQuerySuggestionScoresThreshold();
		}

		return _querySuggestionsDisplayThreshold;
	}

	public int getQuerySuggestionsMax() {
		if (_querySuggestionsMax != null) {
			return _querySuggestionsMax;
		}

		_querySuggestionsMax = GetterUtil.getInteger(
			_portletPreferences.getValue("querySuggestionsMax", null),
			_indexSearchPropsValues.getQuerySuggestionMax());

		if (_querySuggestionsMax <= 0) {
			_querySuggestionsMax =
				_indexSearchPropsValues.getQuerySuggestionMax();
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

	public SearchContainer<Document> getSearchContainer() throws Exception {
		return _searchContainer;
	}

	public SearchContext getSearchContext() throws Exception {
		return _searchContext;
	}

	public long getSearchScopeGroupId() {
		SearchScope searchScope = getSearchScope();

		if (searchScope == SearchScope.EVERYTHING) {
			return 0;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	public String getSearchScopeParameterString() {
		SearchScope searchScope = getSearchScope();

		return searchScope.getParameterString();
	}

	public String getSearchScopePreferenceString() {
		if (_searchScopePreferenceString != null) {
			return _searchScopePreferenceString;
		}

		_searchScopePreferenceString = _portletPreferences.getValue(
			"searchScope", StringPool.BLANK);

		return _searchScopePreferenceString;
	}

	public boolean isCollatedSpellCheckResultEnabled() {
		if (_collatedSpellCheckResultEnabled != null) {
			return _collatedSpellCheckResultEnabled;
		}

		_collatedSpellCheckResultEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue(
				"collatedSpellCheckResultEnabled", null),
			_indexSearchPropsValues.isCollatedSpellCheckResultEnabled());

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

		_displayResultsInDocumentForm = GetterUtil.getBoolean(
			_portletPreferences.getValue("displayResultsInDocumentForm", null));

		ThemeDisplay themeDisplay = getThemeDisplay();

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
			_indexSearchPropsValues.isQueryIndexingEnabled());

		return _queryIndexingEnabled;
	}

	public boolean isQuerySuggestionsEnabled() {
		if (_querySuggestionsEnabled != null) {
			return _querySuggestionsEnabled;
		}

		_querySuggestionsEnabled = GetterUtil.getBoolean(
			_portletPreferences.getValue("querySuggestionsEnabled", null),
			_indexSearchPropsValues.isQuerySuggestionEnabled());

		return _querySuggestionsEnabled;
	}

	public boolean isSearchScopePreferenceEverythingAvailable() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		Group group = themeDisplay.getScopeGroup();

		if (group.isStagingGroup()) {
			return false;
		}

		return true;
	}

	public boolean isSearchScopePreferenceLetTheUserChoose() {
		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		if (searchScopePreference ==
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			return true;
		}

		return false;
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

	protected void addAssetEntriesFacet(SearchContext searchContext) {
		Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

		assetEntriesFacet.setStatic(true);

		searchContext.addFacet(assetEntriesFacet);
	}

	protected void addEnabledSearchFacets(
			long companyId, SearchContext searchContext)
		throws Exception {

		for (SearchFacet searchFacet : getEnabledSearchFacets()) {
			searchFacet.init(
				companyId, getSearchConfiguration(), searchContext);

			Facet facet = searchFacet.getFacet();

			if (facet == null) {
				continue;
			}

			searchContext.addFacet(facet);
		}
	}

	protected void addScopeFacet(SearchContext searchContext) {
		Facet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(true);

		searchContext.addFacet(scopeFacet);
	}

	protected SearchScope getSearchScope() {
		String scopeString = ParamUtil.getString(
			_renderRequest, SearchPortletParameterNames.SCOPE);

		if (Validator.isNotNull(scopeString)) {
			return SearchScope.getSearchScope(scopeString);
		}

		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		SearchScope searchScope = searchScopePreference.getSearchScope();

		if (searchScope == null) {
			throw new IllegalArgumentException(
				"Scope parameter is empty and no default is set in " +
					"preferences");
		}

		return searchScope;
	}

	protected SearchScopePreference getSearchScopePreference() {
		return SearchScopePreference.getSearchScopePreference(
			getSearchScopePreferenceString());
	}

	protected ThemeDisplay getThemeDisplay() {
		return (ThemeDisplay)_renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
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
	private final IndexSearchPropsValues _indexSearchPropsValues;
	private final PortletPreferences _portletPreferences;
	private final PortletURLFactory _portletURLFactory;
	private QueryConfig _queryConfig;
	private Boolean _queryIndexingEnabled;
	private Integer _queryIndexingThreshold;
	private Integer _querySuggestionsDisplayThreshold;
	private Boolean _querySuggestionsEnabled;
	private Integer _querySuggestionsMax;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private String _searchConfiguration;
	private final SearchContainer<Document> _searchContainer;
	private final SearchContext _searchContext;
	private String _searchScopePreferenceString;
	private Boolean _viewInContext;

}