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

package com.liferay.portal.osgi.web.wab.generator.internal.handler;

import com.liferay.portal.osgi.web.wab.generator.WabGenerator;
import com.liferay.portal.osgi.web.wab.generator.internal.connection.WabURLConnection;

import java.net.URL;
import java.net.URLConnection;

import org.osgi.service.url.AbstractURLStreamHandlerService;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @author Gregory Amerson
 */
public class WabURLStreamHandlerService
	extends AbstractURLStreamHandlerService {

	public WabURLStreamHandlerService(
		ClassLoader classLoader, WabGenerator wabGenerator) {

		_classLoader = classLoader;
		_wabGenerator = wabGenerator;
	}

	@Override
	public URLConnection openConnection(URL url) {
		return new WabURLConnection(_classLoader, _wabGenerator, url);
	}

	private final ClassLoader _classLoader;
	private final WabGenerator _wabGenerator;

}