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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 *
 * @author Eudaldo Alonso
 */
public class UpgradeCommonPortletPreferences
	extends BaseUpgradePortletPreferences {

	protected void createCommonPreferencesMap() {
		if (Validator.isNotNull(camelCasePreferencesNames)) {
			return;
		}

		camelCasePreferencesNames = new HashMap<String, String>();

		camelCasePreferencesNames.put("lfr-scope-type", "lfrScopeType");
		camelCasePreferencesNames.put("lfr-scope-uuid", "lfrScopeUuid");
		camelCasePreferencesNames.put("lfr-sharing", "lfrSharing");
		camelCasePreferencesNames.put(
			"lfr-facebook-api-key", "lfrFacebookApiKey");
		camelCasePreferencesNames.put(
			"lfr-facebook-canvas-page-url", "lfrFacebookCanvasPageUrl");
		camelCasePreferencesNames.put(
			"lfr-facebook-show-add-app-link", "lfrFacebookShowAddAppLink");
		camelCasePreferencesNames.put(
			"lfr-widget-show-add-app-link", "lfrWidgetShowAddAppLink");
		camelCasePreferencesNames.put(
			"lfr-app-show-share-with-friends-link",
			"lfrAppShowShareWithFriendsLink");
		camelCasePreferencesNames.put(
			"lfr-igoogle-show-add-app-link", "lfrIgoogleShowAddAppLink");
		camelCasePreferencesNames.put(
			"lfr-netvibes-show-add-app-link", "lfrNetvibesShowAddAppLink");
		camelCasePreferencesNames.put(
			"portlet-setup-use-custom-title", "portletSetupUseCustomTitle");
		camelCasePreferencesNames.put(
			"portlet-setup-show-borders", "portletSetupShowBorders");
		camelCasePreferencesNames.put(
			"portlet-setup-link-to-layout-uuid",
			"portletSetupLinkToLayoutUuid");
		camelCasePreferencesNames.put("portlet-setup-css", "portletSetupCss");
		camelCasePreferencesNames.put("lfr-wap-title", "lfrWapTitle");
		camelCasePreferencesNames.put(
			"lfr-wap-initial-window-state", "lfrWapInitialWindowState");
	}

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return StringPool.BLANK;
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		Map<String, String[]> preferencesMap = portletPreferences.getMap();

		createCommonPreferencesMap();

		for (String oldName : preferencesMap.keySet()) {
			String newName = camelCasePreferencesNames.get(oldName);

			if (Validator.isNull(newName)) {
				if (oldName.startsWith("portlet-setup-title-")) {
					newName = StringUtil.replaceFirst(
						oldName, "portlet-setup-title-", "portletSetupTitle_");
				}
				else if (oldName.startsWith(
					"portlet-setup-supported-clients-mobile-devices-")) {

					newName = StringUtil.replaceFirst(
						oldName,
						"portlet-setup-supported-clients-mobile-devices-",
						"portletSetupSupportedClientsMobileDevices_");
				}
			}

			if (Validator.isNotNull(newName)) {
				String[] values = preferencesMap.get(oldName);

				portletPreferences.reset(oldName);
				portletPreferences.setValues(newName, values);
			}
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private Map<String, String> camelCasePreferencesNames;
}