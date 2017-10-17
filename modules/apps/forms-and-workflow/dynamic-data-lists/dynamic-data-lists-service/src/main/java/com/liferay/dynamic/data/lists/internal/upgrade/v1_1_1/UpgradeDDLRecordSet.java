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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_1;

import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordSet extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select recordSetId from DDLRecordSet");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDLRecordSet set version = ? where recordSetId = " +
						"?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordSetId = rs.getLong(1);

					String version = DDLRecordSetConstants.VERSION_DEFAULT;

					ps2.setString(1, version);

					ps2.setLong(2, recordSetId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}