/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 */
public class DDMFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMStorageLinks();

		_upgradeDDMFields();
	}

	private void _upgradeDDMFields() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStorageLink.classPK, ",
					"DDMStorageLink.structureVersionId from DDMStorageLink ",
					"inner join DDMStructure on DDMStorageLink.structureId = ",
					"DDMStructure.structureId where DDMStructure.structureKey ",
					"like ? "));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update DDMField set parentFieldId = ? where ",
						"DDMField.storageId = ? and ",
						"DDMField.structureVersionId = ? and ",
						"DDMField.fieldName like ? and DDMField.priority = ",
						"?"))) {

			preparedStatement1.setString(1, "CUSTOM-META-TAGS");

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				long storageId = resultSet1.getLong("classPK");
				long structureVersionId = resultSet1.getLong(
					"structureVersionId");

				try (PreparedStatement preparedStatement3 =
						connection.prepareStatement(
							StringBundler.concat(
								"select DDMField.fieldId, DDMField.priority ",
								"from DDMField where DDMField.storageId = ? ",
								"and DDMField.structureVersionId = ? and ",
								"DDMField.fieldName like ? "))) {

					preparedStatement3.setLong(1, storageId);
					preparedStatement3.setLong(2, structureVersionId);
					preparedStatement3.setString(3, "property");

					ResultSet resultSet2 = preparedStatement3.executeQuery();

					while (resultSet2.next()) {
						preparedStatement2.setLong(
							1, resultSet2.getLong("fieldId"));
						preparedStatement2.setLong(2, storageId);
						preparedStatement2.setLong(3, structureVersionId);
						preparedStatement2.setString(4, "content");
						preparedStatement2.setLong(
							5, resultSet2.getLong("priority") + 1);

						preparedStatement2.addBatch();
					}
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _upgradeDDMStorageLinks() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureVersion.structureId, ",
					"DDMStorageLink.storageLinkId from DDMStorageLink inner ",
					"join DDMStructureVersion on ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMStorageLink.structureVersionId where ",
					"DDMStorageLink.structureId = 0"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DDMStorageLink set structureId = ? where " +
						"storageLinkId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				preparedStatement2.setLong(1, resultSet.getLong(1));
				preparedStatement2.setLong(2, resultSet.getLong(2));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}