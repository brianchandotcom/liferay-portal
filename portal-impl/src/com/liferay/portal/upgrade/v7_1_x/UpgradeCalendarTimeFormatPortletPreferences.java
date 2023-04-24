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

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;

import javax.portlet.PortletPreferences;

/**
 * @author Inácio Nery
 */
public class UpgradeCalendarTimeFormatPortletPreferences
	extends BasePortletPreferencesUpgradeProcess {

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return StringBundler.concat(
			"(preferences like '%isoTimeFormat%", Boolean.TRUE,
			"%') or (preferences like '%isoTimeFormat%", Boolean.FALSE, "%')");
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String isoTimeFormat = portletPreferences.getValue(
			"isoTimeFormat", Boolean.FALSE.toString());

		if (isoTimeFormat.equals(Boolean.TRUE.toString())) {
			portletPreferences.setValue("timeFormat", "24-hour");
		}
		else {
			portletPreferences.setValue("timeFormat", "am-pm");
		}

		portletPreferences.reset("isoTimeFormat");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}