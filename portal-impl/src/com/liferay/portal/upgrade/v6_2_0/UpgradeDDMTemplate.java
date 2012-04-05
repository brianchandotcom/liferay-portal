/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_2_0.util.DDMTemplateTable;

import java.sql.SQLException;

/**
 * @author Juan Fernández
 */
public class UpgradeDDMTemplate extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter table DDMTemplate add classNameId LONG");

			runSQL("alter_column_name DDMTemplate structureId classPK LONG");

			runSQL(
				"update DDMTemplate set classNameId = (select classNameId " +
					"from ClassName_ where value = '" +
					"com.liferay.portlet.dynamicdatamapping.model." +
					"DDMTemplate')");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMTemplateTable.TABLE_NAME, DDMTemplateTable.TABLE_COLUMNS,
				DDMTemplateTable.TABLE_SQL_CREATE,
				DDMTemplateTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}