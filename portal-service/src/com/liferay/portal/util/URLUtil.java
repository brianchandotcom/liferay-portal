/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Igor Spasic
 */
public class URLUtil {

	public static URL normalizeURL(URL url) throws MalformedURLException {
		String urlString = url.toString();

		if (urlString.startsWith("vfsjar:")) {
			urlString = StringUtil.replaceFirst(urlString, "vfsjar:", "file:");
		}
		else if (urlString.startsWith("vfszip:")) {
			urlString = StringUtil.replaceFirst(urlString, "vfszip:", "file:");
		}
		else if (urlString.startsWith("vfsfile:")) {
			urlString = StringUtil.replaceFirst(urlString, "vfsfile:", "file:");
		}

		if (urlString.contains(".jar/")) {
			urlString = StringUtil.replaceFirst(urlString, ".jar/", ".jar!/");

			if (urlString.startsWith("file:")) {
				urlString = "jar:" + urlString;
			}
		}

		urlString = urlString.replace('\\', '/');

		int index = urlString.indexOf("file:");

		if (index != -1) {
			index += 5;

			if (urlString.charAt(index) != '/') {
				urlString =
					urlString.substring(0, index) + '/' +
					urlString.substring(index);
			}
		}

		return new URL(urlString);
	}

}