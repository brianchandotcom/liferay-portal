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

package com.liferay.social.networking.upgrade.v1_0_1;

/**
 * @author Adolfo Pérez
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{"1_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_summary_portlet_" +
					"SummaryPortlet"},
			{"2_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_friends_portlet_" +
					"FriendsPortlet"},
			{"3_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_wall_portlet_" +
					"WallPortlet"},
			{"4_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_friendsactivities_" +
					"portlet_FriendsActivitiesPortlet"},
			{"5_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_members_portlet_" +
					"MembersPortlet"},
			{"6_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_map_portlet_MapPortlet"},
			{"7_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_meetups_portlet_" +
					"MeetupsPortlet"},
			{"8_WAR_socialnetworkingportlet",
				"com_liferay_socialnetworking_membersactivities_" +
					"portlet_MembersActivitiesPortlet"}
		};
	}

}