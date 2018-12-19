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

package com.liferay.portal.search.internal.legacy.searcher;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

/**
 * @author André de Oliveira
 */
public class SearchRequestBuilderImpl implements SearchRequestBuilder {

	public SearchRequestBuilderImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public SearchRequestBuilder addSelectedFieldNames(
		String... selectedFieldNames) {

		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(selectedFieldNames);

		return this;
	}

	@Override
	public SearchRequest build() {
		return new SearchRequestImpl();
	}

	@Override
	public SearchRequestBuilder explain(boolean explain) {
		_searchContext.setAttribute(_EXPLAIN, Boolean.valueOf(explain));

		return this;
	}

	@Override
	public SearchRequestBuilder includeResponseString(
		boolean includeResponseString) {

		_searchContext.setAttribute(
			_INCLUDE_RESPONSE_STRING, Boolean.valueOf(includeResponseString));

		return this;
	}

	@Override
	public SearchRequestBuilder rescoreQuery(Query rescoreQuery) {
		_searchContext.setAttribute(_RESCORE_QUERY, rescoreQuery);

		return this;
	}

	public class SearchRequestImpl implements SearchRequest {

		@Override
		public Query getRescoreQuery() {
			return (Query)_searchContext.getAttribute(_RESCORE_QUERY);
		}

		public SearchContext getSearchContext() {
			return _searchContext;
		}

		@Override
		public boolean isExplain() {
			return GetterUtil.getBoolean(_searchContext.getAttribute(_EXPLAIN));
		}

		@Override
		public boolean isIncludeResponseString() {
			return GetterUtil.getBoolean(
				_searchContext.getAttribute(_INCLUDE_RESPONSE_STRING));
		}

	}

	private static final String _EXPLAIN = "explain";

	private static final String _INCLUDE_RESPONSE_STRING =
		"include.response.string";

	private static final String _RESCORE_QUERY = "rescore.query";

	private final SearchContext _searchContext;

}