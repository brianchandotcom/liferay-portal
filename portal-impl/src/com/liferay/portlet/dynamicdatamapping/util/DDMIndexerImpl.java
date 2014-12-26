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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class DDMIndexerImpl implements DDMIndexer {

	@Override
	public void addAttributes(
		Document document, DDMStructure ddmStructure,
		DDMFormValues ddmFormValues) {

		long groupId = GetterUtil.getLong(
			document.get(com.liferay.portal.kernel.search.Field.GROUP_ID));

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (DDMFormFieldValue ddmFormFieldValue :
			ddmFormValues.getDDMFormFieldValues()) {

			try {
				String indexType = ddmStructure.getFieldProperty(
					ddmFormFieldValue.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				for (Locale locale : locales) {
					String name = encodeName(
						ddmStructure.getStructureId(),
						ddmFormFieldValue.getName(), locale);

					String valueString = ddmFormFieldValue.getValue().getString(
						locale);

					String type = ddmStructure.getDDMFormField(
						ddmFormFieldValue.getName()).getType();

					if (type.equals(DDMImpl.TYPE_RADIO) ||
						type.equals(DDMImpl.TYPE_SELECT)) {

						JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
							valueString);

						String[] stringArray = ArrayUtil.toStringArray(
							jsonArray);

						if (indexType.equals("keyword")) {
							document.addKeyword(name, stringArray);
						}
						else {
							document.addText(name, stringArray);
						}
					}
					else {
						if (type.equals(DDMImpl.TYPE_DDM_TEXT_HTML)) {
							valueString = HtmlUtil.extractText(valueString);
						}

						if (indexType.equals("keyword")) {
							document.addKeyword(name, valueString);
						}
						else {
							document.addText(name, valueString);
						}
					}
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	@Override
	public String encodeName(long ddmStructureId, String fieldName) {
		return encodeName(ddmStructureId, fieldName, null);
	}

	@Override
	public String encodeName(
		long ddmStructureId, String fieldName, Locale locale) {

		StringBundler sb = new StringBundler(7);

		sb.append(DDM_FIELD_NAMESPACE);
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(ddmStructureId);
		sb.append(StringPool.DOUBLE_UNDERLINE);
		sb.append(fieldName);

		if (locale != null) {
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));
		}

		return sb.toString();
	}

	@Override
	public String extractIndexableAttributes(
		DDMStructure ddmStructure, DDMFormValues ddmFormValues, Locale locale) {

		StringBundler sb = new StringBundler();

		for (DDMFormFieldValue ddmFormFieldValue :
			ddmFormValues.getDDMFormFieldValues()) {

			try {
				String indexType = ddmStructure.getFieldProperty(
					ddmFormFieldValue.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				String valueString = ddmFormFieldValue.getValue().getString(
					locale);

				String type = ddmStructure.getDDMFormField(
					ddmFormFieldValue.getName()).getType();

				if (type.equals(DDMImpl.TYPE_RADIO) ||
					type.equals(DDMImpl.TYPE_SELECT)) {

					JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
						valueString);

					String[] stringArray = ArrayUtil.toStringArray(jsonArray);

					sb.append(stringArray);
					sb.append(StringPool.SPACE);
				}
				else {
					if (type.equals(DDMImpl.TYPE_DDM_TEXT_HTML)) {
						valueString = HtmlUtil.extractText(valueString);
					}

					sb.append(valueString);
					sb.append(StringPool.SPACE);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

}