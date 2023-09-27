/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 * @author Javier de Arcos
 */
public class UpgradeDLFileEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("DLFileEntry", "externalReferenceCode")) {
			alterTableAddColumn(
				"DLFileEntry", "externalReferenceCode", "VARCHAR(75)");

			_populateExternalReferenceCode();
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"DLFileEntry", "expirationDate DATE null",
				"reviewDate DATE null")
		};
	}

	private void _populateExternalReferenceCode() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fileEntryId from DLFileEntry where " +
					"externalReferenceCode is null or externalReferenceCode " +
						"= ''");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DLFileEntry set externalReferenceCode = ? where " +
						"fileEntryId = ?")) {

			while (resultSet.next()) {
				long fileEntryId = resultSet.getLong(1);

				preparedStatement2.setString(1, String.valueOf(fileEntryId));
				preparedStatement2.setLong(2, fileEntryId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}