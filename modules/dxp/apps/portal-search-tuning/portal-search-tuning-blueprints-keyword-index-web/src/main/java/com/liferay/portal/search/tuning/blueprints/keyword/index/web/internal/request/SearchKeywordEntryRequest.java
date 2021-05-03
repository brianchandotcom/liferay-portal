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

package com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.search.tuning.blueprints.keyword.index.constants.KeywordEntryStatus;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.KeywordEntry;
import com.liferay.portal.search.tuning.blueprints.keyword.index.index.name.KeywordIndexName;
import com.liferay.portal.search.tuning.blueprints.keyword.index.web.internal.index.KeywordEntryFields;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class SearchKeywordEntryRequest {

	public SearchKeywordEntryRequest(
		KeywordIndexName keywordIndexName,
		HttpServletRequest httpServletRequest, Queries queries,
		SearchContainer<KeywordEntry> searchContainer,
		SearchEngineAdapter searchEngineAdapter, Sorts sorts,
		KeywordEntryStatus keywordEntryStatus) {

		_keywordIndexName = keywordIndexName;
		_httpServletRequest = httpServletRequest;
		_queries = queries;
		_searchContainer = searchContainer;
		_searchEngineAdapter = searchEngineAdapter;
		_sorts = sorts;
		_keywordEntryStatus = keywordEntryStatus;

		_searchContext = SearchContextFactory.getInstance(httpServletRequest);
	}

	public SearchKeywordEntryResponse search() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(_keywordIndexName.getIndexName());
		searchSearchRequest.setPreferLocalCluster(false);
		searchSearchRequest.setQuery(_getQuery());
		searchSearchRequest.setSize(_searchContainer.getDelta());
		searchSearchRequest.setSorts(_getSorts());
		searchSearchRequest.setStart(_searchContainer.getStart());

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchKeywordEntryResponse searchQueryStringResponse =
			new SearchKeywordEntryResponse();

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		searchQueryStringResponse.setSearchHits(searchHits);
		searchQueryStringResponse.setTotalHits((int)searchHits.getTotalHits());

		return searchQueryStringResponse;
	}

	private Query _getQuery() {
		String keywords = _searchContext.getKeywords();

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (!Validator.isBlank(keywords)) {
			booleanQuery.addMustQueryClauses(
				_queries.match(KeywordEntryFields.CONTENT, keywords));
		}
		else {
			booleanQuery.addMustQueryClauses(_queries.matchAll());
		}

		if (_keywordEntryStatus != null) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					KeywordEntryFields.STATUS, _keywordEntryStatus.name()));
		}

		return booleanQuery;
	}

	private Collection<Sort> _getSorts() {
		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", KeywordEntryFields.CREATED);
		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "desc");

		SortOrder sortOrder = SortOrder.ASC;

		if (Objects.equals(orderByType, "desc")) {
			sortOrder = SortOrder.DESC;
		}

		return Arrays.asList(_sorts.field(orderByCol, sortOrder));
	}

	private final HttpServletRequest _httpServletRequest;
	private final KeywordEntryStatus _keywordEntryStatus;
	private final KeywordIndexName _keywordIndexName;
	private final Queries _queries;
	private final SearchContainer<KeywordEntry> _searchContainer;
	private final SearchContext _searchContext;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final Sorts _sorts;

}