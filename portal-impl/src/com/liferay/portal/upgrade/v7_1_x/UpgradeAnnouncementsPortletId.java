/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.portal.kernel.upgrade.BaseReplacePortletId;

/**
 * @author Preston Crary
 */
public class UpgradeAnnouncementsPortletId extends BaseReplacePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{
				"1_WAR_soannouncementsportlet",
				"com_liferay_announcements_web_portlet_AnnouncementsPortlet"
			},
			{"83", "com_liferay_announcements_web_portlet_AlertsPortlet"},
			{"84", "com_liferay_announcements_web_portlet_AnnouncementsPortlet"}
		};
	}

}