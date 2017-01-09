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

package com.liferay.marketplace.internal.upgrade.v2_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Ryan Park
 */
public class UpgradeModule extends UpgradeProcess {

	protected void addColumn() throws Exception {
		runSQL("alter table Marketplace_Module add companyId LONG");

		runSQL("drop index IX_A7EFD80E on Marketplace_Module");

		runSQL(
			"create index IX_896A375A on Marketplace_Module (uuid_, " +
				"companyId)");
	}

	@Override
	protected void doUpgrade() throws Exception {
		addColumn();

		updateRows();
	}

	protected void updateRow(long companyId, long moduleId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"update Marketplace_Module set companyId = ? where moduleId " +
					"= ?")) {

			ps.setLong(1, companyId);
			ps.setLong(2, moduleId);

			ps.execute();
		}
	}

	protected void updateRows() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select Marketplace_App.companyId, ");
		sb.append("Marketplace_Module.moduleId from Marketplace_Module inner ");
		sb.append("join Marketplace_App on (Marketplace_App.appId = ");
		sb.append("Marketplace_Module.appId)");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					updateRow(rs.getLong(1), rs.getLong(2));
				}
			}
		}
	}

}