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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseNoninstanceablePortletCleanup extends UpgradeProcess {

	protected void removePortlet(
			String bundleSymbolicName, String[] oldPortletIds,
			String[] portletIds)
		throws Exception {

		if (ArrayUtil.getLength(oldPortletIds) > 0) {
			try (PreparedStatement ps = connection.prepareStatement(
					"select portletId from Portlet where portletId = ?")) {

				ps.setString(1, oldPortletIds[0]);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
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