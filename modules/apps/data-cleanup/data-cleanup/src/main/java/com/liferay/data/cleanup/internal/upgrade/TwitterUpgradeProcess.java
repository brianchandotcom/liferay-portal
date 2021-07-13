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

/**
 * @author Alejandro Tardín
 */
public class TwitterUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		LayoutTypeSettingsUtil.removePortletId(
			connection, "com_liferay_twitter_web_portlet_TwitterPortlet");

		deleteFromClassName("com.liferay.twitter.model.Feed");

		deleteFromPortlet("com_liferay_twitter_web_portlet_TwitterPortlet");

		deleteFromPortletPreferences(
			"com_liferay_twitter_web_portlet_TwitterPortlet");

		deleteFromRelease(
			"com.liferay.twitter.service", "com.liferay.twitter.web");

		deleteFromResourcePermission("com.liferay.twitter.model.Feed");

		deleteFromServiceComponent("Twitter");

		dropTables("Twitter_Feed");
	}

}