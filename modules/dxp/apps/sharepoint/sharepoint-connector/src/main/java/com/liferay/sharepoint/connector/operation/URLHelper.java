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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointRuntimeException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Iván Zaera
 */
public class URLHelper {

	public URL escapeURL(URL url) {
		String urlString = url.toString();

		String escapedURLString = urlString.replaceAll(StringPool.SPACE, "%20");

		try {
			return new URL(escapedURLString);
		}
		catch (MalformedURLException murle) {
			throw new SharepointRuntimeException(
				"Unable to parse escaped URL " + escapedURLString, murle);
		}
	}

	public URL toURL(String urlString) throws SharepointRuntimeException {
		try {
			return new URL(urlString);
		}
		catch (MalformedURLException murle) {
			throw new SharepointRuntimeException(
				"Unable to parse URL '" + urlString + "'", murle);
		}
	}

}