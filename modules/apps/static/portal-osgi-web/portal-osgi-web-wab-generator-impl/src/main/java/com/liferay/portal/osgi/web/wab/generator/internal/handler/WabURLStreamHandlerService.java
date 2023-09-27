/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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