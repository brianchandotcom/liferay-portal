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

package com.liferay.vulcan.jaxrs.writer.json.internal;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.vulcan.provider.ServerURLProvider;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * Creates the URL of the server based on the http request and the forwarded
 * header to account for proxies
 *
 * @author Javier Gamarra
 */
@Component(immediate = true, service = ServerURLProvider.class)
public class ServerURLProviderImpl implements ServerURLProvider {

	/**
	 * Returns the original url of the request without proxy redirection
	 *
	 * @param  httpServletRequest the http request
	 * @return an URL built based on the forwarded host header if exist or the
	 *         serverName
	 */
	public String getServerURL(HttpServletRequest httpServletRequest) {
		StringBuilder sb = new StringBuilder(httpServletRequest.getScheme());

		sb.append(Http.PROTOCOL_DELIMITER);

		String forwardedHost = httpServletRequest.getHeader(
			_FORWARDED_HOST_HEADER);

		if (forwardedHost == null) {
			sb.append(httpServletRequest.getServerName());
			sb.append(StringPool.COLON);
			sb.append(httpServletRequest.getServerPort());
		}
		else {
			sb.append(forwardedHost);
		}

		sb.append(httpServletRequest.getContextPath());

		return sb.toString();
	}

	private static final String _FORWARDED_HOST_HEADER = "X-Forwarded-Host";

}