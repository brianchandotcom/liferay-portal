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

package com.liferay.asset.publisher.web.internal.upgrade.v1_0_2;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Cristina Rodríguez
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_%"
		};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		int pageDelta = GetterUtil.getInteger(
			portletPreferences.getValue("pageDelta", StringPool.BLANK));

		int delta = GetterUtil.getInteger(
			portletPreferences.getValue("delta", StringPool.BLANK));

		if ((delta == GetterUtil.DEFAULT_INTEGER) &&
			(pageDelta != GetterUtil.DEFAULT_INTEGER)) {

			portletPreferences.setValue("delta", String.valueOf(pageDelta));
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}