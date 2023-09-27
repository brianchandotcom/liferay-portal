/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v3_1_4;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Rubén Pulido
 */
public class ResourcePermissionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_insertResourcePermissions();
	}

	private void _insertResourcePermissions() {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				StringBundler.concat(
					"select mvccVersion, resourcePermissionId, companyId, ",
					"scope, primKey, primKeyId, roleId, ownerId, actionIds, ",
					"viewActionId from ResourcePermission where name = '",
					LayoutAdminPortletKeys.GROUP_PAGES, "'"));
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"insert into ResourcePermission (mvccVersion, ",
						"resourcePermissionId, companyId, name, scope, ",
						"primKey, primKeyId, roleId, ownerId, actionIds, ",
						"viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?)"))) {

			while (resultSet.next()) {
				long mvccVersion = resultSet.getLong("mvccVersion");
				long companyId = resultSet.getLong("companyId");
				long scope = resultSet.getLong("scope");
				String primKey = resultSet.getString("primKey");
				String primKeyId = resultSet.getString("primKeyId");
				long roleId = resultSet.getLong("roleId");
				long ownerId = resultSet.getLong("ownerId");
				long actionIds = resultSet.getLong("actionIds");
				long viewActionId = resultSet.getLong("viewActionId");

				preparedStatement.setLong(1, mvccVersion);
				preparedStatement.setLong(
					2, increment(ResourcePermission.class.getName()));
				preparedStatement.setLong(3, companyId);
				preparedStatement.setString(
					4,
					LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);
				preparedStatement.setLong(5, scope);
				preparedStatement.setString(6, primKey);
				preparedStatement.setString(7, primKeyId);
				preparedStatement.setLong(8, roleId);
				preparedStatement.setLong(9, ownerId);
				preparedStatement.setLong(10, actionIds);
				preparedStatement.setLong(11, viewActionId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionUpgradeProcess.class);

}