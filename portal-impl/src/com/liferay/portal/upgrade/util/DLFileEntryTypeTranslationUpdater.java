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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.TranslationUpdater;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryTypeTranslationUpdater implements TranslationUpdater {

	@Override
	public void update(long companyId, long groupId) throws PortalException {
		try {
			doUpdate(companyId, groupId);
		}
		catch (SQLException sqle) {
			throw new UpgradeException(sqle);
		}
	}

	protected void doUpdate(long companyId, long groupId) throws SQLException {
		for (Map.Entry<String, String> nameAndKey :
				_DEFAULT_FILE_ENTRY_TYPE_MAP.entrySet()) {

			updateGroupDLFileEntryTypes(
				companyId, groupId, nameAndKey.getKey(), nameAndKey.getValue());
		}
	}

	protected void updateGroupDLFileEntryTypes(
			long companyId, long groupId, String nameLanguageKey,
			String dlFileEntryTypeKey)
		throws SQLException {

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(
				groupId, dlFileEntryTypeKey);

		if (dlFileEntryType == null) {
			return;
		}

		Map<Locale, String> descriptionMap =
			dlFileEntryType.getDescriptionMap();
		Map<Locale, String> nameMap = dlFileEntryType.getNameMap();

		boolean needsUpdate = false;

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));
		String defaultValue = LanguageUtil.get(defaultLocale, nameLanguageKey);

		for (Locale locale : LanguageUtil.getSupportedLocales()) {
			String description = descriptionMap.get(locale);
			String name = nameMap.get(locale);

			String localizedValue = LanguageUtil.get(locale, nameLanguageKey);

			if (!locale.equals(defaultLocale) &&
				localizedValue.equals(defaultValue)) {

				continue;
			}

			if (description == null) {
				descriptionMap.put(locale, localizedValue);

				needsUpdate = true;
			}

			if (name == null) {
				nameMap.put(locale, localizedValue);

				needsUpdate = true;
			}
		}

		if (needsUpdate) {
			dlFileEntryType.setDescriptionMap(descriptionMap);
			dlFileEntryType.setModifiedDate(DateUtil.newDate());
			dlFileEntryType.setNameMap(nameMap);

			DLFileEntryTypeLocalServiceUtil.updateDLFileEntryType(
				dlFileEntryType);
		}
	}

	private static Map<String, String> _DEFAULT_FILE_ENTRY_TYPE_MAP;

	static {
		_DEFAULT_FILE_ENTRY_TYPE_MAP = new HashMap<String, String>();

		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION);
	}

}