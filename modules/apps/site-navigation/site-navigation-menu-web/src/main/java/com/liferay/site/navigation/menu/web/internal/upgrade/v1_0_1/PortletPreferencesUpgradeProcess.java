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

package com.liferay.site.navigation.menu.web.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.site.navigation.constants.SiteNavigationMenuPortletKeys;

/**
 * @author Balázs Sáfrány-Kovalik
 * @author Preston Crary
 */
public class PortletPreferencesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_renamePortletPreferences("includedLayouts", "expandedLevels");
		_renamePortletPreferences("rootLayoutLevel", "rootMenuItemLevel");
		_renamePortletPreferences("rootLayoutType", "rootMenuItemType");
	}

	private void _renamePortletPreferences(String oldName, String newName)
		throws Exception {

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
			"PortletItem on PortletItem.portletItemId = " +
				"PortletPreferences.ownerId");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			"Company on Company.companyId = PortletPreferences.ownerId");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_GROUP,
			"Group_ on Group_.groupId = PortletPreferences.ownerId");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			"Layout on Layout.plid = PortletPreferences.plid");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			"LayoutRevision on LayoutRevision.layoutRevisionId = " +
				"PortletPreferences.plid");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION,
			"Organization_ on Organization_.organizationId = " +
				"PortletPreferences.ownerId");

		_renamePortletPreferences(
			oldName, newName, PortletKeys.PREFS_OWNER_TYPE_USER,
			"User_ on User_.userId = PortletPreferences.ownerId");
	}

	private void _renamePortletPreferences(
			String oldName, String newName, int ownerType, String join)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update PortletPreferenceValue set name = '", newName,
				"' where portletPreferencesId in (select portletPreferencesId ",
				"from PortletPreferences inner join ", join,
				" where PortletPreferences.ownerType = ", ownerType,
				" and PortletPreferences.portletId like '",
				SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU,
				"%') and name = '", oldName, "'"));
	}

}