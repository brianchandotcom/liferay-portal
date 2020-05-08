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

package com.liferay.layout.page.template.internal.upgrade.v3_1_2;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_insertResourcePermissions();
	}

	private void _insertResourcePermissions() {
		StringBundler sb1 = new StringBundler(5);

		sb1.append("select mvccVersion, resourcePermissionId, companyId, ");
		sb1.append("scope, primKey, primKeyId, roleId, ownerId, actionIds, ");
		sb1.append("viewActionId from ResourcePermission where name = '");
		sb1.append(LayoutAdminPortletKeys.GROUP_PAGES);
		sb1.append("'");

		StringBundler sb2 = new StringBundler(4);

		sb2.append("insert into ResourcePermission (mvccVersion, ");
		sb2.append("resourcePermissionId, companyId, name, scope, primKey, ");
		sb2.append("primKeyId, roleId, ownerId, actionIds, viewActionId) ");
		sb2.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sb1.toString());
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(sb2.toString()))) {

			while (rs.next()) {
				long mvccVersion = rs.getLong("mvccVersion");
				long companyId = rs.getLong("companyId");
				long scope = rs.getLong("scope");
				String primKey = rs.getString("primKey");
				String primKeyId = rs.getString("primKeyId");
				long roleId = rs.getLong("roleId");
				long ownerId = rs.getLong("ownerId");
				long actionIds = rs.getLong("actionIds");
				long viewActionId = rs.getLong("viewActionId");

				ps.setLong(1, mvccVersion);
				ps.setLong(2, increment());
				ps.setLong(3, companyId);
				ps.setString(
					4,
					LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);
				ps.setLong(5, scope);
				ps.setString(6, primKey);
				ps.setString(7, primKeyId);
				ps.setLong(8, roleId);
				ps.setLong(9, ownerId);
				ps.setLong(10, actionIds);
				ps.setLong(11, viewActionId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeResourcePermission.class);

}