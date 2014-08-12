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
		inject(T servletRequest) {

		ServletRequestWrapper previousServletRequestWrapper = null;
		ServletRequest currentServletRequest = servletRequest;

		while (currentServletRequest != null) {
			if (!(currentServletRequest instanceof ServletRequestWrapper)) {
				break;
			}

			Class<?> clazz = currentServletRequest.getClass();

			if (_stopperClassNames.contains(clazz.getName())) {
				break;
			}

			previousServletRequestWrapper =
				(ServletRequestWrapper)currentServletRequest;

			ServletRequestWrapper servletRequestWrapper =
				(ServletRequestWrapper)currentServletRequest;

			currentServletRequest = servletRequestWrapper.getRequest();
		}

		ServletRequestWrapper servletRequestWrapper = null;

		if (currentServletRequest instanceof HttpServletRequest) {
			servletRequestWrapper =
				new ThreadLocalFacadeHttpServletRequestWrapper(
					previousServletRequestWrapper,
					(HttpServletRequest)currentServletRequest);
		}
		else {
			servletRequestWrapper = new ThreadLocalFacadeServletRequestWrapper(
				previousServletRequestWrapper, currentServletRequest);
		}

		if (previousServletRequestWrapper != null) {
			previousServletRequestWrapper.setRequest(servletRequestWrapper);
		}
		else {
			servletRequest = (T)servletRequestWrapper;
		}

		Closeable closeable = (Closeable)servletRequestWrapper;

		return new ObjectValuePair<T, Closeable>(servletRequest, closeable);
	}

	public void setStopperClassNames(Set<String> stopperClassNames) {
		_stopperClassNames.clear();

		_stopperClassNames.addAll(stopperClassNames);
	}

	private static final Set<String> _stopperClassNames = new HashSet<String>();

}