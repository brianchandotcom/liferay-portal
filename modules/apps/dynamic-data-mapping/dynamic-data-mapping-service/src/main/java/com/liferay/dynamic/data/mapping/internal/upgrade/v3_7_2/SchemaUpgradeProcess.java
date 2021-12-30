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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_2;

import com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_2.util.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_2.util.DDMFormInstanceVersionTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Rodrigo Paulino
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_alterTables();
	}

	private void _alterTables() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumnType(
					DDMFormInstanceTable.TABLE_NAME, "description",
					"TEXT null")) {

				alter(
					DDMFormInstanceTable.class,
					new AlterColumnType("description", "TEXT null"));
			}

			if (!hasColumnType(
					DDMFormInstanceVersionTable.TABLE_NAME, "description",
					"TEXT null")) {

				alter(
					DDMFormInstanceVersionTable.class,
					new AlterColumnType("description", "TEXT null"));
			}
		}
	}

}