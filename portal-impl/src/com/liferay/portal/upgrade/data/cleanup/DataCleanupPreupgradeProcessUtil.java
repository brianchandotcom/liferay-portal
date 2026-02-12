/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessUtil {

	public static String getPrimaryKeyColumnName(
			Connection connection, DBInspector dbInspector, String tableName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<String> primaryKeyColumnNames = new ArrayList<>(
			Arrays.asList(db.getPrimaryKeyColumnNames(connection, tableName)));

		primaryKeyColumnNames.remove(
			dbInspector.normalizeName("ctCollectionId"));

		if (primaryKeyColumnNames.size() != 1) {
			return null;
		}

		return primaryKeyColumnNames.get(0);
	}

	public static String getTableName(
			Connection connection, DBInspector dbInspector,
			String fullyQualifiedName)
		throws Exception {

		String tableName = null;

		if (StringUtil.startsWith(
				fullyQualifiedName,
				"com.liferay.object.model.ObjectDefinition#")) {

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select dbTableName from ObjectDefinition where " +
							"className = ?")) {

				preparedStatement.setString(1, fullyQualifiedName);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						tableName = resultSet.getString(1);
					}
				}
			}

			return tableName;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			try {
				Class<?> clazz = bundle.loadClass(fullyQualifiedName);

				ImplementationClassName implementationClassName =
					clazz.getAnnotation(ImplementationClassName.class);

				if (implementationClassName == null) {
					tableName = StringUtil.extractLast(fullyQualifiedName, '.');

					break;
				}

				clazz = bundle.loadClass(implementationClassName.value());

				tableName = (String)clazz.getField(
					"TABLE_NAME"
				).get(
					null
				);

				break;
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}
		}

		return tableName;
	}

	public static boolean isLiferayTable(
		DBInspector dbInspector, Set<String> liferayTableNames,
		String tableName) {

		if (dbInspector.isObjectTable(tableName) ||
			liferayTableNames.contains(tableName)) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DataCleanupPreupgradeProcessUtil.class);

}