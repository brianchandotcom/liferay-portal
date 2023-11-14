/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.io.StreamUtil;

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
				catch (IOException ioException) {
				}
			}
		}
	}

	public static byte[] toByteArray(URL url) throws IOException {
		return StreamUtil.toByteArray(url.openStream());
	}

	public static String toString(URL url) throws IOException {
		return StreamUtil.toString(url.openStream());
	}

}