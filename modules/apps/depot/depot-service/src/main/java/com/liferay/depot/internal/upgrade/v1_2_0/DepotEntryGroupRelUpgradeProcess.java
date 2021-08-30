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

package com.liferay.depot.internal.upgrade.v1_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alejandro Tardín
 */
public class DepotEntryGroupRelUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("DepotEntryGroupRel", "uuid_", "VARCHAR(75) null");
		alterTableAddColumn("DepotEntryGroupRel", "groupId", "LONG");
		alterTableAddColumn("DepotEntryGroupRel", "createDate", "DATE null");
		alterTableAddColumn("DepotEntryGroupRel", "modifiedDate", "DATE null");

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						"select depotEntryGroupRelId from DepotEntryGroupRel");
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update DepotEntryGroupRel set groupId = " +
								"toGroupId, uuid_ = ? where " +
									"depotEntryGroupRelId = ?"));
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