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

package com.liferay.portlet.internal;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.filter.FilterConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterConfigImpl implements FilterConfig {

	public FilterConfigImpl(
		String filterName, PortletContext portletContext,
		Map<String, String> params) {

		_filterName = filterName;
		_portletContext = portletContext;
		_params = params;
	}

	@Override
	public String getFilterName() {
		return _filterName;
	}

	@Override
	public String getInitParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _params.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_params.keySet());
	}

	@Override
	public PortletContext getPortletContext() {
		return _portletContext;
	}

	private final String _filterName;
	private final Map<String, String> _params;
	private final PortletContext _portletContext;

}