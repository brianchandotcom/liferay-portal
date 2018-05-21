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