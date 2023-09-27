/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseThemeIdUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] themeIds : getThemeIds()) {
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

	protected abstract String[][] getThemeIds();

	private static final String[] _TABLE_NAMES = {
		"Layout", "LayoutRevision", "LayoutSet", "LayoutSetBranch"
	};

}