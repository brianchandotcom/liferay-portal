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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.service.url.AbstractURLStreamHandlerService;

/**
 * @author Matthew Tambara
 */
public class WABWrapperUtil {

	public static String generateWABLocation(
		URL lpkgURL, Version version, String contextName) {

		return "webbundle:".concat(
			_generateFileWithQueryString(lpkgURL, version, contextName));
	}

	public static URL generateWABLocationURL(
			URL lpkgURL, Version version, String contextName,
			AbstractURLStreamHandlerService abstractURLStreamHandlerService)
		throws MalformedURLException {

		return new URL(
			"webbundle", null, -1,
			_generateFileWithQueryString(lpkgURL, version, contextName),
			abstractURLStreamHandlerService);
	}

	private static String _generateFileWithQueryString(
		URL lpkgURL, Version version, String contextName) {

		StringBundler sb = new StringBundler(10);

		sb.append(lpkgURL.getPath());
		sb.append(StringPool.QUESTION);
		sb.append(Constants.BUNDLE_VERSION);
		sb.append(StringPool.EQUAL);
		sb.append(version);
		sb.append("&Web-ContextPath=/");
		sb.append(contextName);
		sb.append("&protocol=lpkg");

		String query = lpkgURL.getQuery();

		if (Validator.isNotNull(query)) {
			sb.append(StringPool.AMPERSAND);
			sb.append(query);
		}

		return sb.toString();
	}

}