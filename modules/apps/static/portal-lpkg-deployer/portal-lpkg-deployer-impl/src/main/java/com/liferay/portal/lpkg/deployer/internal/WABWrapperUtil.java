/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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