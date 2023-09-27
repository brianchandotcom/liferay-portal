/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_6;

import com.liferay.portal.kernel.upgrade.BaseThemeIdUpgradeProcess;

/**
 * @author Michael Bowerman
 */
public class UpgradeThemeId extends BaseThemeIdUpgradeProcess {

	@Override
	public String[][] getThemeIds() {
		return new String[][] {
			{"classic", "classic_WAR_classictheme"},
			{"controlpanel", "admin_WAR_admintheme"}
		};
	}

}