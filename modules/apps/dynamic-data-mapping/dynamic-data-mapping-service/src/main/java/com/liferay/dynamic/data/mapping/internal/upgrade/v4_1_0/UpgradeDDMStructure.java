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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_1_0;

import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Locale;

/**
 * @author Marcela Cunha
 */
public class UpgradeDDMStructure extends UpgradeProcess {

	public UpgradeDDMStructure(
		DDMDataDefinitionConverter ddmDataDefinitionConverter) {

		_ddmDataDefinitionConverter = ddmDataDefinitionConverter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeStructureDefinition();
		_upgradeStructureLayoutDefinition();
		_upgradeStructureVersionDefinition();
	}

	private long _getParentStructureLayoutId(long parentStructureId)
		throws Exception {

		StringBundler sb1 = new StringBundler(8);

		sb1.append("select DDMStructureLayout.structureLayoutId  from ");
		sb1.append("DDMStructureLayout inner join DDMStructureVersion on ");
		sb1.append("DDMStructureVersion.structureVersionId = ");
		sb1.append("DDMStructureLayout.structureVersionId inner join ");
		sb1.append("DDMStructure on DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId and DDMStructure.version ");
		sb1.append("= DDMStructureVersion.version where ");
		sb1.append("DDMStructure.structureId = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString())) {

			ps1.setLong(1, parentStructureId);

			try (ResultSet rs = ps1.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("structureLayoutId");
				}
			}
		}

		return 0;
	}

	private void _upgradeStructureDefinition() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select * from DDMStructure where classNameId = ? or " +
					"classNameId = ? order by createDate");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			ps1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");

					Locale locale = LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(companyId));

					long parentStructureId = rs.getLong("parentStructureId");

					long parentStructureLayoutId = 0;

					if (parentStructureId > 0) {
						parentStructureLayoutId = _getParentStructureLayoutId(
							parentStructureId);
					}

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormDataDefinition(
								rs.getString("definition"), locale,
								parentStructureId, parentStructureLayoutId));

					ps2.setLong(2, rs.getLong("structureId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeStructureLayoutDefinition() throws Exception {
		StringBundler sb1 = new StringBundler(16);

		sb1.append("select DDMStructure.structureId, ");
		sb1.append("DDMStructure.parentStructureId, DDMStructure.classNameId ");
		sb1.append(", DDMStructure.structureKey, ");
		sb1.append("DDMStructureLayout.structureLayoutId, ");
		sb1.append("DDMStructureLayout.definition as ");
		sb1.append("structureLayoutDefinition, ");
		sb1.append("DDMStructureVersion.definition as ");
		sb1.append("structureVersionDefinition from DDMStructureLayout inner ");
		sb1.append("join DDMStructureVersion on ");
		sb1.append("DDMStructureVersion.structureVersionId = ");
		sb1.append("DDMStructureLayout.structureVersionId inner join ");
		sb1.append("DDMStructure on DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId and DDMStructure.version ");
		sb1.append("= DDMStructureVersion.version where ");
		sb1.append("DDMStructure.classNameId = ? or DDMStructure.classNameId ");
		sb1.append("= ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ?, " +
						"classNameId = ?, structureLayoutKey = ? where " +
							"structureLayoutId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			ps1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String structureLayoutDefinition = rs.getString(
						"structureLayoutDefinition");
					String structureVersionDefinition = rs.getString(
						"structureVersionDefinition");

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormLayoutDataDefinition(
								structureLayoutDefinition,
								structureVersionDefinition));

					ps2.setLong(2, rs.getLong("classNameId"));
					ps2.setString(3, rs.getString("structureKey"));
					ps2.setLong(4, rs.getLong("structureLayoutId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeStructureVersionDefinition() throws Exception {
		StringBundler sb1 = new StringBundler(6);

		sb1.append("select DDMStructure.structureKey, DDMStructureVersion.* ");
		sb1.append("from DDMStructureVersion inner join DDMStructure on ");
		sb1.append("DDMStructure.structureId = ");
		sb1.append("DDMStructureVersion.structureId where ");
		sb1.append("DDMStructure.classNameId = ? or DDMStructure.classNameId ");
		sb1.append("= ?");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureVersionId = ?")) {

			ps1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			ps1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");

					Locale locale = LocaleUtil.fromLanguageId(
						UpgradeProcessUtil.getDefaultLanguageId(companyId));

					long parentStructureId = rs.getLong("parentStructureId");

					long parentStructureLayoutId = 0;

					if (parentStructureId > 0) {
						parentStructureLayoutId = _getParentStructureLayoutId(
							parentStructureId);
					}

					ps2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormDataDefinition(
								rs.getString("definition"), locale,
								parentStructureId, parentStructureLayoutId));

					ps2.setLong(2, rs.getLong("structureId"));
					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_DL_FILE_ENTRY_METADATA =
		"com.liferay.document.library.kernel.model.DLFileEntryMetadata";

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

	private final DDMDataDefinitionConverter _ddmDataDefinitionConverter;

}