/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.Closeable;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalFacadeServletRequestWrapperUtil {

	public static <T extends ServletRequest> ObjectValuePair<T, Closeable>
		inject(T request) {

		ServletRequestWrapper previousRequestWrapper = null;
		ServletRequest currentRequest = request;

		while (currentRequest != null) {
			if (!(currentRequest instanceof ServletRequestWrapper)) {
				break;
			}

			String currentRequestClassName =
				currentRequest.getClass().getName();

			if (_injectStopperClassNames.contains(currentRequestClassName)) {
				break;
			}

			previousRequestWrapper = (ServletRequestWrapper)currentRequest;

			ServletRequestWrapper servletRequestWrapper =
				(ServletRequestWrapper)currentRequest;

			currentRequest = servletRequestWrapper.getRequest();
		}

		ServletRequestWrapper servletRequestWrapper = null;

		if (currentRequest instanceof HttpServletRequest) {
			servletRequestWrapper =
				new ThreadLocalFacadeHttpServletRequestWrapper(
					previousRequestWrapper, (HttpServletRequest)currentRequest);
		}
		else {
			servletRequestWrapper =
				new ThreadLocalFacadeServletRequestWrapper(
					previousRequestWrapper, currentRequest);
		}

		if (previousRequestWrapper != null) {
			previousRequestWrapper.setRequest(servletRequestWrapper);
		}
		else {
			request = (T)servletRequestWrapper;
		}

		Closeable closeable = (Closeable)servletRequestWrapper;

		return new ObjectValuePair<T, Closeable>(request, closeable);
	}

	public void setInjectStopperClassNames(
		Set<String> injectStopperClassNames) {

		_injectStopperClassNames.clear();
		_injectStopperClassNames.addAll(injectStopperClassNames);
	}

	private static final Set<String> _injectStopperClassNames =
		new HashSet<String>();

}