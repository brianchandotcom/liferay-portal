/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.messaging.proxy.BaseMultiDestinationProxyBean;
import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Tina Tian
 * @author Raymond Augé
 */
public class IndexSearcherProxyBean
	extends BaseMultiDestinationProxyBean implements IndexSearcher {

	@Override
	public String getDestinationName(ProxyRequest proxyRequest) {
		Object[] arguments = proxyRequest.getArguments();

		String searchEngineId = null;

		if (arguments[0] instanceof SearchContext) {
			SearchContext searchContext = (SearchContext)arguments[0];

			searchEngineId = searchContext.getSearchEngineId();
		}
		else {
			searchEngineId = (String)arguments[0];
		}

		return SearchEngineUtil.getSearchReaderDestinationName(searchEngineId);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(
		String searchEngineId, long companyId, Query query, Sort[] sort,
		int start, int end) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String spellCheckKeywords(SearchContext searchContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		throw new UnsupportedOperationException();
	}

}