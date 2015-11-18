/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * @author Tamas Molnar
 */
public class UpgradeIFrame extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"48_INSTANCE_%"};
	}

	protected void upgradePassword(PortletPreferences portletPreferences)
		throws Exception {

		String password = GetterUtil.getString(
			portletPreferences.getValue("password", null));

		if (Validator.isNotNull(password)) {
			portletPreferences.setValue("formPassword", password);
		}

		portletPreferences.reset("password");
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradePassword(portletPreferences);
		upgradeUserName(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeUserName(PortletPreferences portletPreferences)
		throws Exception {

		String userName = GetterUtil.getString(
			portletPreferences.getValue("userName", null));

		if (Validator.isNull(userName)) {
			userName = GetterUtil.getString(
				portletPreferences.getValue("user-name", null));
		}

		if (Validator.isNotNull(userName)) {
			portletPreferences.setValue("formUserName", userName);
		}

		portletPreferences.reset("user-name");
		portletPreferences.reset("userName");
	}

}