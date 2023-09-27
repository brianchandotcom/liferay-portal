/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_0_0;

import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.util.DDMDataDefinitionConverter;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcela Cunha
 */
public class DDMStructureUpgradeProcess extends UpgradeProcess {

	public DDMStructureUpgradeProcess(
		DDMDataDefinitionConverter ddmDataDefinitionConverter) {

		_ddmDataDefinitionConverter = ddmDataDefinitionConverter;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeStructureDefinition();
		_upgradeStructureVersionDefinition();

		_upgradeStructureLayoutDefinition();
	}

	private long _getParentStructureLayoutId(long parentStructureId)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructureLayout.structureLayoutId  from ",
					"DDMStructureLayout inner join DDMStructureVersion on ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMStructureLayout.structureVersionId inner join ",
					"DDMStructure on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId and DDMStructure.version ",
					"= DDMStructureVersion.version where ",
					"DDMStructure.structureId = ?"))) {

			preparedStatement1.setLong(1, parentStructureId);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("structureLayoutId");
				}
			}
		}

		return 0;
	}

	private void _upgradeStructureDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select * from DDMStructure where classNameId = ? or " +
					"classNameId = ? order by createDate");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set parentStructureId = 0, " +
						"definition = ? where structureId = ?")) {

			preparedStatement1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long parentStructureId = resultSet.getLong(
						"parentStructureId");

					long parentStructureLayoutId = 0;

					if (parentStructureId > 0) {
						parentStructureLayoutId = _getParentStructureLayoutId(
							parentStructureId);
					}

					preparedStatement2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormDataDefinition(
								resultSet.getString("definition"),
								resultSet.getLong("groupId"), parentStructureId,
								parentStructureLayoutId,
								resultSet.getLong("structureId")));
					preparedStatement2.setLong(
						2, resultSet.getLong("structureId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeStructureLayoutDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.structureId, ",
					"DDMStructure.parentStructureId, DDMStructure.classNameId ",
					", DDMStructure.structureKey, DDMStructure.version, ",
					"DDMStructureLayout.groupId, ",
					"DDMStructureLayout.structureLayoutId, ",
					"DDMStructureLayout.definition as ",
					"structureLayoutDefinition, ",
					"DDMStructureVersion.definition as ",
					"structureVersionDefinition from DDMStructureLayout inner ",
					"join DDMStructureVersion on ",
					"DDMStructureVersion.structureVersionId = ",
					"DDMStructureLayout.structureVersionId inner join ",
					"DDMStructure on DDMStructure.structureId = ",
					"DDMStructureVersion.structureId and DDMStructure.version ",
					"= DDMStructureVersion.version where ",
					"DDMStructure.classNameId = ? or DDMStructure.classNameId ",
					"= ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureLayout set definition = ?, " +
						"classNameId = ?, structureLayoutKey = ? where " +
							"structureLayoutId = ?")) {

			preparedStatement1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					String structureLayoutDefinition = resultSet.getString(
						"structureLayoutDefinition");
					String structureVersionDefinition = resultSet.getString(
						"structureVersionDefinition");

					preparedStatement2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormLayoutDataDefinition(
								resultSet.getLong("groupId"),
								resultSet.getLong("structureId"),
								structureLayoutDefinition,
								resultSet.getLong("structureLayoutId"),
								structureVersionDefinition));

					preparedStatement2.setLong(
						2, resultSet.getLong("classNameId"));

					String structureLayoutKey = resultSet.getString(
						"structureKey");

					if (!StringUtil.equals(
							resultSet.getString("version"),
							DDMStructureConstants.VERSION_DEFAULT)) {

						structureLayoutKey = String.valueOf(increment());
					}

					preparedStatement2.setString(3, structureLayoutKey);
					preparedStatement2.setLong(
						4, resultSet.getLong("structureLayoutId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private void _upgradeStructureVersionDefinition() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStructure.structureKey, DDMStructureVersion.* ",
					"from DDMStructureVersion inner join DDMStructure on ",
					"DDMStructure.structureId = ",
					"DDMStructureVersion.structureId where ",
					"DDMStructure.classNameId = ? or DDMStructure.classNameId ",
					"= ?"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set parentStructureId = 0, " +
						"definition = ? where structureVersionId = ?")) {

			preparedStatement1.setLong(
				1,
				PortalUtil.getClassNameId(_CLASS_NAME_DL_FILE_ENTRY_METADATA));
			preparedStatement1.setLong(
				2, PortalUtil.getClassNameId(_CLASS_NAME_JOURNAL_ARTICLE));

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long parentStructureId = resultSet.getLong(
						"parentStructureId");

					long parentStructureLayoutId = 0;

					if (parentStructureId > 0) {
						parentStructureLayoutId = _getParentStructureLayoutId(
							parentStructureId);
					}

					preparedStatement2.setString(
						1,
						_ddmDataDefinitionConverter.
							convertDDMFormDataDefinition(
								resultSet.getString("definition"),
								parentStructureId, parentStructureLayoutId));
					preparedStatement2.setLong(
						2, resultSet.getLong("structureVersionId"));
					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final String _CLASS_NAME_DL_FILE_ENTRY_METADATA =
		"com.liferay.document.library.kernel.model.DLFileEntryMetadata";

	private static final String _CLASS_NAME_JOURNAL_ARTICLE =
		"com.liferay.journal.model.JournalArticle";

	private final DDMDataDefinitionConverter _ddmDataDefinitionConverter;

}