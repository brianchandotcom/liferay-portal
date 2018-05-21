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