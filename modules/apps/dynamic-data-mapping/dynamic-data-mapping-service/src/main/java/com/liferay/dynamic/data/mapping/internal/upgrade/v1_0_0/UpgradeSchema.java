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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0;

import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.util.DDMContentTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.util.DDMStructureTable;
import com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_0.util.DDMTemplateTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcellus Tavares
 */
public class UpgradeSchema extends UpgradeProcess {

	protected void alterTables() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alter(
				DDMContentTable.class,
				new AlterColumnName("xml", "data_ TEXT null"));
			alter(
				DDMStructureTable.class,
				new AlterColumnName("xsd", "definition TEXT null"),
				new AlterColumnType("description", "TEXT null"));
			alter(
				DDMTemplateTable.class,
				new AlterColumnType("description", "TEXT null"));
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateSQL();

		alterTables();
	}

	protected void updateSQL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String template = StringUtil.read(
				UpgradeSchema.class.getResourceAsStream(
					"dependencies/update.sql"));

			runSQLTemplateString(template, false);
		}
	}

}