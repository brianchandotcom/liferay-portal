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

package com.liferay.portal.kernel.url;

import com.liferay.petra.reflect.ReflectionUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ServletContextURLContainer implements URLContainer {

	public ServletContextURLContainer(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public URL getResource(String name) {
		try {
			return _servletContext.getResource(name);
		}
		catch (MalformedURLException murle) {
			return ReflectionUtil.throwException(murle);
		}
	}

	@Override
	public Set<String> getResources(String path) {
		return _servletContext.getResourcePaths(path);
	}

	private final ServletContext _servletContext;

}