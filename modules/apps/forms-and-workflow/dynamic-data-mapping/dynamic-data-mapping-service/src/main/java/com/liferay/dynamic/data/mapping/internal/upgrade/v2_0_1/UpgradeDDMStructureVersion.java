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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Inácio Nery
 */
public class UpgradeDDMStructureVersion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeDDMStructureVersion();
	}

	protected void upgradeDDMStructureVersion() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructureVersion where uuid_ is null");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set uuid_ = ?, modifiedDate " +
						"= ? where structureVersionId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long structureVersionId = rs.getLong("structureVersionId");
				Timestamp createDate = rs.getTimestamp("createDate");

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setTimestamp(2, createDate);
				ps2.setLong(3, structureVersionId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}