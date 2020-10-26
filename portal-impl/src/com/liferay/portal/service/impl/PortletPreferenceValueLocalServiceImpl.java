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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValuePersistence;
import com.liferay.portal.service.base.PortletPreferenceValueLocalServiceBaseImpl;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class PortletPreferenceValueLocalServiceImpl
	extends PortletPreferenceValueLocalServiceBaseImpl {

	@Override
	public javax.portlet.PortletPreferences getPreferences(
		PortletPreferences portletPreferences) {

		Map<String, List<PortletPreferenceValue>> portletPreferenceValuesMap =
			getPortletPreferenceValuesMap(
				portletPreferenceValuePersistence,
				portletPreferences.getPortletPreferencesId());

		Map<String, Preference> preferenceMap = new HashMap<>();

		for (Map.Entry<String, List<PortletPreferenceValue>> entry :
				portletPreferenceValuesMap.entrySet()) {

			String name = entry.getKey();

			List<PortletPreferenceValue> portletPreferenceValues =
				entry.getValue();

			String[] values = new String[portletPreferenceValues.size()];

			boolean readOnly = false;

			for (int i = 0; i < portletPreferenceValues.size(); i++) {
				PortletPreferenceValue portletPreferenceValue =
					portletPreferenceValues.get(i);

				values[i] = portletPreferenceValue.getValue();

				if (portletPreferenceValue.isReadOnly()) {
					readOnly = true;
				}
			}

			preferenceMap.put(name, new Preference(name, values, readOnly));
		}

		return new PortletPreferencesImpl(
			portletPreferences.getCompanyId(), portletPreferences.getOwnerId(),
			portletPreferences.getOwnerType(), portletPreferences.getPlid(),
			portletPreferences.getPortletId(), null, preferenceMap);
	}

	protected static Map<String, List<PortletPreferenceValue>>
		getPortletPreferenceValuesMap(
			PortletPreferenceValuePersistence portletPreferenceValuePersistence,
			long portletPreferencesId) {

		Map<String, List<PortletPreferenceValue>> portletPreferenceValuesMap =
			new HashMap<>();

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValuePersistence.findByPortletPreferencesId(
					portletPreferencesId)) {

			List<PortletPreferenceValue> portletPreferenceValues =
				portletPreferenceValuesMap.computeIfAbsent(
					portletPreferenceValue.getName(),
					key -> new ArrayList<>(1));

			portletPreferenceValues.add(portletPreferenceValue);
		}

		return portletPreferenceValuesMap;
	}

}