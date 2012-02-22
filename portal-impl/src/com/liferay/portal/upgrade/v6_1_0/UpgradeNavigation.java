/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradeNavigation extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"71_INSTANCE_%"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		boolean changed = false;

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String displayStyle = portletPreferences.getValue(
			"display-style", null);

		if (Validator.isNumber(displayStyle)) {
			int index = GetterUtil.getInteger(displayStyle);

			if ((index < 0) || (index > 6)) {
				index = 0;
			}

			portletPreferences.setValue(
				"display-style", _DISPLAY_STYLES[index]);

			changed = true;
		}

		if (changed) {
			return PortletPreferencesFactoryUtil.toXML(portletPreferences);
		}
		else {
			return xml;
		}
	}

	private static final String[] _DISPLAY_STYLES = {
		"", "relative-with-breadcrumb", "from-level-2-with-title",
		"from-level-1-with-title", "from-level-1",
		"from-level-1-to-all-sublevels", "from-level-0"
	};

}