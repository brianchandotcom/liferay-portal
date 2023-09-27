/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_1_1;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class DDMStructureLayoutUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_populateFields();
	}

	private void _populateFields() throws Exception {
		long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select structureLayoutId from DDMStructureLayout where " +
					"structureLayoutKey is null or structureLayoutKey = ''");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update DDMStructureLayout set classNameId = ?, ",
						"structureLayoutKey = ? where structureLayoutId = ",
						"?"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setLong(1, classNameId);
					preparedStatement2.setString(
						2, String.valueOf(increment()));
					preparedStatement2.setLong(3, resultSet.getLong(1));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeSchema() throws Exception {
		alterTableAddColumn("DDMStructureLayout", "classNameId", "LONG");
		alterTableAddColumn(
			"DDMStructureLayout", "structureLayoutKey", "VARCHAR(75) null");
	}

}