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
import com.liferay.petra.string.StringUtil;

/**
 * @author Kevin Lee
 */
public class OpenSocialUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.opensocial.model.Gadget'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.opensocial.model.OAuthConsumer'");
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.opensocial.model.OAuthToken'");

		LayoutTypeSettingsUtil.removePortletId(
			connection, "3_WAR_opensocialportlet");

		runSQL(
			"delete from Portlet where portletId like " +
				"'%_WAR_opensocialportlet%'");

		runSQL(
			"delete from PortletPreferences where portletId like " +
				"'%_WAR_opensocialportlet%'");

		_deleteResources();

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'opensocial-portlet'");

		runSQL(
			"delete from ServiceComponent where buildNamespace = 'OpenSocial'");

		_dropTables();
	}

	private void _deleteResources() throws Exception {
		String[] names = {
			"'1_WAR_opensocialportlet'", "'2_WAR_opensocialportlet'",
			"'3_WAR_opensocialportlet'", "'4_WAR_opensocialportlet'",
			"'com.liferay.opensocial'", "'com.liferay.opensocial.model.Gadget'",
			"'com.liferay.opensocial.model.OAuthConsumer'",
			"'com.liferay.opensocial.model.OAuthToken'"
		};

		String values = StringUtil.merge(names, ",");

		runSQL("delete from ResourceAction where name in (" + values + ")");
		runSQL("delete from ResourcePermission where name in (" + values + ")");
	}

	private void _dropTables() throws Exception {
		String[] tableNames = {
			"OpenSocial_Gadget", "OpenSocial_OAuthConsumer",
			"OpenSocial_OAuthToken"
		};

		for (String tableName : tableNames) {
			if (doHasTable(tableName)) {
				runSQL("drop table " + tableName);
			}
		}
	}

}