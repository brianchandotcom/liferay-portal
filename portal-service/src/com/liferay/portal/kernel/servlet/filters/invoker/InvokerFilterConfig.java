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

package com.liferay.portal.kernel.servlet.filters.invoker;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilterConfig implements FilterConfig {

	public InvokerFilterConfig(
		ServletContext servletContext, String filterName,
		Map<String, String> initParameterMap) {

		_servletContext = servletContext;
		_filterName = filterName;
		_initParameterMap = initParameterMap;
	}

	@Override
	public String getFilterName() {
		return _filterName;
	}

	@Override
	public String getInitParameter(String key) {
		return _initParameterMap.get(key);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return new Enumeration<String>() {

			public boolean hasMoreElements() {
				return _keys.hasNext();
			}

			public String nextElement() {
				return _keys.next();
			}

			private Iterator<String> _keys =
				_initParameterMap.keySet().iterator();

		};
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	private String _filterName;
	private Map<String, String> _initParameterMap;
	private ServletContext _servletContext;

}