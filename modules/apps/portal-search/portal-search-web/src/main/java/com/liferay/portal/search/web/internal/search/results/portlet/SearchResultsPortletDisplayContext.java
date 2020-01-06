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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;
import com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class SearchResultsPortletDisplayContext implements Serializable {

	public SearchResultsPortletDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_searchResultsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SearchResultsPortletInstanceConfiguration.class);
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_searchResultsPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public List<Document> getDocuments() {
		return _documents;
	}

	public String getKeywords() {
		return _keywords;
	}

	public SearchContainer<Document> getSearchContainer() {
		return _searchContainer;
	}

	public SearchResultsPortletInstanceConfiguration
		getSearchResultsPortletInstanceConfiguration() {

		return _searchResultsPortletInstanceConfiguration;
	}

	public SearchResultSummaryDisplayContext
		getSearchResultSummaryDisplayContext(Document document) {

		return _searchResultsSummariesHolder.get(document);
	}

	public List<SearchResultSummaryDisplayContext>
		getSearchResultSummaryDisplayContexts() {

		if (_searchResultSummaryDisplayContexts != null) {
			return _searchResultSummaryDisplayContexts;
		}

		return new ArrayList<>();
	}

	public int getTotalHits() {
		return _totalHits;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setDocuments(List<Document> documents) {
		_documents = documents;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setSearchContainer(SearchContainer<Document> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchResultsSummariesHolder(
		SearchResultsSummariesHolder searchResultsSummariesHolder) {

		_searchResultsSummariesHolder = searchResultsSummariesHolder;
	}

	public void setSearchResultSummaryDisplayContexts(
		List<SearchResultSummaryDisplayContext>
			searchResultSummaryDisplayContexts) {

		_searchResultSummaryDisplayContexts =
			searchResultSummaryDisplayContexts;
	}

	public void setTotalHits(int totalHits) {
		_totalHits = totalHits;
	}

	public List<SearchResultSummaryDisplayContext>
		translateSearchResultSummaryDisplayContexts(List<Document> documents) {

		List<SearchResultSummaryDisplayContext>
			searchResultSummaryDisplayContexts = new ArrayList<>();

		for (Document doc : documents) {
			searchResultSummaryDisplayContexts.add(
				Objects.requireNonNull(
					getSearchResultSummaryDisplayContext(doc)));
		}

		return searchResultSummaryDisplayContexts;
	}

	private long _displayStyleGroupId;
	private List<Document> _documents;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private boolean _renderNothing;
	private SearchContainer<Document> _searchContainer;
	private final SearchResultsPortletInstanceConfiguration
		_searchResultsPortletInstanceConfiguration;
	private SearchResultsSummariesHolder _searchResultsSummariesHolder;
	private List<SearchResultSummaryDisplayContext>
		_searchResultSummaryDisplayContexts;
	private int _totalHits;

}