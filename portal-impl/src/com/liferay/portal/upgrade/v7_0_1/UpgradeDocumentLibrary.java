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

package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

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

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ClassName_ (mvccVersion, classNameId, value) " +
					"values (?, ?, ?)")) {

			classNameId = increment();

			ps.setLong(1, 0);
			ps.setLong(2, classNameId);
			ps.setString(3, RawMetadataProcessor.class.getName());

			ps.executeUpdate();
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
			PreparedStatement ps = connection.prepareStatement(
				"select structureId from DDMStructure where structureKey = ? " +
					"and classNameId = ?")) {

			ps.setString(1, structureKey);
			ps.setLong(2, classNameId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("structureId");
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

		try (PreparedStatement ps = connection.prepareStatement(
				"update DDMStructure set classNameId = ? where structureKey " +
					"= ?")) {

			ps.setLong(1, classNameId);
			ps.setString(2, "TIKARAWMETADATA");

			ps.execute();
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

			StringBundler sb = new StringBundler(5);

			sb.append("select fileVersionId, ddmStructureId from ");
			sb.append("DLFileEntryMetadata where fileVersionId in (select ");
			sb.append("fileVersionId from DLFileEntryMetadata group by ");
			sb.append("fileVersionId having count(*) = 2) and ddmStructureId ");
			sb.append("= ?");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString());
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"delete from DLFileEntryMetadata where fileVersionId " +
							"= ? and ddmStructureId = ?")) {

				ps1.setLong(1, oldDDMStructureId);

				ResultSet resultSet = ps1.executeQuery();

				while (resultSet.next()) {
					long fileVersionId = resultSet.getLong("fileVersionId");
					long ddmStructureId = resultSet.getLong("ddmStructureId");

					ps2.setLong(1, fileVersionId);
					ps2.setLong(2, ddmStructureId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}

			try (PreparedStatement ps = connection.prepareStatement(
					"update DLFileEntryMetadata set DDMStructureId = ? where " +
						"DDMStructureId = ?")) {

				ps.setLong(1, newDDMStructureId);
				ps.setLong(2, oldDDMStructureId);

				ps.execute();
			}
		}
	}

}