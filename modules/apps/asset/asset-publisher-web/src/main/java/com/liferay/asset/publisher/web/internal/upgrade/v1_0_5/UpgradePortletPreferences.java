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

package com.liferay.asset.publisher.web.internal.upgrade.v1_0_5;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;

import javax.portlet.PortletPreferences;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class UpgradePortletPreferences
	extends BasePortletPreferencesUpgradeProcess {

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

		String anyClassType = portletPreferences.getValue(
			"anyClassType", Boolean.TRUE.toString());
		String anyClassTypeDLFileEntryAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeDLFileEntryAssetRendererFactory",
				Boolean.TRUE.toString());
		String anyClassTypeJournalArticleAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeJournalArticleAssetRendererFactory",
				Boolean.TRUE.toString());

		if (anyClassType.equals("select-more-than-one")) {
			portletPreferences.setValue("anyClassType", "false");
		}

		if (anyClassTypeDLFileEntryAssetRendererFactory.equals(
				"select-more-than-one")) {

			portletPreferences.setValue(
				"anyClassTypeDLFileEntryAssetRendererFactory", "false");
		}

		if (anyClassTypeJournalArticleAssetRendererFactory.equals(
				"select-more-than-one")) {

			portletPreferences.setValue(
				"anyClassTypeJournalArticleAssetRendererFactory", "false");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}