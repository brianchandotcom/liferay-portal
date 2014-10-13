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
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.text.Format;

import java.util.Date;
import java.util.Locale;

/**
 * @author Alexander Chow
 */
public class DDMIndexerImpl implements DDMIndexer {

	@Override
	public void addAttributes(
		Document document, DDMStructure ddmStructure, Fields fields) {

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				for (Locale locale : LanguageUtil.getAvailableLocales()) {
					String name = encodeName(
						ddmStructure.getStructureId(), field.getName(), locale);

					Serializable value = field.getValue(locale);

					if (value instanceof Boolean) {
						document.addKeyword(name, (Boolean)value);
					}
					else if (value instanceof Boolean[]) {
						document.addKeyword(name, (Boolean[])value);
					}
					else if (value instanceof Date) {
						document.addDate(name, (Date)value);
					}
					else if (value instanceof Date[]) {
						document.addDate(name, (Date[])value);
					}
					else if (value instanceof Double) {
						document.addNumber(name, (Double)value);
					}
					else if (value instanceof Double[]) {
						document.addNumber(name, (Double[])value);
					}
					else if (value instanceof Integer) {
						document.addNumber(name, (Integer)value);
					}
					else if (value instanceof Integer[]) {
						document.addNumber(name, (Integer[])value);
					}
					else if (value instanceof Long) {
						document.addNumber(name, (Long)value);
					}
					else if (value instanceof Long[]) {
						document.addNumber(name, (Long[])value);
					}
					else if (value instanceof Float) {
						document.addNumber(name, (Float)value);
					}
					else if (value instanceof Float[]) {
						document.addNumber(name, (Float[])value);
					}
					else if (value instanceof Object[]) {
						String[] valuesString = ArrayUtil.toStringArray(
							(Object[])value);

						if (indexType.equals("keyword")) {
							document.addKeyword(name, valuesString);
						}
						else {
							document.addText(name, valuesString);
						}
					}
					else {
						String valueString = String.valueOf(value);

						String type = field.getType();

						if (type.equals(DDMImpl.TYPE_RADIO) ||
							type.equals(DDMImpl.TYPE_SELECT)) {

							JSONArray jsonArray =
								JSONFactoryUtil.createJSONArray(valueString);

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
		DDMStructure ddmStructure, Fields fields, Locale locale) {

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

		StringBundler sb = new StringBundler();

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				if (Validator.isNull(indexType)) {
					continue;
				}

				Serializable value = field.getValue(locale);

				if ((value instanceof Boolean) || (value instanceof Double) ||
					(value instanceof Integer) || (value instanceof Long) ||
					(value instanceof Float)) {

					sb.append(value);
					sb.append(StringPool.SPACE);
				}
				else if (value instanceof Date) {
					sb.append(dateFormat.format(value));
					sb.append(StringPool.SPACE);
				}
				else if (value instanceof Date[]) {
					Date[] dates = (Date[])value;

					for (Date date : dates) {
						sb.append(dateFormat.format(date));
						sb.append(StringPool.SPACE);
					}
				}
				else if (value instanceof Object[]) {
					Object[] values = (Object[])value;

					for (Object object : values) {
						sb.append(object);
						sb.append(StringPool.SPACE);
					}
				}
				else {
					String valueString = String.valueOf(value);

					String type = field.getType();

					if (type.equals(DDMImpl.TYPE_RADIO) ||
						type.equals(DDMImpl.TYPE_SELECT)) {

						JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
							valueString);

						String[] stringArray = ArrayUtil.toStringArray(
							jsonArray);

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