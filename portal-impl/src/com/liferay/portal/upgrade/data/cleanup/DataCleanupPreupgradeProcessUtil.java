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

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessUtil {

	public static void clearCache() {
		_primaryKeyColumnNameCache.clear();
		_tableNameCache.clear();
	}

	public static String getPrimaryKeyColumnName(
			Connection connection, DBInspector dbInspector, String tableName)
		throws Exception {

		String primaryKeyColumnName = _primaryKeyColumnNameCache.get(tableName);

		if (primaryKeyColumnName != null) {
			if (primaryKeyColumnName == _NOT_FOUND) {
				return null;
			}

			return primaryKeyColumnName;
		}

		DB db = DBManagerUtil.getDB();

		List<String> primaryKeyColumnNames = new ArrayList<>(
			Arrays.asList(db.getPrimaryKeyColumnNames(connection, tableName)));

		primaryKeyColumnNames.remove(
			dbInspector.normalizeName("ctCollectionId"));

		if (primaryKeyColumnNames.size() != 1) {
			_primaryKeyColumnNameCache.putIfAbsent(tableName, _NOT_FOUND);

			return null;
		}

		primaryKeyColumnName = primaryKeyColumnNames.get(0);

		_primaryKeyColumnNameCache.putIfAbsent(tableName, primaryKeyColumnName);

		return primaryKeyColumnName;
	}

	public static String getTableName(
			Connection connection, DBInspector dbInspector,
			String fullyQualifiedName)
		throws Exception {

		String tableName = _tableNameCache.get(fullyQualifiedName);

		if (tableName != null) {
			if (tableName == _NOT_FOUND) {
				return null;
			}

			return tableName;
		}

		tableName = _computeTableName(connection, fullyQualifiedName);

		_tableNameCache.putIfAbsent(
			fullyQualifiedName, (tableName != null) ? tableName : _NOT_FOUND);

		return tableName;
	}

	private static String _computeTableName(
			Connection connection, String fullyQualifiedName)
		throws Exception {

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
						return resultSet.getString("dbTableName");
					}
				}
			}

			return null;
		}

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			try {
				Class<?> clazz = bundle.loadClass(fullyQualifiedName);

				ImplementationClassName implementationClassName =
					clazz.getAnnotation(ImplementationClassName.class);

				if (implementationClassName == null) {
					return null;
				}

				clazz = bundle.loadClass(implementationClassName.value());

				Field field = clazz.getField("TABLE_NAME");

				return (String)field.get(null);
			}
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException);
				}
			}
		}

		return null;
	}

	private static final String _NOT_FOUND = new String("");

	private static final Log _log = LogFactoryUtil.getLog(
		DataCleanupPreupgradeProcessUtil.class);

	private static final Map<String, String> _primaryKeyColumnNameCache =
		new ConcurrentHashMap<>();
	private static final Map<String, String> _tableNameCache =
		new ConcurrentHashMap<>();

}