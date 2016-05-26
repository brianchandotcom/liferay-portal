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

package com.liferay.network.utilities.web.util;

import com.liferay.network.utilities.web.model.DNSLookup;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;

import java.net.InetAddress;

/**
 * @author Brian Wing Shun Chan
 */
public class DNSLookupWebCacheItem implements WebCacheItem {

	public DNSLookupWebCacheItem(String domain) {
		_domain = domain;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		DNSLookup dnsLookup = null;

		try {
			String results = null;

			char[] array = _domain.trim().toCharArray();

			for (int i = 0; i < array.length; i++) {
				if ((array[i] != '.') && !Character.isDigit(array[i])) {
					InetAddress ia = InetAddress.getByName(_domain);

					results = ia.getHostAddress();

					break;
				}
			}

			if (results == null) {
				InetAddress[] ia = InetAddress.getAllByName(_domain);

				if (ia.length == 0) {
					results = StringPool.BLANK;
				}
				else {
					StringBundler sb = new StringBundler(ia.length * 2 - 1);

					for (int i = 0; i < ia.length; i++) {
						sb.append(ia[i].getHostName());

						if ((i + 1) <= ia.length) {
							sb.append(StringPool.COMMA);
						}
					}

					results = sb.toString();
				}
			}

			dnsLookup = new DNSLookup(_domain, results);
		}
		catch (Exception e) {
			throw new WebCacheException(_domain + " " + e.toString());
		}

		return dnsLookup;
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.DAY;

	private final String _domain;

}