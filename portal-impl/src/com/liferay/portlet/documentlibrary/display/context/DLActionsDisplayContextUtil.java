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

package com.liferay.portlet.documentlibrary.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DLPortletInstanceSettings;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivan Zaera
 */
public class DLActionsDisplayContextUtil {

	public static DLActionsDisplayContext getDLActionsDisplayContext(
			HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletId = portletDisplay.getId();

		if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			portletId = ParamUtil.getString(request, "portletResource");
		}

		DLPortletInstanceSettings dlPortletInstanceSettings =
			DLPortletInstanceSettings.getInstance(
				themeDisplay.getLayout(), portletId);

		return new DLActionsDisplayContext(request, dlPortletInstanceSettings);
	}

}