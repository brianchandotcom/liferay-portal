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

package com.liferay.saml.opensaml.integration.internal.transport;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Tomas Polesovsky
 */
public class ProxyPathRequestWrapper extends HttpServletRequestWrapper {

	public ProxyPathRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getRemoteAddr() {
		HttpServletRequest request = (HttpServletRequest)getRequest();

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.WEB_SERVER_FORWARDED_HOST_ENABLED))) {

			String headerString = request.getHeader(
				HttpHeaders.X_FORWARDED_FOR);

			if (Validator.isNotNull(headerString)) {
				return StringUtil.split(headerString)[0];
			}
		}

		return request.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return PortalUtil.getForwardedHost((HttpServletRequest)getRequest());
	}

	@Override
	public StringBuffer getRequestURL() {
		StringBuffer sb = new StringBuffer();

		HttpServletRequest request = (HttpServletRequest)super.getRequest();

		sb.append(PortalUtil.getPortalURL(request));

		String pathContext = PortalUtil.getPathContext();
		String uri = request.getRequestURI();

		if (!uri.startsWith(pathContext)) {
			sb.append(pathContext);
		}

		sb.append(uri);

		return sb;
	}

	@Override
	public boolean isSecure() {
		return PortalUtil.isSecure((HttpServletRequest)getRequest());
	}

}