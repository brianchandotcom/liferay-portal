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

package com.liferay.portal.search.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.web.internal.display.context.PortletRequestThemeDisplaySupplier;
import com.liferay.portal.search.web.internal.display.context.ThemeDisplaySupplier;

import javax.portlet.RenderRequest;

/**
 * @author André de Oliveira
 */
public class SearchPortletPermissionUtil {

	public static boolean containsConfiguration(
		PortletPermission portletPermission, RenderRequest renderRequest,
		Portal portal) {

		ThemeDisplaySupplier themeDisplaySupplier =
			new PortletRequestThemeDisplaySupplier(renderRequest);

		ThemeDisplay themeDisplay = themeDisplaySupplier.getThemeDisplay();

		try {
			return portletPermission.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
				portal.getPortletId(renderRequest), ActionKeys.CONFIGURATION);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchPortletPermissionUtil.class);

}