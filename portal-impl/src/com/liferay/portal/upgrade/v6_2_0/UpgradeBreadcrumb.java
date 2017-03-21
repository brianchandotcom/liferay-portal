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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeBreadcrumb extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {StringUtil.quote("73", CharPool.PERCENT)};
	}

	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws Exception {

		String displayStyle = portletPreferences.getValue(
			"displayStyle", StringPool.BLANK);

		if (displayStyle.equals("1")) {
			portletPreferences.setValue("displayStyle", "horizontal");
		}
		else if (displayStyle.equals("2")) {
			portletPreferences.setValue("displayStyle", "vertical");
		}
		else {
			portletPreferences.reset("displayStyle");
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