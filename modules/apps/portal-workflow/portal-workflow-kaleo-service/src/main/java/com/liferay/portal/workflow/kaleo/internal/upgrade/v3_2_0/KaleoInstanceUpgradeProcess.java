/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v3_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Rafael Praxedes
 */
public class KaleoInstanceUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("KaleoInstance", "active_")) {
			alterTableAddColumn("KaleoInstance", "active_", "BOOLEAN");

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update KaleoInstance set active_ = ?")) {

				preparedStatement.setBoolean(1, true);

				preparedStatement.execute();
			}
		}
	}

}