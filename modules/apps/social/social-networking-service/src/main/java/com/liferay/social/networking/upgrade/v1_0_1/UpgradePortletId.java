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

import com.liferay.social.networking.constants.SocialNetworkingPortletKeys;

/**
 * @author Adolfo Pérez
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{
				"1_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.SUMMARY
			},
			{
				"2_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.FRIENDS
			},
			{"3_WAR_socialnetworkingportlet", SocialNetworkingPortletKeys.WALL},
			{
				"4_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.FRIENDS_ACTIVITIES
			},
			{
				"5_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.MEMBERS
			},
			{"6_WAR_socialnetworkingportlet", SocialNetworkingPortletKeys.MAP},
			{
				"7_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.MEETUPS
			},
			{
				"8_WAR_socialnetworkingportlet",
				SocialNetworkingPortletKeys.MEMBERS_ACTIVITIES
			}
		};
	}

}