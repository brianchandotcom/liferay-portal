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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradeSitemap extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {"85_INSTANCE_%"};
	}

	@Override
	protected String upgradePreferences(
			Connection con, long companyId, long ownerId, int ownerType,
			long plid, String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long rootLayoutId = GetterUtil.getLong(
			portletPreferences.getValue("root-layout-id", StringPool.BLANK));

		if (rootLayoutId > 0) {
			String uuid = getLayoutUuid(con, plid, rootLayoutId);

			if (uuid != null) {
				portletPreferences.setValue("root-layout-uuid", uuid);
			}

			portletPreferences.reset("root-layout-id");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	@Override
	protected String upgradePreferences(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, String xml) {

		return null;
	}

}