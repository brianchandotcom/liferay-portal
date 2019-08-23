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

/**
 * @author Alejandro Tardín
 */
public class UpgradeTwitter extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from ClassName_ where value = " +
				"'com.liferay.twitter.model.Feed'");

		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_twitter_web_portlet_TwitterPortlet");

		runSQL(
			"delete from Portlet where portletId = " +
				"'com_liferay_twitter_web_portlet_TwitterPortlet'");

		runSQL(
			"delete from PortletPreferences where portletId = " +
				"'com_liferay_twitter_web_portlet_TwitterPortlet'");

		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.twitter.service'");
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.twitter.web'");

		runSQL(
			"delete from ResourcePermission where name = " +
				"'com.liferay.twitter.model.Feed'");

		runSQL("delete from ServiceComponent where buildNamespace = 'Twitter'");

		runSQL("drop table Twitter_Feed");
	}

}