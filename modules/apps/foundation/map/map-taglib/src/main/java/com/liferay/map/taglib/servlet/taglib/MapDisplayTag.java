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
import com.liferay.map.util.MapProviderHelper;
import com.liferay.map.util.MapProviderTracker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class MapDisplayTag extends IncludeTag {

	public void setGeolocation(boolean geolocation) {
		_geolocation = geolocation;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
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

	public void setPoints(String points) {
		_points = points;
	}

	@Override
	protected void cleanUp() {
		_geolocation = false;
		_latitude = 0;
		_longitude = 0;
		_mapProviderKey = null;
		_name = null;
		_points = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-map:map:geolocation", _geolocation);
		request.setAttribute("liferay-map:map:latitude", _latitude);
		request.setAttribute("liferay-map:map:longitude", _longitude);
		request.setAttribute("liferay-map:map:mapProvider", _getMapProvider());
		request.setAttribute("liferay-map:map:name", _name);
		request.setAttribute("liferay-map:map:points", _points);
	}

	private MapProvider _getMapProvider() {
		MapProviderTracker mapProviderTracker =
			ServletContextUtil.getMapProviderTracker();

		String mapProviderKey = _getMapProviderKey();

		MapProvider mapProvider = null;

		if (Validator.isNotNull(mapProviderKey)) {
			mapProvider = mapProviderTracker.getMapProvider(mapProviderKey);
		}

		if (mapProvider == null) {
			List<MapProvider> mapProviders = new ArrayList(
				mapProviderTracker.getMapProviders());

			if (!mapProviders.isEmpty()) {
				mapProvider = mapProviders.get(0);
			}
		}

		return mapProvider;
	}

	private String _getMapProviderKey() {
		String mapProdiverKey = _mapProviderKey;

		if (Validator.isNull(mapProdiverKey)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			MapProviderHelper mapProviderHelper =
				ServletContextUtil.getMapProviderHelper();

			mapProdiverKey = mapProviderHelper.getMapProviderKey(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());
		}

		return mapProdiverKey;
	}

	private static final String _PAGE = "/map_display/page.jsp";

	private boolean _geolocation;
	private double _latitude;
	private double _longitude;
	private String _mapProviderKey;
	private String _name;
	private String _points;

}