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

package com.liferay.portal.servlet.filters.websphere;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;

/**
 * @author Tina Tian
 */
public class WebSphereAsyncServletRequestFilter extends BasePortalFilter {

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		super.doFilter(servletRequest, servletResponse, filterChain);

		while (servletRequest instanceof ServletRequestWrapper) {
			ServletRequestWrapper servletRequestWrapper =
				(ServletRequestWrapper)servletRequest;

			servletRequest = servletRequestWrapper.getRequest();
		}

		Class<?> clazz = servletRequest.getClass();

		if (Objects.equals(
				clazz.getName(),
				"com.ibm.ws.webcontainer.srt.SRTServletRequest")) {

			try {
				if (_setAsyncSupportedMethod == null) {
					_setAsyncSupportedMethod = clazz.getMethod(
						"setAsyncSupported", boolean.class);
				}

				_setAsyncSupportedMethod.invoke(servletRequest, true);
			}
			catch (ReflectiveOperationException roe) {
				Log log = getLog();

				log.error(roe, roe);
			}
		}
	}

	@Override
	public boolean isFilterEnabled() {
		if (!ServerDetector.isWebSphere()) {
			return false;
		}

		return super.isFilterEnabled();
	}

	private Method _setAsyncSupportedMethod;

}