/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_5;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DDMTemplateVersionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String selectOrphanTemplateVersions =
			"select templateVersionId FROM DDMTemplateVersion where not " +
				"exists (select * from DDMTemplate where " +
					"DDMTemplate.templateId = DDMTemplateVersion.templateId)";

		String deleteOrphanTemplateVersion =
			"delete from DDMTemplateVersion where templateVersionId = ?";

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(selectOrphanTemplateVersions);
			PreparedStatement deletePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, deleteOrphanTemplateVersion)) {

			try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
				while (resultSet.next()) {
					deletePreparedStatement.setLong(
						1, resultSet.getLong("templateVersionId"));

					deletePreparedStatement.addBatch();
				}
			}

			deletePreparedStatement.executeBatch();
		}
	}

}