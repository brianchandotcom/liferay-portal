/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_2;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcos Martins
 */
public class DDMTemplateUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMTemplate();
		_upgradeDDMTemplateVersion();
	}

	private void _upgradeDDMTemplate() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select templateId, script FROM DDMTemplate where " +
					"classNameId = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set script = ? where templateId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMStructure.class));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						StringUtil.replace(
							resultSet.getString("script"), "randomizer.",
							"random."));
					preparedStatement2.setLong(
						2, resultSet.getLong("templateId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeDDMTemplateVersion() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select templateVersionId, script FROM DDMTemplateVersion " +
					"where classNameId = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplateVersion set script = ? where " +
						"templateVersionId = ?")) {

			preparedStatement1.setLong(
				1, PortalUtil.getClassNameId(DDMStructure.class));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setString(
						1,
						StringUtil.replace(
							resultSet.getString("script"), "randomizer.",
							"random."));
					preparedStatement2.setLong(
						2, resultSet.getLong("templateVersionId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}