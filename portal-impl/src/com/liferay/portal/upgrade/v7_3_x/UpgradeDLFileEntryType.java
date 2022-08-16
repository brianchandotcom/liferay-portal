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

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia Garcia
 */
public class UpgradeDLFileEntryType extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_populateFields();
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"DLFileEntryType", "dataDefinitionId LONG")
		};
	}

	private void _populateFields() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select uuid_, fileEntryTypeId, groupId, fileEntryTypeKey " +
					"from DLFileEntryType where (dataDefinitionId IS NULL OR " +
						"dataDefinitionId = 0)");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select structureId FROM DDMStructure where groupId = ? AND " +
					"classNameId = ? AND (structureKey = ? OR structureKey = " +
						"? OR structureKey = ? ) ");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DLFileEntryType set dataDefinitionId = ? where " +
						"fileEntryTypeId = ? ");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			while (resultSet1.next()) {
				preparedStatement2.setLong(1, resultSet1.getLong(3));
				preparedStatement2.setLong(2, classNameId);
				preparedStatement2.setString(
					3, DLUtil.getDDMStructureKey(resultSet1.getString(1)));
				preparedStatement2.setString(
					4,
					DLUtil.getDeprecatedDDMStructureKey(resultSet1.getLong(2)));
				preparedStatement2.setString(5, resultSet1.getString(4));

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					if (resultSet2.next()) {
						preparedStatement3.setLong(1, resultSet2.getLong(1));
						preparedStatement3.setLong(2, resultSet1.getLong(2));

						preparedStatement3.addBatch();
					}
				}
			}

			preparedStatement3.executeBatch();
		}
	}

}