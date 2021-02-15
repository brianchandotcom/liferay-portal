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

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_2;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMTemplate extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeDDMTemplate();
		_upgradeDDMTemplateVersion();
	}

	private void _upgradeDDMTemplate() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select script, templateId FROM DDMTemplate where " +
					"classNameId = ?");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplate set script = ? where templateId = ?")) {

			ps1.setLong(1, PortalUtil.getClassNameId(DDMStructure.class));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					ps2.setString(
						1,
						StringUtil.replace(
							rs.getString("script"), "randomizer.", "random."));

					ps2.setLong(2, rs.getLong("templateId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private void _upgradeDDMTemplateVersion() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select script, templateVersionId FROM DDMTemplateVersion " +
					"where classNameId = ?");
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMTemplateVersion set script = ? where " +
						"templateVersionId = ?")) {

			ps1.setLong(1, PortalUtil.getClassNameId(DDMStructure.class));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					ps2.setString(
						1,
						StringUtil.replace(
							rs.getString("script"), "randomizer.", "random."));

					ps2.setLong(2, rs.getLong("templateVersionId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

}