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

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletId;
import com.liferay.portal.kernel.util.PortletKeys;

/**
 * @author Cristina González
 */
public class UpgradePortletId extends BaseUpgradePortletId {

	@Override
	protected void doUpgrade() throws Exception {
		_deleteLegacyResourcePermission();

		upgrade(new UpgradeUserNotificationEvent());

		super.doUpgrade();
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{"115", PortletKeys.BLOGS_AGGREGATOR},
			{"125", PortletKeys.USERS_ADMIN},
			{"127", PortletKeys.USER_GROUPS_ADMIN},
			{"128", PortletKeys.ROLES_ADMIN},
			{"129", PortletKeys.PASSWORD_POLICIES_ADMIN},
			{"134", PortletKeys.SITE_ADMIN}, {"139", PortletKeys.EXPANDO},
			{"140", PortletKeys.MY_PAGES}, {"146", _LAYOUT_PROTOTYPE},
			{"147", _ASSET_CATEGORIES_ADMIN}, {"149", _LAYOUT_SET_PROTOTYPE},
			{"153", PortletKeys.MY_WORKFLOW_TASK},
			{"154", PortletKeys.WIKI_ADMIN}, {"156", PortletKeys.GROUP_PAGES},
			{"161", PortletKeys.BLOGS_ADMIN},
			{"162", PortletKeys.MESSAGE_BOARDS_ADMIN}, {"165", _SITE_SETTINGS},
			{"167", _DYNAMIC_DATA_LISTS}, {"174", _SITE_MEMBERSHIPS_ADMIN},
			{"183", PortletKeys.PORTLET_DISPLAY_TEMPLATE},
			{"19", PortletKeys.MESSAGE_BOARDS}, {"191", _SITE_TEAMS},
			{"192", _SITE_TEMPLATE_SETTINGS},
			{"198", PortletKeys.BOOKMARKS_ADMIN},
			{"199", PortletKeys.DOCUMENT_LIBRARY_ADMIN},
			{"20", PortletKeys.DOCUMENT_LIBRARY}, {"28", PortletKeys.BOOKMARKS},
			{"31", PortletKeys.MEDIA_GALLERY_DISPLAY},
			{"33", PortletKeys.BLOGS}, {"36", PortletKeys.WIKI},
			{"54", PortletKeys.WIKI_DISPLAY}, {"83", PortletKeys.ALERTS},
			{"88", _LAYOUTS_ADMIN}, {"99", _ASSET_TAGS_ADMIN}
		};
	}

	private void _deleteLegacyResourcePermission() throws Exception {
		runSQL(
			"delete from ResourcePermission where name = '161' and primKey " +
				"like '%LAYOUT_33'");
		runSQL(
			"delete from ResourcePermission where name = '162' and primKey " +
				"like '%LAYOUT_19'");
	}

	private static final String _ASSET_CATEGORIES_ADMIN =
		"com_liferay_asset_categories_admin_web_portlet_" +
			"AssetCategoriesAdminPortlet";

	private static final String _ASSET_TAGS_ADMIN =
		"com_liferay_asset_tags_admin_web_portlet_AssetTagsAdminPortlet";

	private static final String _DYNAMIC_DATA_LISTS =
		"com_liferay_dynamic_data_lists_web_portlet_DDLPortlet";

	private static final String _LAYOUT_PROTOTYPE =
		"com_liferay_layout_prototype_web_portlet_LayoutPrototypePortlet";

	private static final String _LAYOUT_SET_PROTOTYPE =
		"com_liferay_layout_set_prototype_web_portlet_" +
			"LayoutSetPrototypePortlet";

	private static final String _LAYOUTS_ADMIN =
		"com_liferay_layout_admin_web_portlet_LayoutAdminPortlet";

	private static final String _SITE_MEMBERSHIPS_ADMIN =
		"com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet";

	private static final String _SITE_SETTINGS =
		"com_liferay_site_admin_web_portlet_SiteSettingsPortlet";

	private static final String _SITE_TEAMS =
		"com_liferay_site_teams_web_portlet_SiteTeamsPortlet";

	private static final String _SITE_TEMPLATE_SETTINGS =
		"com_liferay_layout_set_prototype_web_portlet_" +
			"SiteTemplateSettingsPortlet";

}