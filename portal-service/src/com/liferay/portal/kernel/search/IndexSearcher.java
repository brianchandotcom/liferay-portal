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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
@MessagingProxy(mode = ProxyMode.SYNC)
public interface IndexSearcher extends QuerySuggester {

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #search(SearchContext,
	 *             Query)}
	 */
	public Hits search(
			String searchEngineId, long companyId, Query query, Sort[] sort,
			int start, int end)
		throws SearchException;

}