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

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuPortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU + "%"
		};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		_replacePreferenceName(
			portletPreferences, "includedLayouts", "expandedLevels");

		_replacePreferenceName(
			portletPreferences, "rootLayoutLevel", "rootMenuItemLevel");

		_replacePreferenceName(
			portletPreferences, "rootLayoutType", "rootMenuItemType");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private void _replacePreferenceName(
			PortletPreferences portletPreferences, String oldName,
			String newName)
		throws ReadOnlyException {

		String oldValue = GetterUtil.getString(
			portletPreferences.getValue(oldName, null));

		String newValue = GetterUtil.getString(
			portletPreferences.getValue(newName, null));

		if (Validator.isNotNull(oldValue) && Validator.isNull(newValue)) {
			portletPreferences.setValue(newName, oldValue);
		}

		portletPreferences.reset(oldName);
	}

}