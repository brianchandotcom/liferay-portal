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

package com.liferay.saml.persistence.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.upgrade.v1_1_0.util.SamlSpSessionTable;

import java.sql.SQLException;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class UpgradeSamlSpSession extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL(
				"alter_column_type SamlSpSession samlSpSessionKey " +
					"VARCHAR(75) null");
			runSQL("alter_column_type SamlSpSession assertionXml TEXT null");
			runSQL(
				"alter_column_type SamlSpSession sessionIndex VARCHAR(75) " +
					"null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				SamlSpSessionTable.TABLE_NAME, SamlSpSessionTable.TABLE_COLUMNS,
				SamlSpSessionTable.TABLE_SQL_CREATE,
				SamlSpSessionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}