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

package com.liferay.map.google.maps.internal.display.context;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class GoogleMapsDisplayContext {

	public GoogleMapsDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public String getCompanyGoogleMapsAPIKey() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

		return companyPortletPreferences.getValue("googleMapsAPIKey", null);
	}

	public String getGoogleMapsAPIKey() {
		if (_googleMapsAPIKey != null) {
			return _googleMapsAPIKey;
		}

		Group group = getGroup();

		if (group == null) {
			_googleMapsAPIKey = getCompanyGoogleMapsAPIKey();

			return _googleMapsAPIKey;
		}

		_googleMapsAPIKey = GetterUtil.getString(
			group.getTypeSettingsProperty("googleMapsAPIKey"),
			getCompanyGoogleMapsAPIKey());

		return _googleMapsAPIKey;
	}

	protected Group getGroup() {
		Group group = (Group)_request.getAttribute("site.liveGroup");

		if (group != null) {
			return group;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		group = themeDisplay.getScopeGroup();

		if (!group.isControlPanel()) {
			return group;
		}

		return null;
	}

	private String _googleMapsAPIKey;
	private final HttpServletRequest _request;

}