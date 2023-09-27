/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.repository.connector.SharepointRuntimeException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Iván Zaera
 */
public final class URLUtil {

	public static URL escapeURL(URL url) {
		String urlString = url.toString();

		String escapedURLString = urlString.replaceAll(StringPool.SPACE, "%20");

		try {
			return new URL(escapedURLString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new SharepointRuntimeException(
				"Unable to parse escaped URL " + escapedURLString,
				malformedURLException);
		}
	}

	public static URL toURL(String urlString)
		throws SharepointRuntimeException {

		try {
			return new URL(urlString);
		}
		catch (MalformedURLException malformedURLException) {
			throw new SharepointRuntimeException(
				"Unable to parse URL '" + urlString + "'",
				malformedURLException);
		}
	}

}