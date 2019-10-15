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

package com.liferay.portal.search.tuning.rankings.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author Wade Cao
 */
public class SearchRankingResponse {

	public List<Document> getDocuments() {
		return _documents;
	}

	public Optional<String> getKeywordsOptional() {
		return Optional.ofNullable(_keywords);
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