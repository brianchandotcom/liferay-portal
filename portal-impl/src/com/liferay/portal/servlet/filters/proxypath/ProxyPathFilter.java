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

package com.liferay.portal.servlet.filters.proxypath;

import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PropsValues;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jonathan McCann
 */
public class ProxyPathFilter extends BasePortalFilter {

	@Override
	public void init(FilterConfig filterConfig) {
		if (Validator.isNotNull(PropsValues.PORTAL_PROXY_PATH)) {
			_enabled = true;
		}
	}

	@Override
	public boolean isFilterEnabled() {
		return _enabled;
	}

	@Override
	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		return _enabled;
	}

	@Override
	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		filterChain.doFilter(new ProxyPathRequestWrapper(request), response);
	}

	private boolean _enabled;

	private class ProxyPathRequestWrapper extends HttpServletRequestWrapper {

		public ProxyPathRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public StringBuffer getRequestURL() {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)super.getRequest();

			StringBuffer sb = new StringBuffer();

			sb.append(PortalUtil.getPortalURL(httpServletRequest));
			sb.append(PortalUtil.getPathContext());
			sb.append(httpServletRequest.getRequestURI());

			return sb;
		}

	}

}