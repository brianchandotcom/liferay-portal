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

package com.liferay.app.builder.internal.upgrade.v2_2_0;

import com.liferay.app.builder.internal.upgrade.v2_2_0.util.AppBuilderAppTable;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class UpgradeAppBuilderApp extends UpgradeProcess {

	public UpgradeAppBuilderApp(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMStructureLocalService ddmStructureLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(AppBuilderAppTable.TABLE_NAME, "ddlRecordSetId")) {
			alter(
				AppBuilderAppTable.class,
				new AlterTableAddColumn("ddlRecordSetId", "LONG"));

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select appBuilderAppId, ddmStructureId, groupId from " +
						"AppBuilderApp");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update AppBuilderApp set ddlRecordSetId = ? where " +
							"appBuilderAppId = ?");
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					ps2.setLong(
						1,
						_getDDLRecordSetId(
							rs.getLong("ddmStructureId"),
							rs.getLong("groupId")));
					ps2.setLong(2, rs.getLong("appBuilderAppId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private long _getDDLRecordSetId(long ddmStructureId, long groupId)
		throws PortalException {

		if (_ddmStructureIdDDLRecordSetIdMap.containsKey(ddmStructureId)) {
			return _ddmStructureIdDDLRecordSetIdMap.get(ddmStructureId);
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			groupId, ddmStructure.getStructureKey());

		_ddmStructureIdDDLRecordSetIdMap.put(
			ddmStructureId, ddlRecordSet.getRecordSetId());

		return _ddmStructureIdDDLRecordSetIdMap.get(ddmStructureId);
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private Map<Long, Long> _ddmStructureIdDDLRecordSetIdMap = new HashMap<>();
	private final DDMStructureLocalService _ddmStructureLocalService;

}