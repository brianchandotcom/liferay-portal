/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.ResourceActionsImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Set;

/**
 * @author Luis Ortiz
 */
public class ResourcePermissionDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		if (!dbInspector.hasTable("ResourcePermission") ||
			!dbInspector.hasColumn("ResourcePermission", "primKeyId")) {

			return;
		}

		Set<String> liferayTableNames =
			DataCleanupPreupgradeProcessUtil.getLiferayTableNames(connection);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select distinct name from ResourcePermission where name " +
					"like 'com.liferay.%' and primKeyId != 0 and primKeyId " +
						"is not null and scope = ?")) {

			preparedStatement.setLong(1, ResourceConstants.SCOPE_INDIVIDUAL);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				ResourceActionsImpl resourceActionsImpl =
					new ResourceActionsImpl();

				while (resultSet.next()) {
					String name = resultSet.getString(1);

					String[] classNames = StringUtil.split(
						name,
						resourceActionsImpl.getCompositeModelNameSeparator());

					String tableName = null;

					if (classNames.length == 1) {
						String className = classNames[0];

						int poundIndex = className.indexOf(StringPool.POUND);

						if (poundIndex != -1) {
							className = className.substring(0, poundIndex);
						}

						tableName =
							DataCleanupPreupgradeProcessUtil.getTableName(
								false, dbInspector, className,
								liferayTableNames);
					}
					else {
						for (String className : classNames) {
							tableName =
								DataCleanupPreupgradeProcessUtil.getTableName(
									false, dbInspector, className,
									liferayTableNames);

							if (StringUtil.startsWith(tableName, "DDM")) {
								break;
							}

							tableName = null;
						}
					}

					if (tableName == null) {
						continue;
					}

					if (StringUtil.equalsIgnoreCase(
							tableName, "ObjectDefinition")) {

						try (PreparedStatement preparedStatement1 =
								connection.prepareStatement(
									"select dbTableName from " +
										"ObjectDefinition where className = " +
											"?")) {

							preparedStatement1.setString(1, name);

							try (ResultSet resultSet1 =
									preparedStatement1.executeQuery()) {

								if (resultSet1.next()) {
									tableName = resultSet1.getString(1);
								}
							}
						}
					}

					if (!dbInspector.hasTable(tableName)) {
						if (_log.isInfoEnabled()) {
							_log.info("Table " + tableName + " does not exist");
						}

						continue;
					}

					String primaryKeyColumnName = "resourcePrimKey";

					if (!dbInspector.hasColumn(
							tableName, primaryKeyColumnName)) {

						primaryKeyColumnName =
							DataCleanupPreupgradeProcessUtil.
								getPrimaryKeyColumnName(
									connection, dbInspector, tableName);
					}

					if (primaryKeyColumnName == null) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Table " + tableName +
									" does not have a primary key");
						}

						continue;
					}

					upgrade(
						new TableOrphanReferencesDataCleanupPreupgradeProcess(
							null,
							StringBundler.concat(
								"[$SOURCE_TABLE_ALIAS$].scope = ",
								ResourceConstants.SCOPE_INDIVIDUAL, " and ",
								"[$SOURCE_TABLE_ALIAS$].name = '", name, "'"),
							"primKeyId", "ResourcePermission",
							primaryKeyColumnName, tableName));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionDataCleanupPreupgradeProcess.class);

}