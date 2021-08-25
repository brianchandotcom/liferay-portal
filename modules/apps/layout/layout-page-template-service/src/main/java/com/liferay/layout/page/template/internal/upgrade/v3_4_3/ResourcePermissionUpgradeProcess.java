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
					LayoutPrototype.class.getName(), "'"));
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select layoutPageTemplateEntryId from ",
					"LayoutPageTemplateEntry where layoutPrototypeId = ? ",
					"order by name asc limit 1"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into ResourcePermission (mvccVersion, ",
							"resourcePermissionId, companyId, name, scope, ",
							"primKey, primKeyId, roleId, ownerId, actionIds, ",
							"viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ",
							"?, ?)")))) {

			while (resultSet.next()) {
				String primKey = resultSet.getString("primKey");
				String primKeyId = resultSet.getString("primKeyId");

				if (!primKey.equals(LayoutPrototype.class.getName())) {
					preparedStatement1.setLong(1, Long.valueOf(primKey));

					try (ResultSet resultSet2 =
							preparedStatement1.executeQuery()) {

						if (!resultSet2.next()) {
							continue;
						}

						primKey = String.valueOf(
							resultSet2.getLong("layoutPageTemplateEntryId"));

						primKeyId = primKey;
					}
				}
				else {
					primKey = LayoutPageTemplateEntry.class.getName();
				}

				preparedStatement2.setLong(1, resultSet.getLong("mvccVersion"));
				preparedStatement2.setLong(2, increment());
				preparedStatement2.setLong(3, resultSet.getLong("companyId"));
				preparedStatement2.setString(
					4, LayoutPageTemplateEntry.class.getName());
				preparedStatement2.setLong(5, resultSet.getLong("scope"));
				preparedStatement2.setString(6, primKey);
				preparedStatement2.setString(7, primKeyId);
				preparedStatement2.setLong(8, resultSet.getLong("roleId"));
				preparedStatement2.setLong(9, resultSet.getLong("ownerId"));
				preparedStatement2.setLong(10, resultSet.getLong("actionIds"));
				preparedStatement2.setLong(
					11, resultSet.getLong("viewActionId"));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionUpgradeProcess.class);

}