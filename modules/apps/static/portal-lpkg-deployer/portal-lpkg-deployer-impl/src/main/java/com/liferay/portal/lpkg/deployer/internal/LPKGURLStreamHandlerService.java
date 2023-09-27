/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.internal;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

import org.osgi.service.url.AbstractURLStreamHandlerService;

/**
 * @author Shuyang Zhou
 */
public class LPKGURLStreamHandlerService
	extends AbstractURLStreamHandlerService {

	public LPKGURLStreamHandlerService(Map<String, URL> urls) {
		_urls = urls;
	}

	@Override
	public URLConnection openConnection(URL lpkgURL) throws IOException {
		URL url = _urls.get(lpkgURL.toExternalForm());

		if (url == null) {
			throw new IllegalArgumentException("Unknown LPKG URL " + lpkgURL);
		}

		return url.openConnection();
	}

	private final Map<String, URL> _urls;

}