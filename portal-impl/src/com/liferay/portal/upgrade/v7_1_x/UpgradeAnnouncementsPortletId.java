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