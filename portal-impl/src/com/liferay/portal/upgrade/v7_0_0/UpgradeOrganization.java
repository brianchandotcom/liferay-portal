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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v7_0_0.util.OrganizationTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 * @author Christopher Kian
 */
public class UpgradeOrganization extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alter(OrganizationTable.class, new AlterColumnType("statusId", "LONG"));
		upgradeOrganizationLogoId();
	}

	protected void upgradeOrganizationLogoId() throws SQLException {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select groupId, logoId from LayoutSet where logoId > 0 and " +
					"privateLayout = ?")) {

			ps1.setBoolean(1, false);

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				long groupId = rs1.getLong("groupId");
				long logoId = rs1.getLong("logoId");

				PreparedStatement ps2 = connection.prepareStatement(
					"select classPK from Group_ where groupId = " + groupId);

				ResultSet rs2 = ps2.executeQuery();

				while (rs2.next()) {
					long classPK = rs2.getLong("classPK");

					StringBundler sb = new StringBundler(4);

					sb.append("update Organization_ set logoId = ");
					sb.append(logoId);
					sb.append(" where organizationId = ");
					sb.append(classPK);

					PreparedStatement ps3 = connection.prepareStatement(
						sb.toString());

					ps3.executeUpdate();
				}
			}
		}
	}

}