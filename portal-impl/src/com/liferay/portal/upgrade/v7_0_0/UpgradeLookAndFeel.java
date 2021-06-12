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

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.PortletPreferencesFactoryImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.Preference;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo García
 */
public class UpgradeLookAndFeel extends BasePortletPreferencesUpgradeProcess {

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return "preferences like '%portletSetupShowBorders%'";
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		Map<String, Preference> preferencesMap =
			PortletPreferencesFactoryImpl.createPreferencesMap(xml);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		for (Map.Entry<String, Preference> entry : preferencesMap.entrySet()) {
			String key = entry.getKey();
			Preference preference = entry.getValue();

			if (key.equals("portletSetupShowBorders")) {
				boolean showBorders = GetterUtil.getBoolean(
					preference.getValues()[0], true);

				if (!showBorders) {
					portletPreferences.setValue(
						"portletSetupPortletDecoratorId", "borderless");
				}
			}
			else {
				portletPreferences.setValues(key, preference.getValues());
			}
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}