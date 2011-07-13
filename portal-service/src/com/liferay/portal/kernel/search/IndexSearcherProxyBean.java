/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Bruno Farache
 * @author Tina Tian
 * @author Raymond Augé
 * @author Michael C. Han
 */
public class IndexSearcherProxyBean
	extends BaseMultiDestinationProxyBean implements IndexSearcher {

	public Hits search(SearchContext searchContext, Query query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDestinationName(ProxyRequest proxyRequest) {
		Object[] arguments = proxyRequest.getArguments();

		SearchContext searchContext = (SearchContext)arguments[0];

		String searchEngineId = searchContext.getSearchEngineId();

		return SearchEngineUtil.getSearchReaderDestinationName(searchEngineId);
	}

}