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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class LiferayPortletResponseUtil {

	public static Layout getLayout(
		PortletRequest portletRequest, ThemeDisplay themeDisplay) {

		Layout layout = (Layout)portletRequest.getAttribute(WebKeys.LAYOUT);

		if ((layout == null) && (themeDisplay != null)) {
			layout = themeDisplay.getLayout();
		}

		return layout;
	}

	public static PortletPreferences getPortletSetup(
		ThemeDisplay themeDisplay, Layout layout, String portletName) {

		if (themeDisplay == null) {
			return PortletPreferencesFactoryUtil.getStrictLayoutPortletSetup(
				layout, portletName);
		}
		else {
			return themeDisplay.getStrictLayoutPortletSetup(
				layout, portletName);
		}
	}

}