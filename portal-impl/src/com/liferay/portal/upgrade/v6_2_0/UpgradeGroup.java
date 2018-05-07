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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.upgrade.v6_2_0.util.GroupTable;

import java.sql.PreparedStatement;

/**
 * @author Hugo Huijser
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(
			GroupTable.class, new AlterColumnType("typeSettings", "TEXT null"),
			new AlterColumnType("friendlyURL", "VARCHAR(255) null"));

		upgradeFriendlyURL();
		upgradeSite();
	}

	protected void upgradeFriendlyURL() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"update Group_ set friendlyURL= ? where classNameId = ?")) {

			ps.setString(1, GroupConstants.GLOBAL_FRIENDLY_URL);
			ps.setLong(2, _COMPANY_CLASS_NAME_ID);

			ps.execute();
		}
	}

	protected void upgradeSite() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update Group_ set site = TRUE where classNameId = " +
					_COMPANY_CLASS_NAME_ID);
		}
	}

	private static final Long _COMPANY_CLASS_NAME_ID =
		PortalUtil.getClassNameId("com.liferay.portal.model.Company");

}