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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.v6_2_0.BaseUpgradePortletPreferences;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Marcellus Tavares
 */
public abstract class RenameUpgradePortletPreferences
	extends BaseUpgradePortletPreferences {

	protected abstract Map<String, String> getPreferenceNamesMap();

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences preferences = PortletPreferencesFactoryUtil.fromXML(
			companyId, ownerId, ownerType, plid, portletId, xml);

		Map<String, String[]> preferencesMap = preferences.getMap();

		Map<String, String> preferenceNamesMap = getPreferenceNamesMap();

		for (Map.Entry<String, String> entry : preferenceNamesMap.entrySet()) {
			String name = entry.getKey();

			String[] values = preferencesMap.get(name);

			if (values == null) {
				continue;
			}

			preferences.reset(name);

			String newName = entry.getValue();

			String[] newValues = preferencesMap.get(newName);

			if (newValues == null) {
				preferences.setValues(newName, values);
			}
		}

		return PortletPreferencesFactoryUtil.toXML(preferences);
	}

}