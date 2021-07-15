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

	protected void deleteFrom(
			String tableName, String fieldName, String... fieldValues)
		throws Exception {

		for (String fieldValue : fieldValues) {
			runSQL(
				String.format(
					"delete from %s where %s = '%s'", tableName, fieldName,
					fieldValue));
		}
	}

	protected void deleteFromClassName(String... classNames) throws Exception {
		deleteFrom("ClassName_", "value", classNames);
	}

	protected void deleteFromPortlet(String... portletIds) throws Exception {
		deleteFrom("Portlet", "portletId", portletIds);
	}

	protected void deleteFromPortletPreferences(String... portletIds)
		throws Exception {

		for (String portletId : portletIds) {
			runSQL(
				StringBundler.concat(
					"delete from PortletPreferences where portletId like '",
					portletId, "%'"));
		}
	}

	protected void deleteFromRelease(String... servletContextNames)
		throws Exception {

		deleteFrom("Release_", "servletContextName", servletContextNames);
	}

	protected void deleteFromResourceAction(String... names) throws Exception {
		deleteFrom("ResourceAction", "name", names);
	}

	protected void deleteFromResourcePermission(String... names)
		throws Exception {

		deleteFrom("ResourcePermission", "name", names);
	}

	protected void deleteFromServiceComponent(String... buildNamespaces)
		throws Exception {

		deleteFrom("ServiceComponent", "buildNamespace", buildNamespaces);
	}

	protected void dropTables(String... tableNames) throws Exception {
		for (String tableName : tableNames) {
			if (hasTable(tableName)) {
				runSQL("drop table " + tableName);
			}
		}
	}

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

		deleteFromPortlet(portletIds);

		deleteFromPortletPreferences(portletIds);

		deleteFromRelease(bundleSymbolicNames);

		deleteFromResourceAction(portletIds);

		deleteFromResourcePermission(portletIds);
	}

	protected void removeServiceData(
			String buildNamespace, String[] bundleSymbolicNames,
			String[] classNames, String[] tables)
		throws Exception {

		deleteFromClassName(classNames);

		deleteFromRelease(bundleSymbolicNames);

		deleteFromResourceAction(classNames);

		deleteFromResourcePermission(classNames);

		deleteFromServiceComponent(buildNamespace);

		dropTables(tables);
	}

}