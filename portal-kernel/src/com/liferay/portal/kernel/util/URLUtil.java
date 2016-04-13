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

package com.liferay.portal.kernel.util;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author Shuyang Zhou
 */
public class URLUtil {

	public static long getLastModifiedTime(URL url) throws IOException {
		URLConnection urlConnection = null;

		try {
			urlConnection = url.openConnection();

			return urlConnection.getLastModified();
		}
		finally {
			if (urlConnection != null) {
				try {
					InputStream inputStream = urlConnection.getInputStream();

					inputStream.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	/**
	 * Checks if the provided URL is either root relative or fully qualified.
	 * Non-root relative URLs have little meaning in a portal context.
	 *
	 * @param uRL
	 * @return
	 */
	public static boolean isValidURL(String uRL) {
		if (uRL.charAt(0) == '/') {
			return true;
		}

		int queryStringStart = uRL.indexOf('?');

		if (queryStringStart == -1) {
			queryStringStart = uRL.length();
		}

		int protocolEnd = uRL.lastIndexOf("://", queryStringStart);

		if (protocolEnd == -1) {
			return false;
		}

		return true;
	}

}