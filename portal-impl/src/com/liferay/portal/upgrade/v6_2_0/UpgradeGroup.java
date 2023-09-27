/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;

/**
 * @author Hugo Huijser
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterColumnType("Group_", "typeSettings", "TEXT null");
		alterColumnType("Group_", "friendlyURL", "VARCHAR(255) null");

		upgradeFriendlyURL();
		upgradeSite();
	}

	protected void upgradeFriendlyURL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"update Group_ set friendlyURL= ? where classNameId = ?")) {

			preparedStatement.setString(1, GroupConstants.GLOBAL_FRIENDLY_URL);
			preparedStatement.setLong(2, _CLASS_NAME_ID);

			preparedStatement.execute();
		}
	}

	protected void upgradeSite() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update Group_ set site = [$TRUE$] where classNameId = " +
					_CLASS_NAME_ID);
		}
	}

	private static final Long _CLASS_NAME_ID = PortalUtil.getClassNameId(
		"com.liferay.portal.model.Company");

}