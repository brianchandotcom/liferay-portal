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

package com.liferay.journal.internal.upgrade.v3_3_0;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class StorageLinksUpgradeProcess extends UpgradeProcess {

	public StorageLinksUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStructureVersion.structureVersionId, ",
						"DDMStorageLink.storageLinkId from DDMStorageLink ",
						"inner join DDMStructure on DDMStructure.structureId ",
						"= DDMStorageLink.structureVersionId inner join ",
						"DDMStructureVersion on ",
						"DDMStructureVersion.structureId = ",
						"DDMStructure.structureId and ",
						"DDMStructureVersion.version = DDMStructure.version ",
						"where DDMStorageLink.classNameId = ",
						_classNameLocalService.getClassNameId(
							JournalArticle.class)));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DDMStorageLink set structureVersionId = ? where " +
						"storageLinkId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updatePreparedStatement.setLong(1, resultSet.getLong(1));
				updatePreparedStatement.setLong(2, resultSet.getLong(2));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStructureVersion.structureId, ",
						"DDMStorageLink.storageLinkId from DDMStorageLink ",
						"inner join DDMStructureVersion on ",
						"DDMStructureVersion.structureVersionId = ",
						"DDMStorageLink.structureVersionId where ",
						"DDMStorageLink.structureId = 0"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DDMStorageLink set structureId = ? where " +
						"storageLinkId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updatePreparedStatement.setLong(1, resultSet.getLong(1));
				updatePreparedStatement.setLong(2, resultSet.getLong(2));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}