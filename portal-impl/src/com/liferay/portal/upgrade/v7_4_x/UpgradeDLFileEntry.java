/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v7_4_x.util.DLFileEntryTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia García
 * @author Javier de Arcos
 */
public class UpgradeDLFileEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(DLFileEntryTable.TABLE_NAME, "externalReferenceCode")) {
			alter(
				DLFileEntryTable.class,
				new AlterTableAddColumn(
					"externalReferenceCode", "VARCHAR(75)"));

			_populateExternalReferenceCode();
		}

		if (!hasColumn(DLFileEntryTable.TABLE_NAME, "expirationDate")) {
			alter(
				DLFileEntryTable.class,
				new AlterTableAddColumn("expirationDate", "DATE null"));
		}

		if (!hasColumn(DLFileEntryTable.TABLE_NAME, "reviewDate")) {
			alter(
				DLFileEntryTable.class,
				new AlterTableAddColumn("reviewDate", "DATE null"));
		}
	}

	private void _populateExternalReferenceCode() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fileEntryId from DLFileEntry where " +
					"externalReferenceCode is null or externalReferenceCode " +
						"= ''");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update DLFileEntry set externalReferenceCode = ? " +
							"where fileEntryId = ?"))) {

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