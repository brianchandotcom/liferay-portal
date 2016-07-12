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

package com.liferay.map.taglib.internal.servlet;

import com.liferay.map.util.MapProviderHelper;
import com.liferay.map.util.MapProviderTracker;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true)
public class ServletContextUtil {

	public static final MapProviderHelper getMapProviderHelper() {
		return _instance._getMapProviderHelper();
	}

	public static final MapProviderTracker getMapProviderTracker() {
		return _instance._getMapProviderTracker();
	}

	public static final ServletContext getServletContext() {
		return _instance._getServletContext();
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;
	}

	@Reference(unbind = "-")
	protected void setMapProviderHelper(MapProviderHelper mapProviderHelper) {
		_mapProviderHelper = mapProviderHelper;
	}

	@Reference(unbind = "-")
	protected void setMapProviderTracker(
		MapProviderTracker mapProviderTracker) {

		_mapProviderTracker = mapProviderTracker;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.map.taglib)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private MapProviderHelper _getMapProviderHelper() {
		return _mapProviderHelper;
	}

	private MapProviderTracker _getMapProviderTracker() {
		return _mapProviderTracker;
	}

	private ServletContext _getServletContext() {
		return _servletContext;
	}

	private static ServletContextUtil _instance;

	private MapProviderHelper _mapProviderHelper;
	private MapProviderTracker _mapProviderTracker;
	private ServletContext _servletContext;

}