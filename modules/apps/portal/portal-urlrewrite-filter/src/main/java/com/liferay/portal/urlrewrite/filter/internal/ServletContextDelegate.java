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

package com.liferay.portal.urlrewrite.filter.internal;

import java.io.InputStream;

import java.util.Objects;

import javax.servlet.ServletContext;

import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * @author Shuyang Zhou
 */
public class ServletContextDelegate {

	public ServletContextDelegate(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public InputStream getResourceAsStream(String path) {
		if (Objects.equals(path, UrlRewriteFilter.DEFAULT_WEB_CONF_PATH)) {
			return ServletContextDelegate.class.getResourceAsStream(
				"/urlrewrite.xml");
		}

		return _servletContext.getResourceAsStream(path);
	}

	private final ServletContext _servletContext;

}