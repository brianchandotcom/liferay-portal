/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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