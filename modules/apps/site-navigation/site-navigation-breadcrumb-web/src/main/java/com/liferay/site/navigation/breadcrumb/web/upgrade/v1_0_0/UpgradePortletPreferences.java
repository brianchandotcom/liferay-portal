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

package com.liferay.site.navigation.breadcrumb.web.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.site.navigation.breadcrumb.web.constants.SiteNavigationBreadcrumbPortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			SiteNavigationBreadcrumbPortletKeys.SITE_NAVIGATION_BREADCRUMB
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

		if (displayStyle.equals("horizontal")) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
					"breadcrumb-horizontal-ftl");
		}
		else if (displayStyle.equals("vertical")) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
					"breadcrumb-vertical-ftl");
		}
		else {
			portletPreferences.reset("displayStyle");

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Display styles for breadcrumbs are deprecated in favor " +
						"of application display templates");
			}
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}