/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.portlet.action.test.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;

/**
 * @author Cristina González
 */
public class MockThemeDisplayUtil {

	public static ThemeDisplay getThemeDisplay(
			Company company, Group group, Layout layout, LayoutSet layoutSet,
			Locale locale)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(company);
		themeDisplay.setLanguageId(group.getDefaultLanguageId());
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(group.getDefaultLanguageId()));
		themeDisplay.setLocale(locale);
		themeDisplay.setPortalDomain("localhost");
		themeDisplay.setPortalURL(company.getPortalURL(group.getGroupId()));
		themeDisplay.setSecure(true);
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

}