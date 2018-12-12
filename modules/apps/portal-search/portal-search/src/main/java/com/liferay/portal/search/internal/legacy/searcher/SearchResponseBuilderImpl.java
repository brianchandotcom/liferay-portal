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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

/**
 * @author André de Oliveira
 */
public class SearchResponseBuilderImpl implements SearchResponseBuilder {

	public SearchResponseBuilderImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public SearchResponse build() {
		return new SearchResponseImpl();
	}

	@Override
	public SearchResponseBuilder requestString(String requestString) {
		_searchContext.setAttribute(_QUERY_STRING, requestString);
		_searchContext.setAttribute(_REQUEST_STRING, requestString);

		return this;
	}

	@Override
	public SearchResponseBuilder responseString(String responseString) {
		_searchContext.setAttribute(_RESPONSE_STRING, responseString);

		return this;
	}

	public class SearchResponseImpl implements SearchResponse {

		@Override
		public String getRequestString() {
			return GetterUtil.getString(
				_searchContext.getAttribute(_REQUEST_STRING));
		}

		@Override
		public String getResponseString() {
			return GetterUtil.getString(
				_searchContext.getAttribute(_RESPONSE_STRING));
		}

	}

	private static final String _QUERY_STRING = "queryString";

	private static final String _REQUEST_STRING = "request.string";

	private static final String _RESPONSE_STRING = "response.string";

	private final SearchContext _searchContext;

}