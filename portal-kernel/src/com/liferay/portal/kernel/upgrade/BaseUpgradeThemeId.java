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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseUpgradeThemeId extends UpgradeProcess {

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