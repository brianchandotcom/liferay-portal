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

package com.liferay.style.book.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.style.book.internal.upgrade.v1_1_0.util.StyleBookEntryTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jürgen Kappler
 */
public class StyleBookEntryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();
	}

	private void _upgradeSchema() throws Exception {
		alter(
			StyleBookEntryTable.class,
			new AlterTableAddColumn("uuid_", "VARCHAR(75) null"),
			new AlterTableAddColumn("modifiedDate", "DATE null"));

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						"select styleBookEntryId from StyleBookEntry");
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update StyleBookEntry set uuid_ = ? where " +
								"styleBookEntryId = ?"));
				ResultSet resultSet = preparedStatement1.executeQuery()) {

				while (resultSet.next()) {
					preparedStatement2.setString(1, PortalUUIDUtil.generate());
					preparedStatement2.setLong(2, resultSet.getLong(1));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

}