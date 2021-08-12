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

package com.liferay.layout.page.template.internal.upgrade.v3_4_3;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class ResourcePermissionUpgradeProcess extends UpgradeProcess {

	public ResourcePermissionUpgradeProcess(
		LayoutPageTemplateEntryLocalService
			layoutPageTemplateEntryLocalService) {

		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_insertResourcePermissions();
	}

	private void _insertResourcePermissions() {
		StringBundler sb1 = new StringBundler(5);

		sb1.append("select mvccVersion, resourcePermissionId, companyId, ");
		sb1.append("scope, primKey, primKeyId, roleId, ownerId, actionIds, ");
		sb1.append("viewActionId from ResourcePermission where name = '");
		sb1.append(LayoutPrototype.class.getName());
		sb1.append("'");

		StringBundler sb2 = new StringBundler(4);

		sb2.append("insert into ResourcePermission (mvccVersion, ");
		sb2.append("resourcePermissionId, companyId, name, scope, primKey, ");
		sb2.append("primKeyId, roleId, ownerId, actionIds, viewActionId) ");
		sb2.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(sb1.toString());
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(sb2.toString()))) {

			while (resultSet.next()) {
				String primKey = resultSet.getString("primKey");
				String primKeyId = resultSet.getString("primKeyId");

				if (!primKey.equals(LayoutPrototype.class.getName())) {
					LayoutPageTemplateEntry layoutPageTemplateEntry =
						_layoutPageTemplateEntryLocalService.
							fetchFirstLayoutPageTemplateEntry(
								Long.valueOf(primKey));

					if (layoutPageTemplateEntry == null) {
						continue;
					}

					primKey = String.valueOf(
						layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

					primKeyId = primKey;
				}
				else {
					primKey = LayoutPageTemplateEntry.class.getName();
				}

				long mvccVersion = resultSet.getLong("mvccVersion");
				long companyId = resultSet.getLong("companyId");
				long scope = resultSet.getLong("scope");
				long roleId = resultSet.getLong("roleId");
				long ownerId = resultSet.getLong("ownerId");
				long actionIds = resultSet.getLong("actionIds");
				long viewActionId = resultSet.getLong("viewActionId");

				preparedStatement.setLong(1, mvccVersion);
				preparedStatement.setLong(2, increment());
				preparedStatement.setLong(3, companyId);
				preparedStatement.setString(
					4, LayoutPageTemplateEntry.class.getName());
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
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionUpgradeProcess.class);

	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}