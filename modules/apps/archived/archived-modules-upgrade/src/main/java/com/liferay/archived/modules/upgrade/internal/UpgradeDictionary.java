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
public class UpgradeDictionary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PreparedStatement ps = connection.prepareStatement(
			"select * from Portlet where portletId = '23'");

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			LayoutTypeSettingsUtil.removePortletId(connection, "23");

			runSQL("delete from Portlet where portletId = '23'");

			runSQL("delete from PortletPreferences where portletId = '23'");

			runSQL("delete from ResourcePermission where name = '23'");
		}
		else {
			LayoutTypeSettingsUtil.removePortletId(
				connection,
				"com_liferay_dictionary_web_portlet_DictionaryPortlet");

			runSQL(
				"delete from Portlet where portletId = " +
					"'com_liferay_dictionary_web_portlet_DictionaryPortlet'");

			runSQL(
				"delete from PortletPreferences where portletId = " +
					"'com_liferay_dictionary_web_portlet_DictionaryPortlet'");

			runSQL(
				"delete from ResourcePermission where name = " +
					"'com_liferay_dictionary_web_portlet_DictionaryPortlet'");
		}
	}

}