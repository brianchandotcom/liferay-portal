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

/**
 * @author Sam Ziemer
 */
public class DirectoryUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.directory.web"},
			new String[] {"11", "186", "187", "188"},
			new String[] {
				"com_liferay_directory_web_portlet_DirectoryPortlet",
				"com_liferay_directory_web_portlet_FriendsDirectoryPortlet",
				"com_liferay_directory_web_portlet_SiteMembersDirectoryPortlet",
				"com_liferay_directory_web_portlet_MySitesDirectoryPortlet"
			});
	}

}