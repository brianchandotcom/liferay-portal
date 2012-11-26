/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mypages;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.DefaultControlPanelEntry;

/**
 * @author Jorge Ferrer
 * @author Amos Fong
 */
public class MyPagesControlPanelEntry extends DefaultControlPanelEntry {

	@Override
	protected boolean hasPermissionDenied(
			Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception {

		if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED &&
			!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			return true;
		}

		boolean hasPowerUserRole = RoleLocalServiceUtil.hasUserRole(
			themeDisplay.getUserId(), themeDisplay.getCompanyId(),
			RoleConstants.POWER_USER, true);

		if ((PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED ||
			PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED) &&
			!hasPowerUserRole) {

			return true;
		}

		return false;
	}

}