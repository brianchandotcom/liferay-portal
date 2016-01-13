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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.util.PortletKeys;

/**
 * @author Cristina González
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {"147", PortletKeys.ASSET_CATEGORIES_ADMIN},
			new String[] {"99", PortletKeys.ASSET_TAGS_ADMIN},
			new String[] {"134", PortletKeys.SITE_ADMIN},
			new String[] {"165", PortletKeys.SITE_SETTINGS},
			new String[] {"191", PortletKeys.SITE_TEAMS},
			new String[] {
				"1_WAR_soannouncementsportlet", PortletKeys.ANNOUNCEMENTS
			},
			new String[] {"83", PortletKeys.ALERTS},
			new String[] {"84", PortletKeys.ANNOUNCEMENTS},
			new String[] {"19", PortletKeys.MESSAGE_BOARDS},
			new String[] {"162", PortletKeys.MESSAGE_BOARDS_ADMIN},
			new String[] {"33", PortletKeys.BLOGS},
			new String[] {"115", PortletKeys.BLOGS_AGGREGATOR},
			new String[] {"161", PortletKeys.BLOGS_ADMIN},
			new String[] {"139", PortletKeys.EXPANDO}
		};
	}

}