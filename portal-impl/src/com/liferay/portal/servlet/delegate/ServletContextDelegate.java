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

package com.liferay.portal.servlet.delegate;

import com.liferay.portal.kernel.util.ProxyUtil;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class ServletContextDelegate {

	public static ServletContext create(ServletContext servletContext) {
		Class<?> clazz = servletContext.getClass();

		return ProxyUtil.newDelegateProxyInstance(
			clazz.getClassLoader(), ServletContext.class,
			new ServletContextDelegate(servletContext), servletContext);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ServletContext)) {
			return false;
		}

		ServletContext servletContext = (ServletContext)object;

		return servletContext.equals(_servletContext);
	}

	public String getContextPath() {
		return _contextPath;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	@Override
	public int hashCode() {
		return _servletContext.hashCode();
	}

	private ServletContextDelegate(ServletContext servletContext) {
		_servletContext = servletContext;

		_contextPath = servletContext.getContextPath();
		_servletContextName = servletContext.getServletContextName();
	}

	private final String _contextPath;
	private final ServletContext _servletContext;
	private final String _servletContextName;

}