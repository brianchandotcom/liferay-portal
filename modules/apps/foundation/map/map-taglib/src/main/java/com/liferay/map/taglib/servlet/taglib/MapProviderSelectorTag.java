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

package com.liferay.map.taglib.servlet.taglib;

import com.liferay.map.MapProvider;
import com.liferay.map.taglib.internal.servlet.ServletContextUtil;
import com.liferay.map.util.MapProviderTracker;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Julio Camarero
 */
public class MapProviderSelectorTag extends IncludeTag {

	public void setMapProviderKey(String mapProviderKey) {
		_mapProviderKey = mapProviderKey;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		_mapProviderKey = null;
		_name = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-map:map-provider-selector:mapProviderKey",
			_mapProviderKey);
		request.setAttribute(
			"liferay-map:map-provider-selector:mapProviders",
			_getMapProviders());
		request.setAttribute("liferay-map:map-provider-selector:name", _name);
	}

	private List<MapProvider> _getMapProviders() {
		MapProviderTracker mapProviderTracker =
			ServletContextUtil.getMapProviderTracker();

		return new ArrayList<>(mapProviderTracker.getMapProviders());
	}

	private static final String _PAGE = "/map_provider_selector/page.jsp";

	private String _mapProviderKey;
	private String _name;

}