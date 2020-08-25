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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class UriInfoUtil {

	public static String getAbsolutePath(UriInfo uriInfo) {
		if (_isHttpsEnabled()) {
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();

			return String.valueOf(
				uriBuilder.scheme(
					"https"
				).build());
		}

		return String.valueOf(uriInfo.getAbsolutePath());
	}

	public static String getBasePath(UriInfo uriInfo) {
		UriBuilder uriBuilder = getBaseUriBuilder(uriInfo);

		return String.valueOf(uriBuilder.build());
	}

	public static UriBuilder getBaseUriBuilder(UriInfo uriInfo) {
		if (_isHttpsEnabled()) {
			UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

			return uriBuilder.scheme("https");
		}

		return uriInfo.getBaseUriBuilder();
	}

	private static boolean _isHttpsEnabled() {
		if (Http.HTTPS.equals(PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL)) ||
			Http.HTTPS.equals(
				PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL))) {

			return true;
		}

		return false;
	}

}