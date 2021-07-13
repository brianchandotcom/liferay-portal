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

	protected void deleteFromClassName(String... values) throws Exception {
		for (String value : values) {
			runSQL("delete from ClassName_ where value = '" + value + "'");
		}
	}

	protected void deleteFromPortlet(String... portletIds) throws Exception {
		for (String portletId : portletIds) {
			String operator = "=";

			if (portletId.endsWith("%") || portletId.startsWith("%")) {
				operator = "like";
			}

			runSQL(
				StringBundler.concat(
					"delete from Portlet where portletId ", operator, " '",
					portletId, "'"));
		}
	}

	protected void deleteFromPortletPreferences(String... portletIds)
		throws Exception {

		for (String portletId : portletIds) {
			String operator = "=";

			if (portletId.endsWith("%") || portletId.startsWith("%")) {
				operator = "like";
			}

			runSQL(
				StringBundler.concat(
					"delete from PortletPreferences where portletId ", operator,
					" '", portletId, "'"));
		}
	}

	protected void deleteFromRelease(String... servletContextNames)
		throws Exception {

		for (String servletContextName : servletContextNames) {
			runSQL(
				"delete from Release_ where servletContextName = '" +
					servletContextName + "'");
		}
	}

	protected void deleteFromResourcePermission(String... names)
		throws Exception {

		for (String name : names) {
			runSQL(
				"delete from ResourcePermission where name = '" + name + "'");
		}
	}

	protected void deleteFromServiceComponent(String... buildNamespaces)
		throws Exception {

		for (String buildNamespace : buildNamespaces) {
			runSQL(
				"delete from ServiceComponent where buildNamespace = '" +
					buildNamespace + "'");
		}
	}

	protected void dropTables(String... tableNames) throws Exception {
		for (String tableName : tableNames) {
			if (hasTable(tableName)) {
				runSQL("drop table " + tableName);
			}
		}
	}

	protected void removeNoninstanceablePortlet(
			String bundleSymbolicName, String[] oldPortletIds,
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

		for (String portletId : portletIds) {
			LayoutTypeSettingsUtil.removePortletId(connection, portletId);

			runSQL("delete from Portlet where portletId = '" + portletId + "'");

			runSQL(
				"delete from PortletPreferences where portletId = '" +
					portletId + "'");

			runSQL(
				"delete from ResourcePermission where name = '" + portletId +
					"'");
		}

		runSQL(
			"delete from Release_ where servletContextName = '" +
				bundleSymbolicName + "'");
	}

}