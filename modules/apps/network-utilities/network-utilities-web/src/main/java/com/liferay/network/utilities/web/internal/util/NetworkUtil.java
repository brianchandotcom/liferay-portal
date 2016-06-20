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

package com.liferay.network.utilities.web.internal.util;

import com.liferay.network.utilities.web.internal.model.DNSLookup;
import com.liferay.network.utilities.web.internal.model.Whois;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class NetworkUtil {

	public static DNSLookup getDNSLookup(String domain) {
		WebCacheItem wci = new DNSLookupWebCacheItem(domain);

		return (DNSLookup)WebCachePoolUtil.get(
			NetworkUtil.class.getName() + ".dnslookup." + domain, wci);
	}

	public static Whois getWhois(String domain) {
		WebCacheItem wci = new WhoisWebCacheItem(domain);

		return (Whois)WebCachePoolUtil.get(
			NetworkUtil.class.getName() + ".whois." + domain, wci);
	}

}