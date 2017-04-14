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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext;

import java.io.Serializable;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class SearchResultsPortletDisplayContext implements Serializable {

	public List<Document> getDocuments() {
		return _documents;
	}

	public String getKeywords() {
		return _keywords;
	}

	public SearchContainer<Document> getSearchContainer() {
		return _searchContainer;
	}

	public SearchResultSummaryDisplayContext
		getSearchResultSummaryDisplayContext(Document document) {

		return _searchResultsSummariesHolder.get(document);
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

	public void setTotalHits(int totalHits) {
		_totalHits = totalHits;
	}

	private List<Document> _documents;
	private String _keywords;
	private boolean _renderNothing;
	private SearchContainer<Document> _searchContainer;
	private SearchResultsSummariesHolder _searchResultsSummariesHolder;
	private int _totalHits;

}