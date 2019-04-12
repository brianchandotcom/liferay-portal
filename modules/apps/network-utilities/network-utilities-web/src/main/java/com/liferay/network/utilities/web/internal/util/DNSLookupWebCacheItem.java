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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.InetAddressUtil;
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

			String trimmedDomain = _domain.trim();

			char[] array = trimmedDomain.toCharArray();

			for (char c : array) {
				if ((c != '.') && !Character.isDigit(c)) {
					InetAddress inetAddress =
						InetAddressUtil.getInetAddressByName(_domain);

					results = inetAddress.getHostAddress();

					break;
				}
			}

			if (results == null) {
				InetAddress[] inetAddresses = InetAddress.getAllByName(_domain);

				if (inetAddresses.length == 0) {
					results = StringPool.BLANK;
				}
				else {
					StringBundler sb = new StringBundler(
						inetAddresses.length * 2 - 1);

					for (int i = 0; i < inetAddresses.length; i++) {
						sb.append(inetAddresses[i].getHostName());

						if ((i + 1) <= inetAddresses.length) {
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