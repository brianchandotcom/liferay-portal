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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexDefinition;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wade Cao
 */
public class SearchRankingRequest {

	public SearchRankingRequest(
		HttpServletRequest httpServletRequest, Queries queries,
		SearchContainer searchContainer,
		SearchEngineAdapter searchEngineAdapter) {

		_httpServletRequest = httpServletRequest;
		_queries = queries;
		_searchContext = SearchContextFactory.getInstance(httpServletRequest);
		_searchContainer = searchContainer;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public SearchRankingResponse search() {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		String keywords = _searchContext.getKeywords();

		if ((keywords != null) && (keywords != StringPool.BLANK)) {
			searchSearchRequest.setQuery(_queries.match("keywords", keywords));
		}
		else {
			searchSearchRequest.setQuery(_queries.matchAll());
		}

		_searchContext.setSorts(_getSort());

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(RankingIndexDefinition.INDEX_NAME);
		searchSearchRequest.setSize(_searchContainer.getDelta());
		searchSearchRequest.setSorts(_searchContext.getSorts());
		searchSearchRequest.setStart(_searchContainer.getStart());

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchRankingResponse searchRankingResponse =
			new SearchRankingResponse();

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		searchRankingResponse.setSearchHits(searchHits);
		searchRankingResponse.setTotalHits((int)searchHits.getTotalHits());

		return searchRankingResponse;
	}

	private Sort _getSort() {
		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "keywords");
		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		Sort sort = null;

		boolean orderByAsc = true;

		if (Objects.equals(orderByType, "asc")) {
			orderByAsc = false;
		}

		if (Objects.equals(orderByCol, "modified-date")) {
			sort = new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, orderByAsc);
		}
		else {
			sort = new Sort(orderByCol, orderByAsc);
		}

		return sort;
	}

	private final HttpServletRequest _httpServletRequest;
	private final Queries _queries;
	private final SearchContainer _searchContainer;
	private final SearchContext _searchContext;
	private final SearchEngineAdapter _searchEngineAdapter;

}