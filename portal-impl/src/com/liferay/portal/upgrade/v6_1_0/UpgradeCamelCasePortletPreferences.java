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
 * @author Eudaldo Alonso
 */
public class UpgradeCamelCasePortletPreferences
	extends BaseUpgradePortletPreferences {

	public UpgradeCamelCasePortletPreferences() {
		_camelCasePreferenceNames.put(
			"lfr-app-show-share-with-friends-link",
			"lfrAppShowShareWithFriendsLink");
		_camelCasePreferenceNames.put(
			"lfr-facebook-api-key", "lfrFacebookApiKey");
		_camelCasePreferenceNames.put(
			"lfr-facebook-canvas-page-url", "lfrFacebookCanvasPageUrl");
		_camelCasePreferenceNames.put(
			"lfr-facebook-show-add-app-link", "lfrFacebookShowAddAppLink");
		_camelCasePreferenceNames.put(
			"lfr-igoogle-show-add-app-link", "lfrIgoogleShowAddAppLink");
		_camelCasePreferenceNames.put(
			"lfr-netvibes-show-add-app-link", "lfrNetvibesShowAddAppLink");
		_camelCasePreferenceNames.put("lfr-scope-type", "lfrScopeType");
		_camelCasePreferenceNames.put("lfr-scope-uuid", "lfrScopeUuid");
		_camelCasePreferenceNames.put("lfr-sharing", "lfrSharing");
		_camelCasePreferenceNames.put(
			"lfr-wap-initial-window-state", "lfrWapInitialWindowState");
		_camelCasePreferenceNames.put("lfr-wap-title", "lfrWapTitle");
		_camelCasePreferenceNames.put(
			"lfr-widget-show-add-app-link", "lfrWidgetShowAddAppLink");
		_camelCasePreferenceNames.put("portlet-setup-css", "portletSetupCss");
		_camelCasePreferenceNames.put(
			"portlet-setup-link-to-layout-uuid",
			"portletSetupLinkToLayoutUuid");
		_camelCasePreferenceNames.put(
			"portlet-setup-show-borders", "portletSetupShowBorders");
		_camelCasePreferenceNames.put(
			"portlet-setup-use-custom-title", "portletSetupUseCustomTitle");
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

		for (String oldName : preferencesMap.keySet()) {
			String newName = _camelCasePreferenceNames.get(oldName);

			if (Validator.isNull(newName)) {
				if (oldName.startsWith(
						"portlet-setup-supported-clients-mobile-devices-")) {

					newName = StringUtil.replaceFirst(
						oldName,
						"portlet-setup-supported-clients-mobile-devices-",
						"portletSetupSupportedClientsMobileDevices_");
				}
				else if (oldName.startsWith("portlet-setup-title-")) {
					newName = StringUtil.replaceFirst(
						oldName, "portlet-setup-title-", "portletSetupTitle_");
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

	private final Map<String, String> _camelCasePreferenceNames =
		new HashMap<String, String>();

}