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

import com.liferay.network.utilities.web.model.Whois;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;

import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;

/**
 * @author Brian Wing Shun Chan
 */
public class WhoisWebCacheItem implements WebCacheItem {

	public static final String WHOIS_SERVER = "whois.geektools.com";

	public static final int WHOIS_SERVER_PORT = 43;

	public WhoisWebCacheItem(String domain) {
		_domain = domain;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		Whois whois = null;

		try (Socket socket = new Socket(WHOIS_SERVER, WHOIS_SERVER_PORT);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(socket.getInputStream()))) {

			PrintStream out = new PrintStream(socket.getOutputStream());

			out.println(_domain);

			StringBundler sb = new StringBundler();
			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith("Results ")) {
					break;
				}

				sb.append(line).append("\n");
			}

			whois = new Whois(
				_domain,
				StringUtil.replace(sb.toString().trim(), "\n\n", "\n"));
		}
		catch (Exception e) {
			throw new WebCacheException(_domain + " " + e.toString());
		}

		return whois;
	}

	@Override
	public long getRefreshTime() {
		return _REFRESH_TIME;
	}

	private static final long _REFRESH_TIME = Time.DAY;

	private final String _domain;

}