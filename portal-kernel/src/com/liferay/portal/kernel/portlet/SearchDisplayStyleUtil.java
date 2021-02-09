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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class SearchDisplayStyleUtil {

	public static String getDisplayStyle(
		HttpServletRequest httpServletRequest, String portletName,
		String defaultValue) {

		return getDisplayStyle(
			httpServletRequest, portletName, "display-style", defaultValue);
	}

	public static String getDisplayStyle(
		HttpServletRequest httpServletRequest, String portletName, String key,
		String defaultValue) {

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				portletName, key, defaultValue);
		}

		portalPreferences.setValue(portletName, "display-style", displayStyle);

		return displayStyle;
	}

}