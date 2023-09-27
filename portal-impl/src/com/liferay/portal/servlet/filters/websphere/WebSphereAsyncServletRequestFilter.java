/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (ReflectiveOperationException reflectiveOperationException) {
				Log log = getLog();

				log.error(
					reflectiveOperationException, reflectiveOperationException);
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