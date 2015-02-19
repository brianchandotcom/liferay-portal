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

package com.liferay.language.web.upgrade.v1_0_0;

import com.liferay.language.web.constants.LanguagePortletKeys;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.taglib.ui.LanguageTag;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Eduardo Garcia
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {LanguagePortletKeys.LANGUAGE};
	}

	@SuppressWarnings("deprecation")
	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		int displayStyle = GetterUtil.getInteger(
			portletPreferences.getValue("displayStyle", null),
			LanguageTag.LIST_ICON);

		if (displayStyle == LanguageTag.LIST_LONG_TEXT) {
			portletPreferences.setValue("displayStyle", "long-text");
		}
		else if (displayStyle == LanguageTag.LIST_SHORT_TEXT) {
			portletPreferences.setValue("displayStyle", "short-text");
		}
		else if (displayStyle == LanguageTag.SELECT_BOX) {
			portletPreferences.setValue("displayStyle", "select-box");
		}
		else {
			portletPreferences.setValue("displayStyle", "icon");
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

}