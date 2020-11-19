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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.upgrade.v7_4_x.util.RegionTable;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Drew Brokke
 */
public class UpgradeRegion extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long defaultCompanyId = 0;
		long defaultUserId = 0;

		String sql = StringBundler.concat(
			"SELECT User_.companyId, User_.userId FROM User_ JOIN Company ON ",
			"User_.companyId = Company.companyId WHERE User_.defaultUser = ",
			"true AND Company.webId = ",
			StringUtil.quote(
				PropsValues.COMPANY_DEFAULT_WEB_ID, StringPool.QUOTE));

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				defaultCompanyId = rs.getLong(1);
				defaultUserId = rs.getLong(2);

				break;
			}
		}

		if (!hasColumn("Region", "companyId")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("companyId", "LONG"));
		}

		if (defaultCompanyId > 0) {
			runSQL(
				"update Region set companyId = " + defaultCompanyId +
					" where companyId is null");
		}

		if (!hasColumn("Region", "userId")) {
			alter(RegionTable.class, new AlterTableAddColumn("userId", "LONG"));
		}

		if (defaultUserId > 0) {
			runSQL(
				"update Region set userId = " + defaultUserId +
					" where userId is null");
		}

		if (!hasColumn("Region", "userName")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("userName", "VARCHAR(75) null"));
		}

		if (!hasColumn("Region", "createDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("createDate", "DATE null"));
		}

		if (!hasColumn("Region", "modifiedDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("modifiedDate", "DATE null"));
		}

		if (!hasColumn("Region", "position")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("position", "DOUBLE"));
		}

		if (!hasColumn("Region", "lastPublishDate")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("lastPublishDate", "DATE null"));
		}

		if (!hasColumn("Region", "uuid_")) {
			alter(
				RegionTable.class,
				new AlterTableAddColumn("uuid_", "VARCHAR(75) null"));
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement ps1 = connection.prepareStatement(
					"select regionId from Region where uuid_ is null");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update Region set uuid_ = ? where regionId = ?"));
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, rs.getLong(1));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}