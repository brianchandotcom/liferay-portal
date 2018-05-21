/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.upgrade.v1_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.saml.persistence.upgrade.v1_0_0.util.SamlSpSessionTable;

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
				"alter_column_type SamlSpSession nameIdFormat VARCHAR(1024) " +
					"null");
			runSQL(
				"alter_column_type SamlSpSession nameIdValue VARCHAR(1024) " +
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