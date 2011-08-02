/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brett Swaim
 */
public class UpgradeScopes extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		updatePortletPreferences();
	}

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return "preferences like '%lfrScopeLayoutUuid%'";
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		boolean hasScopeType = GetterUtil.getBoolean(
			portletPreferences.getValue("lfrScopeType", "false"));

		if (!hasScopeType) {
			portletPreferences.setValue("lfrScopeType", "layout");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}