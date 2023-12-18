/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.upgrade.v2_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.DatabaseMetaData;

/**
 * @author Mariano Álvaro Sáiz
 */
public class SchemaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			removePrimaryKey("WMSLADefinition");

			try {
				dropIndexes("WMSLADefinition", "wmSLADefinitionId");
			}
			finally {
				DatabaseMetaData databaseMetaData = connection.getMetaData();

				DBInspector dbInspector = new DBInspector(connection);

				runSQL(
					StringBundler.concat(
						"alter table ",
						dbInspector.normalizeName(
							"WMSLADefinition", databaseMetaData),
						" add primary key (wmSLADefinitionId)"));
			}
		}
	}

}