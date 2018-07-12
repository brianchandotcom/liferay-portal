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

package com.liferay.apio.architect.impl.provider.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides utility functions for URL providers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class URLProviderUtil {

	/**
	 * Returns the server's URL based on the HTTP request and the forwarded
	 * header, to account for proxies.
	 *
	 * @param  httpServletRequest the current HTTP request
	 * @return the server's URL
	 * @review
	 */
	public static String getServerURL(HttpServletRequest httpServletRequest) {
		StringBuilder sb = new StringBuilder();

		String forwardedProto = httpServletRequest.getHeader(
			"X-Forwarded-Proto");

		if (forwardedProto != null) {
			sb.append(forwardedProto);
		}
		else {
			sb.append(httpServletRequest.getScheme());
		}

		sb.append("://");

		String forwardedHost = httpServletRequest.getHeader("X-Forwarded-Host");

		if (forwardedHost == null) {
			sb.append(httpServletRequest.getServerName());
			sb.append(":");
			sb.append(httpServletRequest.getServerPort());
		}
		else {
			sb.append(forwardedHost);
		}

		return sb.toString();
	}

	private URLProviderUtil() {
		throw new UnsupportedOperationException();
	}

}