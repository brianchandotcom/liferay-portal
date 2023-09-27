/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v3_1_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Matija Petanjek
 */
public class DispatchTriggerModelResourcePermissionUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws IOException, SQLException {
		runSQL(
			"delete from ResourceAction where name = '90' and actionId = " +
				"'ADD_DISPATCH_TRIGGER'");
	}

}