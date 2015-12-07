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

package com.liferay.frontend.map.api.util;

import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = MapAPIHelperUtil.class)
public class MapAPIHelperUtil {

	public String getGroupMapsAPIProvider(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(company.getCompanyId());

		String companyMapsAPIProvider = PrefsParamUtil.getString(
			companyPortletPreferences, request, "mapsAPIProvider");

		Group liveGroup = (Group)request.getAttribute("site.liveGroup");

		UnicodeProperties groupTypeSettings = null;

		if (liveGroup != null) {
			groupTypeSettings = liveGroup.getTypeSettingsProperties();
		}
		else {
			groupTypeSettings = new UnicodeProperties();
		}

		return PropertiesParamUtil.getString(
			groupTypeSettings, request, "mapsAPIProvider",
			companyMapsAPIProvider);
	}

}