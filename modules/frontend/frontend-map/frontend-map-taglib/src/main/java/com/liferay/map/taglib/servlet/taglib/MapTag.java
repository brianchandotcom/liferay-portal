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

import com.liferay.frontend.map.api.MapProvider;
import com.liferay.frontend.map.api.util.MapProviderHelper;
import com.liferay.frontend.map.api.util.MapProviderTracker;
import com.liferay.map.taglib.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class MapTag extends IncludeTag {

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setGeolocation(boolean geolocation) {
		_geolocation = geolocation;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
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

	public void setProvider(String provider) {
		_provider = provider;
	}

	@Override
	protected void cleanUp() {
		_apiKey = null;
		_geolocation = false;
		_latitude = 0;
		_longitude = 0;
		_name = null;
		_points = null;
		_provider = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		validate(request);
		request.setAttribute("liferay-map:map:apiKey", _apiKey);
		request.setAttribute("liferay-map:map:geolocation", _geolocation);
		request.setAttribute("liferay-map:map:latitude", _latitude);
		request.setAttribute("liferay-map:map:longitude", _longitude);
		request.setAttribute("liferay-map:map:name", _name);
		request.setAttribute("liferay-map:map:points", _points);
		request.setAttribute("liferay-map:map:mapProvider", _getMapProvider());
	}

	private MapProvider _getMapProvider() {
		MapProviderTracker mapProviderTracker =
			ServletContextUtil.getMapProviderTracker();

		return mapProviderTracker.getMapProvider(_provider);
	}

	private String _getMapProviderKey(long companyId, long groupId) {
		MapProviderHelper mapProviderHelper =
			ServletContextUtil.getMapProviderHelper();

		return GetterUtil.getString(
				mapProviderHelper.getMapProviderKey(companyId, groupId),
				_DEFAULT_PROVIDER_KEY);
	}

	private void validate(HttpServletRequest request) {
		if (Validator.isNull(_apiKey) || Validator.isNull(_provider)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Company company = themeDisplay.getCompany();

			Group group = themeDisplay.getSiteGroup();

			if (group.isStagingGroup()) {
				group = group.getLiveGroup();
			}

			UnicodeProperties groupTypeSettings = new UnicodeProperties();

			if (group != null) {
				groupTypeSettings = group.getTypeSettingsProperties();
			}

			PortletPreferences companyPortletPreferences =
				PrefsPropsUtil.getPreferences(company.getCompanyId());

			if (Validator.isNull(_apiKey)) {
				_apiKey = groupTypeSettings.getProperty(
					_DEFAULT_API_KEY,
					companyPortletPreferences.getValue(_DEFAULT_API_KEY, null));
			}

			if (Validator.isNull(_provider)) {
				_provider = _getMapProviderKey(
					company.getCompanyId(), group.getGroupId());
			}
		}
	}

	private static final String _DEFAULT_API_KEY = "googleMapsAPIKey";

	private static final String _DEFAULT_PROVIDER_KEY = "Google";

	private static final String _PAGE = "/map/page.jsp";

	private String _apiKey;
	private boolean _geolocation;
	private double _latitude;
	private double _longitude;
	private String _name;
	private String _points;
	private String _provider;

}