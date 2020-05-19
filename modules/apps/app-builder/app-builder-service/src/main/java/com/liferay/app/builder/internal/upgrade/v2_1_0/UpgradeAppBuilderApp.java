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

package com.liferay.app.builder.internal.upgrade.v2_1_0;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.internal.upgrade.v2_1_0.util.AppBuilderAppTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class UpgradeAppBuilderApp extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(AppBuilderAppTable.TABLE_NAME, "scope")) {
			alter(
				AppBuilderAppTable.class,
				new AlterTableAddColumn("scope", "VARCHAR(75) null"));

			try (PreparedStatement ps = connection.prepareStatement(
					"update AppBuilderApp set scope = ?")) {

				ps.setString(1, AppBuilderAppConstants.STANDARD_APP_SCOPE);

				ps.execute();
			}
		}
	}

}