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

package com.liferay.remote.app.internal.upgrade.v2_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.remote.app.internal.upgrade.v2_3_0.util.RemoteAppEntryTable;

/**
 * @author Javier de Arcos
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "description")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("description", "TEXT null"));
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "sourceCodeURL")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("sourceCodeURL", "STRING null"));
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "status")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("status", "INTEGER"));

			runSQL("update RemoteAppEntry set status = 0 where status is null");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusByUserId")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusByUserId", "LONG"));

			runSQL(
				"update RemoteAppEntry set statusByUserId = userId where " +
					"statusByUserId is null");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusByUserName")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusByUserName", "VARCHAR(75)"));

			runSQL(
				"update RemoteAppEntry set statusByUserName = (select " +
					"screenName from User_ where RemoteAppEntry.userId = " +
						"User_.userId)");
		}

		if (!hasColumn(RemoteAppEntryTable.TABLE_NAME, "statusDate")) {
			alter(
				RemoteAppEntryTable.class,
				new AlterTableAddColumn("statusDate", "DATE"));

			runSQL(
				"update RemoteAppEntry set statusDate = modifiedDate where " +
					"statusDate is null");
		}
	}

}