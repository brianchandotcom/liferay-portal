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

import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Cristina González
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {"115", PortletKeys.BLOGS_AGGREGATOR},
			new String[] {"125", PortletKeys.USERS_ADMIN},
			new String[] {"127", PortletKeys.USER_GROUPS_ADMIN},
			new String[] {"128", PortletKeys.ROLES_ADMIN},
			new String[] {"129", PortletKeys.PASSWORD_POLICIES_ADMIN},
			new String[] {"134", PortletKeys.SITE_ADMIN},
			new String[] {"139", PortletKeys.EXPANDO},
			new String[] {"140", PortletKeys.MY_PAGES},
			new String[] {"146", PortletKeys.LAYOUT_PROTOTYPE},
			new String[] {"147", PortletKeys.ASSET_CATEGORIES_ADMIN},
			new String[] {"149", PortletKeys.LAYOUT_SET_PROTOTYPE},
			new String[] {"156", PortletKeys.GROUP_PAGES},
			new String[] {"161", PortletKeys.BLOGS_ADMIN},
			new String[] {"162", PortletKeys.MESSAGE_BOARDS_ADMIN},
			new String[] {"165", PortletKeys.SITE_SETTINGS},
			new String[] {"174", PortletKeys.SITE_MEMBERSHIPS_ADMIN},
			new String[] {"19", PortletKeys.MESSAGE_BOARDS},
			new String[] {"191", PortletKeys.SITE_TEAMS},
			new String[] {"192", PortletKeys.SITE_TEMPLATE_SETTINGS},
			new String[] {"199", PortletKeys.DOCUMENT_LIBRARY_ADMIN},
			new String[] {
				"1_WAR_soannouncementsportlet", PortletKeys.ANNOUNCEMENTS
			},
			new String[] {"20", PortletKeys.DOCUMENT_LIBRARY},
			new String[] {"31", PortletKeys.MEDIA_GALLERY_DISPLAY},
			new String[] {"33", PortletKeys.BLOGS},
			new String[] {"83", PortletKeys.ALERTS},
			new String[] {"84", PortletKeys.ANNOUNCEMENTS},
			new String[] {"88", PortletKeys.LAYOUTS_ADMIN},
			new String[] {"99", PortletKeys.ASSET_TAGS_ADMIN}
		};
	}

}