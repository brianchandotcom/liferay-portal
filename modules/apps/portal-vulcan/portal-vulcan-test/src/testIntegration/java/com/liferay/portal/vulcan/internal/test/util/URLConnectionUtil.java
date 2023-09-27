/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.test.util;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

/**
 * @author Brian Wing Shun Chan
 */
public class URLConnectionUtil {

	public static URLConnection createURLConnection(String urlString)
		throws IOException {

		return createURLConnection(new URL(urlString));
	}

	public static URLConnection createURLConnection(URL url)
		throws IOException {

		URLConnection urlConnection = url.openConnection();

		Base64.Encoder encoder = Base64.getEncoder();

		String encodedUserNameAndPassword = encoder.encodeToString(
			"test@liferay.com:test".getBytes(StandardCharsets.UTF_8));

		urlConnection.setRequestProperty(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		return urlConnection;
	}

	public static InputStream getInputStream(String urlString)
		throws IOException {

		URLConnection urlConnection = createURLConnection(urlString);

		urlConnection.connect();

		return urlConnection.getInputStream();
	}

	public static String read(String urlString) throws IOException {
		return StringUtil.read(getInputStream(urlString));
	}

}