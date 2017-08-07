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