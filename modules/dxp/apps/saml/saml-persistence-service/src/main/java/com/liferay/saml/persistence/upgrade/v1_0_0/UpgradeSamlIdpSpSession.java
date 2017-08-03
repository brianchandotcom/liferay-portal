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

package com.liferay.saml.persistence.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.upgrade.v1_0_0.util.SamlIdpSpSessionTable;

import java.sql.SQLException;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class UpgradeSamlIdpSpSession extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL(
				"alter_column_type SamlIdpSpSession samlSpEntityId " +
					"VARCHAR(1024) null");
			runSQL(
				"alter_column_type SamlIdpSpSession nameIdFormat " +
					"VARCHAR(1024) null");
			runSQL(
				"alter_column_type SamlIdpSpSession nameIdValue " +
					"VARCHAR(1024) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				SamlIdpSpSessionTable.TABLE_NAME,
				SamlIdpSpSessionTable.TABLE_COLUMNS,
				SamlIdpSpSessionTable.TABLE_SQL_CREATE,
				SamlIdpSpSessionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}