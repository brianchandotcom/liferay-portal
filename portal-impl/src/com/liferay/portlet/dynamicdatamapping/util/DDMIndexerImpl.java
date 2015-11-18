/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
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

import java.math.BigDecimal;

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

		long groupId = GetterUtil.getLong(
			document.get(com.liferay.portal.kernel.search.Field.GROUP_ID));

		Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

		for (Field field : fields) {
			try {
				String indexType = ddmStructure.getFieldProperty(
					field.getName(), "indexType");

				String structureKey = ddmStructure.getStructureKey();

				if (structureKey.equals("TIKARAWMETADATA")) {
					indexType = "text";
				}

				if (Validator.isNull(indexType)) {
					continue;
				}

				for (Locale locale : locales) {
					String name = encodeName(
						ddmStructure.getStructureId(), field.getName(), locale);

					Serializable value = field.getValue(locale);

					if (value instanceof BigDecimal) {
						Double doubleValue = ((BigDecimal)value).doubleValue();

						document.addNumberSortable(name, doubleValue);
					}
					else if (value instanceof BigDecimal[]) {
						BigDecimal[] bigDecimals = (BigDecimal[])value;

						Double[] doubleValues = new Double[bigDecimals.length];

						for (int i = 0; i < bigDecimals.length; i++) {
							doubleValues[i] = bigDecimals[i].doubleValue();
						}

						document.addNumberSortable(name, doubleValues);
					}
					else if (value instanceof Boolean) {
						document.addKeywordSortable(name, (Boolean)value);
					}
					else if (value instanceof Boolean[]) {
						document.addKeywordSortable(name, (Boolean[])value);
					}
					else if (value instanceof Date) {
						document.addDate(name, (Date)value);
					}
					else if (value instanceof Date[]) {
						document.addDate(name, (Date[])value);
					}
					else if (value instanceof Double) {
						document.addNumberSortable(name, (Double)value);
					}
					else if (value instanceof Double[]) {
						document.addNumberSortable(name, (Double[])value);
					}
					else if (value instanceof Integer) {
						document.addNumberSortable(name, (Integer)value);
					}
					else if (value instanceof Integer[]) {
						document.addNumberSortable(name, (Integer[])value);
					}
					else if (value instanceof Long) {
						document.addNumberSortable(name, (Long)value);
					}
					else if (value instanceof Long[]) {
						document.addNumberSortable(name, (Long[])value);
					}
					else if (value instanceof Float) {
						document.addNumberSortable(name, (Float)value);
					}
					else if (value instanceof Float[]) {
						document.addNumberSortable(name, (Float[])value);
					}
					else if (value instanceof Number[]) {
						Number[] numbers = (Number[])value;

						Double[] doubles = new Double[numbers.length];

						for (int i = 0; i < numbers.length; i++) {
							doubles[i] = numbers[i].doubleValue();
						}

						document.addNumberSortable(name, doubles);
					}
					else if (value instanceof Object[]) {
						String[] valuesString = ArrayUtil.toStringArray(
							(Object[])value);

						if (indexType.equals("keyword")) {
							document.addKeywordSortable(name, valuesString);
						}
						else {
							document.addTextSortable(name, valuesString);
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
								document.addKeywordSortable(name, stringArray);
							}
							else {
								document.addTextSortable(name, stringArray);
							}
						}
						else {
							if (type.equals(DDMImpl.TYPE_DDM_TEXT_HTML)) {
								valueString = HtmlUtil.extractText(valueString);
							}

							if (indexType.equals("keyword")) {
								document.addKeywordSortable(name, valueString);
							}
							else {
								document.addTextSortable(name, valueString);
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

		sb.append(DDM_FIELD_PREFIX);
		sb.append(ddmStructureId);
		sb.append(DDM_FIELD_SEPARATOR);
		sb.append(fieldName);

		if (Validator.isNotNull(locale)) {
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

				String structureKey = ddmStructure.getStructureKey();

				if (structureKey.equals("TIKARAWMETADATA")) {
					indexType = "text";
				}

				if (Validator.isNull(indexType)) {
					continue;
				}

				Serializable value = field.getValue(locale);

				if ((value instanceof Boolean) || (value instanceof Number)) {
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

	private static Log _log = LogFactoryUtil.getLog(DDMIndexerImpl.class);

}