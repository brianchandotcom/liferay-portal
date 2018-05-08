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

package com.liferay.apio.architect.application.internal.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.apio.architect.url.ServerURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * Creates the server's URL based on the HTTP request and the forwarded header,
 * to account for proxies.
 *
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class ServerURLProvider implements Provider<ServerURL> {

	@Override
	public ServerURL createContext(HttpServletRequest httpServletRequest) {
		return () -> {
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

			String forwardedHost = httpServletRequest.getHeader(
				"X-Forwarded-Host");

			if (forwardedHost == null) {
				sb.append(httpServletRequest.getServerName());
				sb.append(":");
				sb.append(httpServletRequest.getServerPort());
			}
			else {
				sb.append(forwardedHost);
			}

			sb.append(httpServletRequest.getContextPath());

			return sb.toString();
		};
	}

}