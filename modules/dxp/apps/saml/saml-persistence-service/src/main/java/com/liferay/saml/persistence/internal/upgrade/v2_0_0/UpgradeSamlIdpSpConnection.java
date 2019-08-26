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

package com.liferay.saml.persistence.internal.upgrade.v2_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.saml.persistence.internal.upgrade.v2_0_0.util.SamlIdpSpConnectionTable;

import java.sql.SQLException;

/**
 * @author Carlos Sierra Andrés
 */
public class UpgradeSamlIdpSpConnection extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alter(
				SamlIdpSpConnectionTable.class,
				new AlterTableAddColumn("encryptionForced BOOLEAN"));
		}
		catch (SQLException sqle) {
			upgradeTable(
				SamlIdpSpConnectionTable.TABLE_NAME,
				SamlIdpSpConnectionTable.TABLE_COLUMNS,
				SamlIdpSpConnectionTable.TABLE_SQL_CREATE,
				SamlIdpSpConnectionTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}