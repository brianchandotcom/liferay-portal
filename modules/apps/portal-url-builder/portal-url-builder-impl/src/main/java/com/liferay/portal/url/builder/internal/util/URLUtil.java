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

package com.liferay.portal.url.builder.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import org.osgi.framework.Bundle;

/**
 * @author Iván Zaera Avellón
 */
public class URLUtil {

	public static void appendParam(
		StringBundler sb, String name, String value) {

		boolean hasQueryString = false;

		for (int i = 0; i < sb.index(); i++) {
			String string = sb.stringAt(i);

			if (string.contains(StringPool.QUESTION)) {
				hasQueryString = true;

				break;
			}
		}

		if (!hasQueryString) {
			sb.append(StringPool.QUESTION);
		}
		else {
			sb.append(StringPool.AMPERSAND);
		}

		sb.append(name);
		sb.append(StringPool.EQUAL);
		sb.append(value);
	}

	public static void appendURL(
		StringBundler sb, String cdnHost, boolean ignoreCDNHost,
		boolean ignorePathProxy, String pathPrefix, String pathProxy,
		String relativeURL) {

		if (!ignoreCDNHost && !Validator.isBlank(cdnHost)) {
			sb.append(cdnHost);
		}

		if (!ignorePathProxy) {
			sb.append(pathProxy);
		}

		if (!Validator.isBlank(pathPrefix)) {
			if (!pathPrefix.startsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}

			if (pathPrefix.endsWith(StringPool.SLASH)) {
				sb.append(pathPrefix.substring(0, pathPrefix.length() - 1));
			}
			else {
				sb.append(pathPrefix);
			}
		}

		if (!relativeURL.startsWith(StringPool.SLASH)) {
			sb.append(StringPool.SLASH);
		}

		sb.append(relativeURL);
	}

	public static void appendURL(
		StringBundler sb, String cdnHost, boolean ignoreCDNHost,
		String pathPrefix, String pathProxy, String relativeURL) {

		appendURL(
			sb, cdnHost, ignoreCDNHost, false, pathPrefix, pathProxy,
			relativeURL);
	}

	public static void appendURL(
		StringBundler sb, String pathPrefix, String pathProxy,
		String relativeURL) {

		appendURL(sb, null, true, pathPrefix, pathProxy, relativeURL);
	}

	public static String getBundlePathPrefix(Bundle bundle, String pathModule) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String webContextPath = headers.get("Web-ContextPath");

		if (!webContextPath.endsWith(StringPool.SLASH)) {
			webContextPath += StringPool.SLASH;
		}

		return pathModule + webContextPath;
	}

	public static String removeParams(String url) {
		int i = url.indexOf(StringPool.QUESTION);

		if (i == -1) {
			return url;
		}

		return url.substring(0, i);
	}

}