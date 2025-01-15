/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlParserUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Feliphe Marinho
 */
public class ObjectEntryValuesUtil {

	public static Object getTitleFieldValue(
		String businessType, Map<String, Object> modelAttributes,
		ObjectField objectField, User user, Map<String, Object> values) {

		String objectFieldName = objectField.getName();

		if (!values.containsKey(objectFieldName)) {
			return modelAttributes.get(objectField.getDBColumnName());
		}

		Object value = values.get(objectFieldName);

		if (StringUtil.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN)) {

			return GetterUtil.getBoolean(value);
		}

		if (!(value instanceof Map)) {
			return value;
		}

		Map<String, Object> localizedValues = (Map<String, Object>)value;

		String siteDefaultLanguageId = LanguageUtil.getLanguageId(
			LocaleUtil.getSiteDefault());

		if (localizedValues.containsKey(siteDefaultLanguageId)) {
			return localizedValues.get(siteDefaultLanguageId);
		}

		if ((user != null) &&
			localizedValues.containsKey(user.getLanguageId())) {

			return localizedValues.get(user.getLanguageId());
		}

		return localizedValues.get(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
	}

	public static Object getValue(
		String languageId, ObjectField objectField,
		Map<String, Object> values) {

		if (objectField == null) {
			return null;
		}

		if (StringUtil.equals(objectField.getName(), "creator")) {
			return values.get("userName");
		}
		else if (StringUtil.equals(objectField.getName(), "id")) {
			return values.get("objectEntryId");
		}

		Object value = values.get(objectField.getName());

		if ((languageId != null) && objectField.isLocalized()) {
			Map<String, Object> localizedValues =
				(Map<String, Object>)values.get(
					objectField.getI18nObjectFieldName());

			value = localizedValues.get(languageId);
		}

		if (objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			try {
				DLFileEntry dlFileEntry =
					DLFileEntryLocalServiceUtil.getDLFileEntry(
						GetterUtil.getLong(value));

				return dlFileEntry.getFileName();
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return StringPool.BLANK;
			}
		}
		else if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

			return HtmlParserUtil.extractText(GetterUtil.getString(value));
		}

		return value;
	}

	public static String getValueString(
		ObjectField objectField, Map<String, Serializable> values) {

		return String.valueOf(
			getValue(null, objectField, new HashMap<>(values)));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryValuesUtil.class);

}