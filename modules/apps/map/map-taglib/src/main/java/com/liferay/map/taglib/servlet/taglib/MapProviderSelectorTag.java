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

import com.liferay.map.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Julio Camarero
 */
public class MapProviderSelectorTag extends IncludeTag {

	public String getConfigurationPrefix() {
		return _configurationPrefix;
	}

	public String getMapProviderKey() {
		return _mapProviderKey;
	}

	public String getName() {
		return _name;
	}

	public void setConfigurationPrefix(String configurationPrefix) {
		_configurationPrefix = configurationPrefix;
	}

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
		super.cleanUp();

		_configurationPrefix = null;
		_mapProviderKey = null;
		_name = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-map:map-provider-selector:configurationPrefix",
			_configurationPrefix);
		httpServletRequest.setAttribute(
			"liferay-map:map-provider-selector:mapProviderKey",
			_mapProviderKey);
		httpServletRequest.setAttribute(
			"liferay-map:map-provider-selector:mapProviders",
			ServletContextUtil.getMapProviders());
		httpServletRequest.setAttribute(
			"liferay-map:map-provider-selector:name", _name);
	}

	private static final String _PAGE = "/map_provider_selector/page.jsp";

	private String _configurationPrefix;
	private String _mapProviderKey;
	private String _name;

}