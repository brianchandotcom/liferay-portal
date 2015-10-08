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

package com.liferay.dynamic.data.mapping.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.util.DDMContentTable;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.util.DDMStructureTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.SQLException;

/**
 * @author Marcellus Tavares
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);

		try {
			runSQL("alter_column_name DDMContent xml data_ TEXT null");

			runSQL("alter_column_name DDMStructure xsd definition TEXT null");
		}
		catch (SQLException sqle) {

			// DDMContent

			upgradeTable(
				DDMContentTable.TABLE_NAME, DDMContentTable.TABLE_COLUMNS,
				DDMContentTable.TABLE_SQL_CREATE,
				DDMContentTable.TABLE_SQL_ADD_INDEXES);

			// DDMStructure

			upgradeTable(
				DDMStructureTable.TABLE_NAME, DDMStructureTable.TABLE_COLUMNS,
				DDMStructureTable.TABLE_SQL_CREATE,
				DDMStructureTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}