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

package com.liferay.journal.content.web.internal.upgrade.v1_0_0;

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			StringPool.PERCENT + JournalContentPortletKeys.JOURNAL_CONTENT +
				StringPool.PERCENT
		};
	}

	protected String[] upgradeBooleanAssetAddonEntry(
			String[] assetAddonEntryKeys, PortletPreferences portletPreferences,
			String preferenceKey)
		throws Exception {

		boolean preferenceValue = GetterUtil.getBoolean(
			portletPreferences.getValue(preferenceKey, null));

		if (preferenceValue) {
			assetAddonEntryKeys = ArrayUtil.append(
				assetAddonEntryKeys, preferenceKey);
		}

		portletPreferences.reset(preferenceKey);

		return assetAddonEntryKeys;
	}

	protected void upgradeContentMetadataAssetAddonEntryKeys(
			PortletPreferences portletPreferences)
		throws Exception {

		String[] contentMetadataAssetAddonEntryKeys = new String[0];

		contentMetadataAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			contentMetadataAssetAddonEntryKeys, portletPreferences,
			"enableCommentRatings");
		contentMetadataAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			contentMetadataAssetAddonEntryKeys, portletPreferences,
			"enableComments");
		contentMetadataAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			contentMetadataAssetAddonEntryKeys, portletPreferences,
			"enableRatings");
		contentMetadataAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			contentMetadataAssetAddonEntryKeys, portletPreferences,
			"enableRelatedAssets");

		portletPreferences.setValue(
			"contentMetadataAssetAddonEntryKeys",
			StringUtil.merge(contentMetadataAssetAddonEntryKeys));
	}

	protected String[] upgradeMultiValueAssetAddonEntryKeys(
			String[] assetAddonEntryKeys, PortletPreferences portletPreferences,
			String preferenceKey, Map<String, String> newPreferenceValues)
		throws Exception {

		String[] preferenceValues = portletPreferences.getValues(
			preferenceKey, null);

		if (preferenceValues != null) {
			for (String preferenceValue : preferenceValues) {
				String newPreferenceValue = newPreferenceValues.get(
					preferenceValue);

				if (Validator.isNotNull(newPreferenceValue)) {
					assetAddonEntryKeys = ArrayUtil.append(
						assetAddonEntryKeys, preferenceKey);
				}
			}
		}

		portletPreferences.reset(preferenceKey);

		return assetAddonEntryKeys;
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeContentMetadataAssetAddonEntryKeys(portletPreferences);
		upgradeUserToolAssetAddonEntryKeys(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeUserToolAssetAddonEntryKeys(
			PortletPreferences portletPreferences)
		throws Exception {

		String[] userToolAssetAddonEntryKeys = new String[0];

		userToolAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			userToolAssetAddonEntryKeys, portletPreferences, "enablePrint");

		Map<String, String> extensions = new HashMap<>();

		extensions.put("doc", "enableDOC");
		extensions.put("odt", "enableODT");
		extensions.put("pdf", "enablePDF");
		extensions.put("txt", "enableTXT");

		userToolAssetAddonEntryKeys = upgradeMultiValueAssetAddonEntryKeys(
			userToolAssetAddonEntryKeys, portletPreferences, "extensions",
			extensions);

		userToolAssetAddonEntryKeys = upgradeBooleanAssetAddonEntry(
			userToolAssetAddonEntryKeys, portletPreferences,
			"showAvailableLocales");

		portletPreferences.setValue(
			"userToolAssetAddonEntryKeys",
			StringUtil.merge(userToolAssetAddonEntryKeys));
	}

}