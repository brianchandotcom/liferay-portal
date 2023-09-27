/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.BaseThemeIdUpgradeProcess;

/**
 * @author Antonio Ortega
 */
public class UpgradeThemeId extends BaseThemeIdUpgradeProcess {

	@Override
	protected String[][] getThemeIds() {
		return new String[][] {
			{
				"userdashboard_WAR_userdashboardtheme",
				"classic_WAR_classictheme"
			},
			{"userprofile_WAR_userprofiletheme", "classic_WAR_classictheme"}
		};
	}

}