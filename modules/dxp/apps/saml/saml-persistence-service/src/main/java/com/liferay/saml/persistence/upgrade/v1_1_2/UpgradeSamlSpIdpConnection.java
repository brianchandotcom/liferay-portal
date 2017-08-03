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

package com.liferay.saml.persistence.upgrade.v1_1_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.upgrade.v1_1_2.util.SamlSpIdpConnectionTable;

import java.sql.SQLException;

/**
 * @author Mika Koivisto
 */
public class UpgradeSamlSpIdpConnection extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type SamlSpIdpConnection forceAuthn BOOLEAN");
		}
		catch (SQLException sqle) {
			upgradeTable(
				SamlSpIdpConnectionTable.TABLE_NAME,
				SamlSpIdpConnectionTable.TABLE_COLUMNS,
				SamlSpIdpConnectionTable.TABLE_SQL_CREATE,
				SamlSpIdpConnectionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}