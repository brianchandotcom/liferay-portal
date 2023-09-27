/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected long addRawMetadataProcessorClassName() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			RawMetadataProcessor.class);

		if (classNameId != 0) {
			return classNameId;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into ClassName_ (mvccVersion, classNameId, value) " +
					"values (?, ?, ?)")) {

			classNameId = increment();

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setString(
				3, RawMetadataProcessor.class.getName());

			preparedStatement.executeUpdate();
		}

		return classNameId;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateTikaRawMetadataDDMStructure();

		updateTikaRawMetadataFileEntryMetadata();
	}

	protected long getDDMStructureId(String structureKey, long classNameId)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = ? " +
					"and classNameId = ?")) {

			preparedStatement.setString(1, structureKey);
			preparedStatement.setLong(2, classNameId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("structureId");
			}

			return 0;
		}
	}

	protected void updateTikaRawMetadataDDMStructure() throws Exception {
		long classNameId = addRawMetadataProcessorClassName();

		long ddmStructureId = getDDMStructureId("TIKARAWMETADATA", classNameId);

		if (ddmStructureId != 0) {
			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMStructure set classNameId = ? where structureKey " +
					"= ?")) {

			preparedStatement.setLong(1, classNameId);
			preparedStatement.setString(2, "TIKARAWMETADATA");

			preparedStatement.execute();
		}
	}

	protected void updateTikaRawMetadataFileEntryMetadata() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long oldDDMStructureId = getDDMStructureId(
				"TIKARAWMETADATA",
				PortalUtil.getClassNameId(DLFileEntry.class));

			if (oldDDMStructureId == 0) {
				return;
			}

			long newDDMStructureId = getDDMStructureId(
				"TIKARAWMETADATA",
				PortalUtil.getClassNameId(RawMetadataProcessor.class));

			if (newDDMStructureId == 0) {
				return;
			}

			try (PreparedStatement preparedStatement1 =
					connection.prepareStatement(
						StringBundler.concat(
							"select fileVersionId, DDMStructureId from ",
							"DLFileEntryMetadata where fileVersionId in ",
							"(select fileVersionId from DLFileEntryMetadata ",
							"group by fileVersionId having count(*) >= 2) and ",
							"DDMStructureId = ?"));
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from DLFileEntryMetadata where fileVersionId " +
							"= ? and DDMStructureId = ?")) {

				preparedStatement1.setLong(1, oldDDMStructureId);

				ResultSet resultSet = preparedStatement1.executeQuery();

				while (resultSet.next()) {
					long fileVersionId = resultSet.getLong("fileVersionId");
					long ddmStructureId = resultSet.getLong("DDMStructureId");

					preparedStatement2.setLong(1, fileVersionId);
					preparedStatement2.setLong(2, ddmStructureId);

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update DLFileEntryMetadata set DDMStructureId = ? " +
							"where DDMStructureId = ?")) {

				preparedStatement.setLong(1, newDDMStructureId);
				preparedStatement.setLong(2, oldDDMStructureId);

				preparedStatement.execute();
			}
		}
	}

}