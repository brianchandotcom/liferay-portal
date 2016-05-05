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

	protected void updateTikaRawMetadataDDMStructure() throws Exception {
		long classNameId = addRawMetadataProcessorClassName();

		long ddmStructureId = _getDDMStructureId(
			"TIKARAWMETADATA", classNameId);

		if (ddmStructureId != 0) {
			return;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"update DDMStructure set classNameId = ? where " +
					"structureKey = ?")) {

			ps.setLong(1, classNameId);
			ps.setString(2, "TIKARAWMETADATA");

			ps.execute();
		}
	}

	protected void updateTikaRawMetadataFileEntryMetadata() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long oldDDMStructureId = _getDDMStructureId(
				"TIKARAWMETADATA",
				PortalUtil.getClassNameId(DLFileEntry.class));

			if (oldDDMStructureId == 0) {
				return;
			}

			long newDDMStructureId = _getDDMStructureId(
				"TIKARAWMETADATA",
				PortalUtil.getClassNameId(RawMetadataProcessor.class));

			if (newDDMStructureId == 0) {
				return;
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

	private long _getDDMStructureId(String structureKey, long classNameId)
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

}