/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v2_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Javier de Arcos
 */
public class RemoteAppEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
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

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"RemoteAppEntry", "description TEXT null",
				"sourceCodeURL STRING null")
		};
	}

}