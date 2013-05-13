/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicServletConfig implements ServletConfig {

	public DynamicServletConfig(
		ServletConfig servletConfig, Map<String, String> params) {

		_servletConfig = servletConfig;
		_params = params;
	}

	@Override
	public String getInitParameter(String name) {
		return _params.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_params.keySet());
	}

	@Override
	public ServletContext getServletContext() {
		return _servletConfig.getServletContext();
	}

	@Override
	public String getServletName() {
		return _servletConfig.getServletName();
	}

	private Map<String, String> _params;
	private ServletConfig _servletConfig;

}