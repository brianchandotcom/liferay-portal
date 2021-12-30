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
		if (!hasColumn("DDMStructureLayout", "classNameId") &&
			!hasColumn("DDMStructureLayout", "structureLayoutKey")) {

			runSQL("alter table DDMStructureLayout add classNameId LONG");
			runSQL(
				"alter table DDMStructureLayout add structureLayoutKey " +
					"VARCHAR(75) null");
		}
	}

}