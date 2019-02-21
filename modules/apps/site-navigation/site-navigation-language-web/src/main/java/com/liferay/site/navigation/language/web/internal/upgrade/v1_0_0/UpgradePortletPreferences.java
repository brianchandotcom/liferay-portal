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

package com.liferay.site.navigation.language.web.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.site.navigation.language.constants.SiteNavigationLanguagePortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Eduardo García
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			SiteNavigationLanguagePortletKeys.SITE_NAVIGATION_LANGUAGE
		};
	}

	@SuppressWarnings("deprecation")
	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String displayStyleString = portletPreferences.getValue(
			"displayStyle", null);

		if (Validator.isNull(displayStyleString)) {
			return;
		}

		int displayStyle = GetterUtil.getInteger(displayStyleString);

		if (displayStyle == LIST_ICON) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateConstants.DISPLAY_STYLE_PREFIX +
					"language-icon-ftl");
		}
		else if (displayStyle == LIST_LONG_TEXT) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateConstants.DISPLAY_STYLE_PREFIX +
					"language-long-text-ftl");
		}
		else if (displayStyle == LIST_SHORT_TEXT) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateConstants.DISPLAY_STYLE_PREFIX +
					"language-short-text-ftl");
		}
		else if (displayStyle == SELECT_BOX) {
			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateConstants.DISPLAY_STYLE_PREFIX +
					"language-select-box-ftl");
		}
		else {
			portletPreferences.reset("displayStyle");

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Display styles for languages are deprecated in favor of " +
						"widget templates");
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

	protected final int LIST_ICON = 0;

	protected final int LIST_LONG_TEXT = 1;

	protected final int LIST_SHORT_TEXT = 2;

	protected final int SELECT_BOX = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}