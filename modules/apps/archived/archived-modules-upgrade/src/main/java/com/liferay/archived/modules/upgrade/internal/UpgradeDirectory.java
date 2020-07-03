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

package com.liferay.archived.modules.upgrade.internal;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sam Ziemer
 */
public class UpgradeDirectory extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = connection.prepareStatement(
			"select * from Portlet where portletId = '11'");

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			LayoutTypeSettingsUtil.removePortletId(connection, "11");
			LayoutTypeSettingsUtil.removePortletId(connection, "186");
			LayoutTypeSettingsUtil.removePortletId(connection, "187");
			LayoutTypeSettingsUtil.removePortletId(connection, "188");

			runSQL("delete from Portlet where portletId = '11'");
			runSQL("delete from Portlet where portletId = '186'");
			runSQL("delete from Portlet where portletId = '187'");
			runSQL("delete from Portlet where portletId = '188'");

			runSQL("delete from PortletPreferences where portletId = '11'");
			runSQL("delete from PortletPreferences where portletId = '186'");
			runSQL("delete from PortletPreferences where portletId = '187'");
			runSQL("delete from PortletPreferences where portletId = '188'");

			runSQL("delete from ResourcePermission where name = '11'");
			runSQL("delete from ResourcePermission where name = '186'");
			runSQL("delete from ResourcePermission where name = '187'");
			runSQL("delete from ResourcePermission where name = '188'");
		}
		else {
			LayoutTypeSettingsUtil.removePortletId(
				connection,
				"com_liferay_directory_web_portlet_DirectoryPortlet");
			LayoutTypeSettingsUtil.removePortletId(
				connection,
				"com_liferay_directory_web_portlet_FriendsDirectoryPortlet");
			LayoutTypeSettingsUtil.removePortletId(
				connection,
				"com_liferay_directory_web_portlet_SiteMembersDirectoryPortlet");
			LayoutTypeSettingsUtil.removePortletId(
				connection,
				"com_liferay_directory_web_portlet_MySitesDirectoryPortlet");

			runSQL(
				"delete from Portlet where portletId = " +
					"'com_liferay_directory_web_portlet_DirectoryPortlet'");
			runSQL(
				"delete from Portlet where portletId = " +
					"'com_liferay_directory_web_portlet_FriendsDirectoryPortlet'");
			runSQL(
				"delete from Portlet where portletId = " +
					"'com_liferay_directory_web_portlet_SiteMembersDirectoryPortlet'");
			runSQL(
				"delete from Portlet where portletId = " +
					"'com_liferay_directory_web_portlet_MySitesDirectoryPortlet'");

			runSQL(
				"delete from PortletPreferences where portletId = " +
					"'com_liferay_directory_web_portlet_DirectoryPortlet'");
			runSQL(
				"delete from PortletPreferences where portletId = " +
					"'com_liferay_directory_web_portlet_FriendsDirectoryPortlet'");
			runSQL(
				"delete from PortletPreferences where portletId = " +
					"'com_liferay_directory_web_portlet_SiteMembersDirectoryPortlet'");
			runSQL(
				"delete from PortletPreferences where portletId = " +
					"'com_liferay_directory_web_portlet_MySitesDirectoryPortlet'");

			runSQL(
				"delete from ResourcePermission where name = " +
					"'com_liferay_directory_web_portlet_DirectoryPortlet'");
			runSQL(
				"delete from ResourcePermission where name = " +
					"'com_liferay_directory_web_portlet_FriendsDirectoryPortlet'");
			runSQL(
				"delete from ResourcePermission where name = " +
					"'com_liferay_directory_web_portlet_SiteMembersDirectoryPortlet'");
			runSQL(
				"delete from ResourcePermission where name = " +
					"'com_liferay_directory_web_portlet_MySitesDirectoryPortlet'");
		}

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.directory.web'");
	}

}