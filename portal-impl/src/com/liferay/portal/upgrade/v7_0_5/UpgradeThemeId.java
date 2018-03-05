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

package com.liferay.portal.upgrade.v7_0_5;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael Bowerman
 */
public class UpgradeThemeId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] themeIds : _THEME_IDS) {
			String oldThemeId = themeIds[0];
			String newThemeId = themeIds[1];

			for (String tableName : _TABLE_NAMES) {
				runSQL(
					StringBundler.concat(
						"update ", tableName, " set themeId = '", newThemeId,
						"' where themeId = '", oldThemeId, "'"));
			}
		}
	}

	private static final String[] _TABLE_NAMES =
		{"Layout", "LayoutRevision", "LayoutSet", "LayoutSetBranch"};

	private static final String[][] _THEME_IDS = {
		new String[] {"classic", "classic_WAR_classictheme"},
		new String[] {"controlpanel", "admin_WAR_admintheme"}
	};

}