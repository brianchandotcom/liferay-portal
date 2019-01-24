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

package com.liferay.site.navigation.menu.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.site.navigation.menu.web.internal.constants.SiteNavigationMenuPortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Eduardo García
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU + "%"
		};
	}

	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String displayStyle = GetterUtil.getString(
			portletPreferences.getValue("displayStyle", null));

		if (Validator.isNull(displayStyle) ||
			displayStyle.startsWith(
				PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX)) {

			return;
		}

		portletPreferences.setValue(
			"displayStyle",
			PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
				"list-menu-ftl");

		_persistSupportedProperties(portletPreferences, displayStyle);

		_removeUnsupportedPreferences(portletPreferences);
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeDisplayStyle(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private void _persistSupportedProperties(
			PortletPreferences portletPreferences, String displayStyle)
		throws ReadOnlyException {

		String includedLayouts = "auto";
		String rootLayoutLevel = "1";
		String rootLayoutType = "absolute";

		if (displayStyle.equals("[custom]")) {
			includedLayouts = portletPreferences.getValue(
				"includedLayouts", includedLayouts);
			rootLayoutLevel = portletPreferences.getValue(
				"rootLayoutLevel", rootLayoutLevel);
			rootLayoutType = portletPreferences.getValue(
				"rootLayoutType", rootLayoutType);
		}
		else {
			String[] displayStyleDefinition = PropsUtil.getArray(
				"navigation.display.style", new Filter(displayStyle));

			if ((displayStyleDefinition != null) &&
				(displayStyleDefinition.length != 0)) {

				includedLayouts = displayStyleDefinition[3];
				rootLayoutLevel = displayStyleDefinition[2];
				rootLayoutType = displayStyleDefinition[1];
			}
			else {
				if (displayStyle.equals("from-level-0")) {
					rootLayoutLevel = "0";
				}
				else if (displayStyle.equals("from-level-1-to-all-sublevels")) {
					includedLayouts = "all";
				}
				else if (displayStyle.equals("from-level-2-with-title")) {
					rootLayoutLevel = "2";
				}
				else if (displayStyle.equals("relative-with-breadcrumb")) {
					rootLayoutLevel = "0";
					rootLayoutType = "relative";
				}
			}
		}

		portletPreferences.setValue("includedLayouts", includedLayouts);
		portletPreferences.setValue("rootLayoutLevel", rootLayoutLevel);
		portletPreferences.setValue("rootLayoutType", rootLayoutType);
	}

	private void _removeUnsupportedPreferences(
			PortletPreferences portletPreferences)
		throws ReadOnlyException {

		portletPreferences.reset("bulletStyle");
		portletPreferences.reset("headerType");
		portletPreferences.reset("nestedChildren");
	}

}