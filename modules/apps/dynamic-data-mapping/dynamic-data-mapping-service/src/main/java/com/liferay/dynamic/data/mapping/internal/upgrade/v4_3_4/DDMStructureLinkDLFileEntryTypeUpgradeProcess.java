/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_4;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alicia Garcia
 */
public class DDMStructureLinkDLFileEntryTypeUpgradeProcess
	extends UpgradeProcess {

	public DDMStructureLinkDLFileEntryTypeUpgradeProcess(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_fixDataDefinitionIdFields();
	}

	private void _fixDataDefinitionIdFields() throws Exception {
		StringBuilder sb = new StringBuilder(5);

		sb.append("select DLFileEntryType.uuid_, fileEntryTypeId, ");
		sb.append("DLFileEntryType.groupId, DLFileEntryType.companyId, ");
		sb.append("dataDefinitionId, fileEntryTypeKey from DLFileEntryType ");
		sb.append("inner join DDMStructure ON dataDefinitionId = structureId ");
		sb.append("where type_ = 0");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				sb.toString());
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select structureId FROM DDMStructure where groupId = ? AND " +
					"classNameId = ? AND (structureKey = ? OR structureKey = " +
						"? OR structureKey = ? ) ");
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				"select structureLinkId from DDMStructureLink where " +
					"companyId = ? and classNameId = ? and classPK = ? and " +
						"structureId = ?");
			PreparedStatement preparedStatement4 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"insert into DDMStructureLink (structureLinkId, " +
						"companyId, classNameId, classPK, structureId) " +
							"values (?, ?, ?, ?, ?)");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				long fileEntryTypeId = resultSet1.getLong(2);

				preparedStatement2.setLong(1, resultSet1.getLong(3));
				preparedStatement2.setLong(
					2, PortalUtil.getClassNameId(DLFileEntryMetadata.class));
				preparedStatement2.setString(
					3, DLUtil.getDDMStructureKey(resultSet1.getString(1)));
				preparedStatement2.setString(
					4, DLUtil.getDeprecatedDDMStructureKey(fileEntryTypeId));
				preparedStatement2.setString(5, resultSet1.getString(4));

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					if (resultSet2.next()) {
						long structureId = resultSet2.getLong(1);

						ActionableDynamicQuery actionableDynamicQuery =
							_dlFileEntryTypeLocalService.
								getActionableDynamicQuery();

						actionableDynamicQuery.setAddCriteriaMethod(
							dynamicQuery -> dynamicQuery.add(
								RestrictionsFactoryUtil.eq(
									"fileEntryTypeId", fileEntryTypeId)));
						actionableDynamicQuery.setPerformActionMethod(
							(DLFileEntryType dlFileEntryType) -> {
								dlFileEntryType.setDataDefinitionId(
									structureId);

								_dlFileEntryTypeLocalService.
									updateDLFileEntryType(dlFileEntryType);
							});

						actionableDynamicQuery.performActions();
					}
				}

				long companyId = resultSet1.getLong(4);

				preparedStatement3.setLong(1, companyId);

				preparedStatement3.setLong(
					2, PortalUtil.getClassNameId(DLFileEntryType.class));
				preparedStatement3.setLong(3, fileEntryTypeId);

				long dataDefinitionId = resultSet1.getLong(5);

				preparedStatement3.setLong(4, dataDefinitionId);

				try (ResultSet resultSet3 = preparedStatement3.executeQuery()) {
					if (resultSet3.next()) {
						continue;
					}

					preparedStatement4.setLong(1, increment());
					preparedStatement4.setLong(2, companyId);
					preparedStatement4.setLong(
						3, PortalUtil.getClassNameId(DLFileEntryType.class));
					preparedStatement4.setLong(4, fileEntryTypeId);
					preparedStatement4.setLong(5, dataDefinitionId);

					preparedStatement4.addBatch();
				}

				preparedStatement4.executeBatch();
			}
		}
	}

	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}