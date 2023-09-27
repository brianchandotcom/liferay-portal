/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;

/**
 * @author Drew Brokke
 */
public class UpgradeUsersUserGroups extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[] expectedPrimaryKeyColumnNames = {
			"userId", "userGroupId", "ctCollectionId"
		};

		if (Arrays.equals(
				expectedPrimaryKeyColumnNames,
				getPrimaryKeyColumnNames(connection, "Users_UserGroups"))) {

			return;
		}

		removePrimaryKey("Users_UserGroups");

		runSQL(
			String.format(
				"alter table Users_UserGroups add primary key (%s)",
				ArrayUtil.toString(
					expectedPrimaryKeyColumnNames, (String)null)));
	}

}