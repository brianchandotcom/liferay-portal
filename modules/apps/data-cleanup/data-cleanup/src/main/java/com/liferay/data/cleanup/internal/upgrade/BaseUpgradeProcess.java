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

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.data.cleanup.internal.upgrade.util.LayoutTypeSettingsUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseUpgradeProcess extends UpgradeProcess {

	protected void removePortletData(
			String[] bundleSymbolicNames, String[] oldPortletIds,
			String[] portletIds)
		throws Exception {

		if (ArrayUtil.getLength(oldPortletIds) > 0) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select portletId from Portlet where portletId = ?")) {

				preparedStatement.setString(1, oldPortletIds[0]);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						portletIds = oldPortletIds;
					}
				}
			}
		}

		LayoutTypeSettingsUtil.removePortletIds(connection, portletIds);

		_deleteFromPortlet(portletIds);

		_deleteFromPortletPreferences(portletIds);

		_deleteFromRelease(bundleSymbolicNames);

		_deleteFromResourceAction(portletIds);

		_deleteFromResourcePermission(portletIds);
	}

	protected void removeServiceData(
			String buildNamespace, String[] bundleSymbolicNames,
			String[] classNames, String[] tableNames)
		throws Exception {

		_deleteFromClassName(classNames);

		_deleteFromRelease(bundleSymbolicNames);

		_deleteFromResourceAction(classNames);

		_deleteFromResourcePermission(classNames);

		_deleteFromServiceComponent(buildNamespace);

		_dropTables(tableNames);
	}

	private void _deleteFrom(
			String tableName, String columnName, String... columnValues)
		throws Exception {

		if (columnValues == null) {
			return;
		}

		for (String columnValue : columnValues) {
			runSQL(
				StringBundler.concat(
					"delete from ", tableName, " where ", columnName, " = '",
					columnValue, "'"));
		}
	}

	private void _deleteFromClassName(String[] classNames) throws Exception {
		_deleteFrom("ClassName_", "value", classNames);
	}

	private void _deleteFromPortlet(String[] portletIds) throws Exception {
		_deleteFrom("Portlet", "portletId", portletIds);
	}

	private void _deleteFromPortletPreferences(String[] portletIds)
		throws Exception {

		if (portletIds == null) {
			return;
		}

		for (String portletId : portletIds) {
			runSQL(
				StringBundler.concat(
					"delete from PortletPreferences where portletId like '",
					portletId, "%'"));
		}
	}

	private void _deleteFromRelease(String[] servletContextNames)
		throws Exception {

		_deleteFrom("Release_", "servletContextName", servletContextNames);
	}

	private void _deleteFromResourceAction(String[] names) throws Exception {
		_deleteFrom("ResourceAction", "name", names);
	}

	private void _deleteFromResourcePermission(String[] names)
		throws Exception {

		_deleteFrom("ResourcePermission", "name", names);
	}

	private void _deleteFromServiceComponent(String buildNamespace)
		throws Exception {

		_deleteFrom("ServiceComponent", "buildNamespace", buildNamespace);
	}

	private void _dropTables(String[] tableNames) throws Exception {
		if (tableNames == null) {
			return;
		}

		for (String tableName : tableNames) {
			if (hasTable(tableName)) {
				runSQL("drop table " + tableName);
			}
		}
	}

}