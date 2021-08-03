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

package com.liferay.content.dashboard.web.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Yurena Cabrera
 */
public class ContentDashboardTestUtil {

	public static ThemeDisplay getThemeDisplay(Group group)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		return themeDisplay;
	}

}