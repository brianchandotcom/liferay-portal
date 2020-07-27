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

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Rafael Praxedes
 */
public class UpgradeSchema extends UpgradeProcess {

	public UpgradeSchema(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String template = StringUtil.read(
				UpgradeSchema.class.getResourceAsStream(
					"dependencies/update.sql"));

			runSQLTemplateString(template, false);

			StringBundler sb = new StringBundler(5);

			sb.append("insert into AppBuilderAppVersion (uuid_, ");
			sb.append("appBuilderAppVersionId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, appBuilderAppId, ");
			sb.append("ddlRecordSetId, ddmStructureId, ddmStructureLayoutId, ");
			sb.append("version) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select AppBuilderApp.* from AppBuilderApp");
				ResultSet rs = ps1.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, sb.toString())) {

				while (rs.next()) {
					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, _counterLocalService.increment());
					ps2.setLong(3, rs.getLong("groupId"));
					ps2.setLong(4, rs.getLong("companyId"));
					ps2.setLong(5, rs.getLong("userId"));
					ps2.setString(6, rs.getString("userName"));
					ps2.setTimestamp(7, rs.getTimestamp("createDate"));
					ps2.setTimestamp(8, rs.getTimestamp("modifiedDate"));
					ps2.setLong(9, rs.getLong("appBuilderAppId"));
					ps2.setLong(10, rs.getLong("ddlRecordSetId"));
					ps2.setLong(11, rs.getLong("ddmStructureId"));
					ps2.setLong(12, rs.getLong("ddmStructureLayoutId"));
					ps2.setString(13, _DEFAULT_VERSION);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private static final String _DEFAULT_VERSION = "1.0";

	private final CounterLocalService _counterLocalService;

}