/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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