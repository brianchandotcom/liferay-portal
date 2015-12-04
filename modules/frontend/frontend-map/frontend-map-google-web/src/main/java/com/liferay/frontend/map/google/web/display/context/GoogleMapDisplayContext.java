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

package com.liferay.frontend.map.google.web.display.context;

import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Jürgen Kappler
 */
public class GoogleMapDisplayContext {

	public GoogleMapDisplayContext(PortletRequest request) {
		_request = request;
	}

	public String getCompanyGoogleMapsAPIKey() {
		if (_companyGoogleMapsAPIKey != null) {
			return _companyGoogleMapsAPIKey;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(company.getCompanyId());

		_companyGoogleMapsAPIKey = PrefsParamUtil.getString(
			companyPortletPreferences, _request, "googleMapsAPIKey", "");

		return _companyGoogleMapsAPIKey;
	}

	public String getGroupsGoogleMapsAPIKey() {
		if (_groupGoogleMapsAPIKey != null) {
			return _groupGoogleMapsAPIKey;
		}

		Group liveGroup = (Group)_request.getAttribute("site.liveGroup");

		UnicodeProperties groupTypeSettings = null;

		if (liveGroup != null) {
			groupTypeSettings = liveGroup.getTypeSettingsProperties();
		}
		else {
			groupTypeSettings = new UnicodeProperties();
		}

		_groupGoogleMapsAPIKey = PropertiesParamUtil.getString(
			groupTypeSettings, _request, "googleMapsAPIKey",
			getCompanyGoogleMapsAPIKey());

		return _groupGoogleMapsAPIKey;
	}

	private String _companyGoogleMapsAPIKey;
	private String _groupGoogleMapsAPIKey;
	private final PortletRequest _request;

}