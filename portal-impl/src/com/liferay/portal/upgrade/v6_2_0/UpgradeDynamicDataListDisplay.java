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

import com.liferay.portal.kernel.upgrade.RenameUpgradePortletPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class UpgradeDynamicDataListDisplay
	extends RenameUpgradePortletPreferences {

	public UpgradeDynamicDataListDisplay() {
		_preferenceNamesMap.put("detailDDMTemplateId", "formDDMTemplateId");
		_preferenceNamesMap.put("listDDMTemplateId", "displayDDMTemplateId");
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"169_INSTANCE_%"};
	}

	@Override
	protected Map<String, String> getPreferenceNamesMap() {
		return _preferenceNamesMap;
	}

	private final Map<String, String> _preferenceNamesMap =
		new HashMap<String, String>();

}