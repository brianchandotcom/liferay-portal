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

package com.liferay.journal.content.web.upgrade.v1_0_0;

import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

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

	protected String[] upgradeBooleanSelectableEntry(
		String[] selectableEntries, PortletPreferences portletPreferences,
		String preferenceKey) throws ReadOnlyException {

		boolean preferenceValue = GetterUtil.getBoolean(
			portletPreferences.getValue(preferenceKey, null));

		if (preferenceValue) {
			selectableEntries = ArrayUtil.append(
				selectableEntries, preferenceKey);
		}

		portletPreferences.reset(preferenceKey);

		return selectableEntries;
	}

	protected void upgradeContentMetadataEntries(
			PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String[] contentMetadataEntries = new String[0];

		contentMetadataEntries = upgradeBooleanSelectableEntry(
			contentMetadataEntries, portletPreferences, "enableRatings");
		contentMetadataEntries = upgradeBooleanSelectableEntry(
			contentMetadataEntries, portletPreferences, "enableComments");
		contentMetadataEntries = upgradeBooleanSelectableEntry(
			contentMetadataEntries, portletPreferences, "enableCommentRatings");
		contentMetadataEntries = upgradeBooleanSelectableEntry(
			contentMetadataEntries, portletPreferences, "enableRelatedAssets");

		portletPreferences.setValue(
			"contentMetadataEntries", StringUtil.merge(contentMetadataEntries));
	}

	protected String[] upgradeMultiValueSelectableEntry(
			String[] selectableEntries, PortletPreferences portletPreferences,
			String preferenceKey, Map<String, String> conversionValues)
		throws ReadOnlyException {

		String[] preferenceValues = portletPreferences.getValues(
			preferenceKey, null);

		if (preferenceValues != null) {
			for (String value : preferenceValues) {
				String convertedValue = conversionValues.get(value);

				if (Validator.isNotNull(convertedValue)) {
					selectableEntries = ArrayUtil.append(
						selectableEntries, preferenceKey);
				}
			}
		}

		portletPreferences.reset(preferenceKey);

		return selectableEntries;
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeUserToolEntries(portletPreferences);
		upgradeContentMetadataEntries(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeUserToolEntries(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String[] userToolEntries = new String[0];

		userToolEntries = upgradeBooleanSelectableEntry(
			userToolEntries, portletPreferences, "enablePrint");
		userToolEntries = upgradeBooleanSelectableEntry(
			userToolEntries, portletPreferences, "showAvailableLocales");

		Map<String, String> extensions = new HashMap<>();

		extensions.put("pdf", "enablePDF");
		extensions.put("odt", "enableODT");
		extensions.put("doc", "enableDOC");
		extensions.put("txt", "enableTXT");

		userToolEntries = upgradeMultiValueSelectableEntry(
			userToolEntries, portletPreferences, "extensions", extensions);

		portletPreferences.setValue(
			"userToolEntries", StringUtil.merge(userToolEntries));
	}

}