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

/**
 * @author Javier de Arcos
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("RemoteAppEntry", "description")) {
			alterTableAddColumn("RemoteAppEntry", "description", "TEXT null");
		}

		if (!hasColumn("RemoteAppEntry", "sourceCodeURL")) {
			alterTableAddColumn(
				"RemoteAppEntry", "sourceCodeURL", "STRING null");
		}

		if (!hasColumn("RemoteAppEntry", "status")) {
			alterTableAddColumn("RemoteAppEntry", "status", "INTEGER");

			runSQL("update RemoteAppEntry set status = 0 where status is null");
		}

		if (!hasColumn("RemoteAppEntry", "statusByUserId")) {
			alterTableAddColumn("RemoteAppEntry", "statusByUserId", "LONG");

			runSQL(
				"update RemoteAppEntry set statusByUserId = userId where " +
					"statusByUserId is null");
		}

		if (!hasColumn("RemoteAppEntry", "statusByUserName")) {
			alterTableAddColumn(
				"RemoteAppEntry", "statusByUserName", "VARCHAR(75)");

			runSQL(
				"update RemoteAppEntry set statusByUserName = (select " +
					"screenName from User_ where RemoteAppEntry.userId = " +
						"User_.userId)");
		}

		if (!hasColumn("RemoteAppEntry", "statusDate")) {
			alterTableAddColumn("RemoteAppEntry", "statusDate", "DATE");

			runSQL(
				"update RemoteAppEntry set statusDate = modifiedDate where " +
					"statusDate is null");
		}
	}

}