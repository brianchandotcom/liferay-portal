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

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.constants.SearchPortletParameterNames;
import com.liferay.portal.search.web.facet.SearchFacet;
import com.liferay.portal.search.web.facet.util.SearchFacetTracker;
import com.liferay.portal.search.web.internal.portlet.SearchPortletSearchResultPreferences;
import com.liferay.portal.search.web.internal.search.request.SearchRequestImpl;
import com.liferay.portal.search.web.internal.search.request.SearchResponseImpl;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SearchDisplayContext {

	public SearchDisplayContext(
			RenderRequest renderRequest, PortletPreferences portletPreferences,
			Portal portal, Html html, Language language,
			FacetedSearcherManager facetedSearcherManager,
			IndexSearchPropsValues indexSearchPropsValues,
			PortletURLFactory portletURLFactory)
		throws PortletException {

		_renderRequest = renderRequest;
		_portletPreferences = portletPreferences;
		_indexSearchPropsValues = indexSearchPropsValues;
		_portletURLFactory = portletURLFactory;

		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		SearchResultPreferences searchResultPreferences =
			new SearchPortletSearchResultPreferences(
				portletPreferences, themeDisplaySupplier);

		_searchResultPreferences = searchResultPreferences;

		_themeDisplaySupplier = themeDisplaySupplier;

		String keywords = getKeywords();

		if (keywords == null) {
			_hits = null;
			_keywords = null;
			_queryString = null;
			_searchContainer = null;
			_searchContext = null;

			return;
		}

		_keywords = new Keywords(keywords);

		HttpServletRequest request = portal.getHttpServletRequest(
			_renderRequest);

		String emptyResultMessage = language.format(
			request, "no-results-were-found-that-matched-the-keywords-x",
			"<strong>" + html.escape(keywords) + "</strong>", false);

		SearchContainer<Document> searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultMessage);

		SearchContext searchContext = SearchContextFactory.getInstance(request);

		searchContext.setKeywords(_keywords.getKeywords());

		boolean luceneSyntax = isUseAdvancedSearchSyntax();

		if (!luceneSyntax) {
			luceneSyntax = _keywords.isLuceneSyntax();
		}

		if (luceneSyntax) {
			searchContext.setAttribute("luceneSyntax", Boolean.TRUE);
		}

		SearchRequestImpl searchRequestImpl = new SearchRequestImpl(
			() -> searchContext, searchContainerOptions -> searchContainer,
			facetedSearcherManager);

		searchRequestImpl.addSearchSettingsContributor(
			this::contributeSearchSettings);

		SearchResponseImpl searchResponseImpl = searchRequestImpl.search();

		_hits = searchResponseImpl.getHits();
		_queryString = searchResponseImpl.getQueryString();
		_searchContainer = searchResponseImpl.getSearchContainer();
		_searchContext = searchResponseImpl.getSearchContext();
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

	public Hits getHits() {
		return _hits;
	}

	public String getKeywords() {
		return ParamUtil.getString(
			_renderRequest, SearchPortletParameterNames.KEYWORDS, null);
	}

	public PortletURL getPortletURL() throws PortletException {
		return _portletURLFactory.getPortletURL();
	}

	public PortletURLFactory getPortletURLFactory() {
		return _portletURLFactory;
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
		_queryConfig.setHighlightEnabled(
			_searchResultPreferences.isHighlightEnabled());
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

	public String getQueryString() {
		return _queryString;
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

	public String[] getQueryTerms() {
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

	public SearchContainer<Document> getSearchContainer() {
		return _searchContainer;
	}

	public SearchContext getSearchContext() {
		return _searchContext;
	}

	public SearchResultPreferences getSearchResultPreferences() {
		return _searchResultPreferences;
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
		return _searchResultPreferences.isDisplayResultsInDocumentForm();
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

	public boolean isUseAdvancedSearchSyntax() {
		if (_useAdvancedSearchSyntax != null) {
			return _useAdvancedSearchSyntax;
		}

		_useAdvancedSearchSyntax = GetterUtil.getBoolean(
			_portletPreferences.getValue("useAdvancedSearchSyntax", null));

		return _useAdvancedSearchSyntax;
	}

	public boolean isViewInContext() {
		return _searchResultPreferences.isViewInContext();
	}

	protected void addEnabledSearchFacets(SearchSettings searchSettings) {
		ThemeDisplay themeDisplay = _themeDisplaySupplier.getThemeDisplay();

		long companyId = themeDisplay.getCompanyId();

		Collection<SearchFacet> searchFacets = getEnabledSearchFacets();

		Stream<SearchFacet> searchFacetsStream = searchFacets.stream();

		Stream<Optional<Facet>> facetOptionalsStream = searchFacetsStream.map(
			searchFacet -> createFacet(
				searchFacet, companyId, searchSettings.getSearchContext()));

		facetOptionalsStream.forEach(
			facetOptional -> facetOptional.ifPresent(searchSettings::addFacet));
	}

	protected void contributeSearchSettings(SearchSettings searchSettings) {
		searchSettings.setKeywords(_keywords.getKeywords());

		QueryConfig queryConfig = searchSettings.getQueryConfig();

		queryConfig.setCollatedSpellCheckResultEnabled(
			isCollatedSpellCheckResultEnabled());
		queryConfig.setCollatedSpellCheckResultScoresThreshold(
			getCollatedSpellCheckResultDisplayThreshold());
		queryConfig.setQueryIndexingEnabled(isQueryIndexingEnabled());
		queryConfig.setQueryIndexingThreshold(getQueryIndexingThreshold());
		queryConfig.setQuerySuggestionEnabled(isQuerySuggestionsEnabled());
		queryConfig.setQuerySuggestionScoresThreshold(
			getQuerySuggestionsDisplayThreshold());
		queryConfig.setQuerySuggestionsMax(getQuerySuggestionsMax());

		addEnabledSearchFacets(searchSettings);

		filterByThisSite(searchSettings);
	}

	protected Optional<Facet> createFacet(
		SearchFacet searchFacet, long companyId, SearchContext searchContext) {

		try {
			searchFacet.init(
				companyId, getSearchConfiguration(), searchContext);
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Optional.ofNullable(searchFacet.getFacet());
	}

	protected void filterByThisSite(SearchSettings searchSettings) {
		Optional<Long> groupIdOptional = getThisSiteGroupId();

		groupIdOptional.ifPresent(
			groupId -> {
				searchSettings.addCondition(
					new BooleanClauseImpl(
						new TermQueryImpl(
							Field.GROUP_ID, String.valueOf(groupId)),
						BooleanClauseOccur.MUST));
			});
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
		return _themeDisplaySupplier.getThemeDisplay();
	}

	protected Optional<Long> getThisSiteGroupId() {
		long searchScopeGroupId = getSearchScopeGroupId();

		if (searchScopeGroupId == 0) {
			return Optional.empty();
		}

		return Optional.of(searchScopeGroupId);
	}

	private Integer _collatedSpellCheckResultDisplayThreshold;
	private Boolean _collatedSpellCheckResultEnabled;
	private Boolean _displayMainQuery;
	private Boolean _displayOpenSearchResults;
	private Boolean _dlLinkToViewURL;
	private List<SearchFacet> _enabledSearchFacets;
	private final Hits _hits;
	private Boolean _includeSystemPortlets;
	private final IndexSearchPropsValues _indexSearchPropsValues;
	private final Keywords _keywords;
	private final PortletPreferences _portletPreferences;
	private final PortletURLFactory _portletURLFactory;
	private QueryConfig _queryConfig;
	private Boolean _queryIndexingEnabled;
	private Integer _queryIndexingThreshold;
	private final String _queryString;
	private Integer _querySuggestionsDisplayThreshold;
	private Boolean _querySuggestionsEnabled;
	private Integer _querySuggestionsMax;
	private final RenderRequest _renderRequest;
	private String _searchConfiguration;
	private final SearchContainer<Document> _searchContainer;
	private final SearchContext _searchContext;
	private final SearchResultPreferences _searchResultPreferences;
	private String _searchScopePreferenceString;
	private final ThemeDisplaySupplier _themeDisplaySupplier;
	private Boolean _useAdvancedSearchSyntax;

}