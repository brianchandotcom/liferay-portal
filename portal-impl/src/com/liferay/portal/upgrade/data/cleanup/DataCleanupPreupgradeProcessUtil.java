/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Luis Ortiz
 */
public class DataCleanupPreupgradeProcessUtil {

	public static Set<String> getLiferayTableNames(Connection connection)
		throws Exception {

		Set<String> liferayTableNames = new TreeSet<>(
			String.CASE_INSENSITIVE_ORDER);

		liferayTableNames.addAll(
			DBResourceUtil.getServiceComponentModuleTableNames(connection));
		liferayTableNames.addAll(
			DBResourceUtil.getServiceComponentPortalTableNames(connection));
		liferayTableNames.addAll(
			DBResourceUtil.getModuleTableNames(connection));
		liferayTableNames.addAll(
			DBResourceUtil.getPortalTableNames(connection));

		return liferayTableNames;
	}

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
			boolean applyFallback, DBInspector dbInspector,
			String fullyQualifiedName, Set<String> liferayTableNames)
		throws Exception {

		String tableName = null;

		try {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			Class<?> clazz = classLoader.loadClass(fullyQualifiedName);

			ImplementationClassName implementationClassName =
				clazz.getAnnotation(ImplementationClassName.class);

			if (implementationClassName == null) {
				tableName = StringUtil.extractLast(fullyQualifiedName, '.');
			}
			else {
				clazz = classLoader.loadClass(implementationClassName.value());

				tableName = (String)clazz.getField(
					"TABLE_NAME"
				).get(
					null
				);
			}
		}
		catch (ClassNotFoundException classNotFoundException) {
			if (_log.isDebugEnabled()) {
				_log.debug(classNotFoundException);
			}

			if (applyFallback) {
				tableName = StringUtil.extractLast(fullyQualifiedName, '.');
			}
		}

		if ((tableName == null) ||
			!isLiferayTable(dbInspector, liferayTableNames, tableName)) {

			return null;
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