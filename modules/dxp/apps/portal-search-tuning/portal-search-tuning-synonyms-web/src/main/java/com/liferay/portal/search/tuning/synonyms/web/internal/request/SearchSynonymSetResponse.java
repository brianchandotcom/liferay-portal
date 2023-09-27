/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class SearchSynonymSetResponse {

	public List<Document> getDocuments() {
		return _documents;
	}

	public String getKeywords() {
		return _keywords;
	}

	public int getPaginationDelta() {
		return _paginationDelta;
	}

	public int getPaginationStart() {
		return _paginationStart;
	}

	public String getQueryString() {
		return _searchResponse.getRequestString();
	}

	public SearchContainer<Document> getSearchContainer() {
		return _searchContainer;
	}

	public SearchHits getSearchHits() {
		return _searchHits;
	}

	public SearchResponse getSearchResponse() {
		return _searchResponse;
	}

	public int getTotalHits() {
		return _totalHits;
	}

	public void setDocuments(List<Document> documents) {
		_documents = documents;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setPaginationDelta(int paginationDelta) {
		_paginationDelta = paginationDelta;
	}

	public void setPaginationStart(int paginationStart) {
		_paginationStart = paginationStart;
	}

	public void setSearchContainer(SearchContainer<Document> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchHits(SearchHits searchHits) {
		_searchHits = searchHits;
	}

	public void setSearchResponse(SearchResponse searchResponse) {
		_searchResponse = searchResponse;
	}

	public void setTotalHits(int totalHits) {
		_totalHits = totalHits;
	}

	private List<Document> _documents;
	private String _keywords;
	private int _paginationDelta;
	private int _paginationStart;
	private SearchContainer<Document> _searchContainer;
	private SearchHits _searchHits;
	private SearchResponse _searchResponse;
	private int _totalHits;

}