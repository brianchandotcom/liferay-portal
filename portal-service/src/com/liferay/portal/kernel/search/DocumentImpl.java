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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class DocumentImpl implements Document {

	public static String getLocalizedName(Locale locale, String name) {
		if (locale == null) {
			return name;
		}

		String languageId = LocaleUtil.toLanguageId(locale);

		return getLocalizedName(languageId, name);
	}

	public static String getLocalizedName(String languageId, String name) {
		return name.concat(StringPool.UNDERLINE).concat(languageId);
	}

	public static String getSortableFieldName(String name) {
		return name.concat(StringPool.UNDERLINE).concat(_SORTABLE_FIELD_SUFFIX);
	}

	public static boolean isSortableTextField(String name) {
		return _defaultSortableTextFields.contains(name);
	}

	@Override
	public void add(Field field) {
		_fields.put(field.getName(), field);
	}

	@Override
	public void addDate(String name, Date value) {
		if (value == null) {
			return;
		}

		addNumber(name, _dateFormat.format(value), Long.class);
	}

	@Override
	public void addDate(String name, Date[] values) {
		if (values == null) {
			return;
		}

		String[] dates = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			dates[i] = _dateFormat.format(values[i]);
		}

		addNumber(name, dates, Long.class);
	}

	@Override
	public void addFile(String name, byte[] bytes, String fileExt) {
		InputStream is = new UnsyncByteArrayInputStream(bytes);

		addFile(name, is, fileExt);
	}

	@Override
	public void addFile(String name, File file, String fileExt)
		throws IOException {

		InputStream is = new FileInputStream(file);

		addFile(name, is, fileExt);
	}

	@Override
	public void addFile(String name, InputStream is, String fileExt) {
		addText(name, FileUtil.extractText(is, fileExt));
	}

	@Override
	public void addKeyword(String name, boolean value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Boolean value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, boolean[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Boolean[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, double value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Double value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, double[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Double[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, float value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Float value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, float[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Float[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, int value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, int[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Integer value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Integer[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, long value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Long value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, long[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Long[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, short value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, Short value) {
		addKeyword(name, String.valueOf(value));
	}

	@Override
	public void addKeyword(String name, short[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, Short[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	@Override
	public void addKeyword(String name, String value) {
		addKeyword(name, value, false);
	}

	@Override
	public void addKeyword(String name, String value, boolean lowerCase) {
		if (lowerCase && Validator.isNotNull(value)) {
			value = StringUtil.toLowerCase(value);
		}

		Field field = new Field(name, value);

		for (String fieldName : Field.UNSCORED_FIELD_NAMES) {
			if (StringUtil.equalsIgnoreCase(name, fieldName)) {
				field.setBoost(0);
			}
		}

		_fields.put(name, field);
	}

	@Override
	public void addKeyword(String name, String[] values) {
		if (values == null) {
			return;
		}

		Field field = new Field(name, values);

		_fields.put(name, field);
	}

	@Override
	public void addLocalizedKeyword(String name, Map<Locale, String> values) {
		addLocalizedKeyword(name, values, false);
	}

	@Override
	public void addLocalizedKeyword(
		String name, Map<Locale, String> values, boolean lowerCase) {

		if ((values == null) || values.isEmpty()) {
			return;
		}

		if (lowerCase) {
			Map<Locale, String> lowerCaseValues = new HashMap<Locale, String>(
				values.size());

			for (Map.Entry<Locale, String> entry : values.entrySet()) {
				String value = GetterUtil.getString(entry.getValue());

				lowerCaseValues.put(
					entry.getKey(), StringUtil.toLowerCase(value));
			}

			values = lowerCaseValues;
		}

		Field field = new Field(name, values);

		_fields.put(name, field);
	}

	@Override
	public void addLocalizedText(String name, Map<Locale, String> values) {
		if ((values == null) || values.isEmpty()) {
			return;
		}

		Field field = new Field(name, values);

		field.setTokenized(true);

		_fields.put(name, field);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public void addModifiedDate() {
		addModifiedDate(new Date());
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public void addModifiedDate(Date modifiedDate) {
		addDate(Field.MODIFIED, modifiedDate);
	}

	@Override
	public void addNumber(String name, double value) {
		addNumber(name, String.valueOf(value), Double.class);
	}

	@Override
	public void addNumber(String name, Double value) {
		addNumber(name, String.valueOf(value), Double.class);
	}

	@Override
	public void addNumber(String name, double[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Double.class);
	}

	@Override
	public void addNumber(String name, Double[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Double.class);
	}

	@Override
	public void addNumber(String name, float value) {
		addNumber(name, String.valueOf(value), Float.class);
	}

	@Override
	public void addNumber(String name, Float value) {
		addNumber(name, String.valueOf(value), Float.class);
	}

	@Override
	public void addNumber(String name, float[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Float.class);
	}

	@Override
	public void addNumber(String name, Float[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Float.class);
	}

	@Override
	public void addNumber(String name, int value) {
		addNumber(name, String.valueOf(value), Integer.class);
	}

	@Override
	public void addNumber(String name, int[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Integer.class);
	}

	@Override
	public void addNumber(String name, Integer value) {
		addNumber(name, String.valueOf(value), Integer.class);
	}

	@Override
	public void addNumber(String name, Integer[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Integer.class);
	}

	@Override
	public void addNumber(String name, long value) {
		addNumber(name, String.valueOf(value), Long.class);
	}

	@Override
	public void addNumber(String name, Long value) {
		addNumber(name, String.valueOf(value), Long.class);
	}

	@Override
	public void addNumber(String name, long[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Long.class);
	}

	@Override
	public void addNumber(String name, Long[] values) {
		addNumber(name, ArrayUtil.toStringArray(values), Long.class);
	}

	@Override
	public void addNumber(String name, String value) {
		addNumber(name, value, Long.class);
	}

	public void addNumber(
		String name, String value, Class<? extends Number> clazz) {

		if (Validator.isNull(value)) {
			return;
		}

		addNumber(name, new String[] {value}, clazz);
	}

	@Override
	public void addNumber(String name, String[] values) {
		addNumber(name, values, Long.class);
	}

	public void addNumber(
		String name, String[] values, Class<? extends Number> clazz) {

		if (values == null) {
			return;
		}

		String sortableFieldName = getSortableFieldName(name);

		Field field = new Field(sortableFieldName, values);

		field.setNumeric(true);
		field.setNumericClass(clazz);

		_fields.put(sortableFieldName, field);

		addKeyword(name, values);
	}

	@Override
	public void addText(String name, String value) {
		if (Validator.isNull(value)) {
			return;
		}

		Field field = new Field(name, value);

		field.setTokenized(true);

		_fields.put(name, field);

		if (_sortableTextFields.contains(name)) {
			String truncatedValue = value;

			if (value.length() > _SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH) {
				truncatedValue = value.substring(
					0, _SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH);
			}

			addKeyword(getSortableFieldName(name), truncatedValue, true);
		}
	}

	@Override
	public void addText(String name, String[] values) {
		if (values == null) {
			return;
		}

		Field field = new Field(name, values);

		field.setTokenized(true);

		_fields.put(name, field);
	}

	@Override
	public void addUID(String portletId, long field1) {
		addUID(portletId, String.valueOf(field1));
	}

	@Override
	public void addUID(String portletId, long field1, String field2) {
		addUID(portletId, String.valueOf(field1), field2);
	}

	@Override
	public void addUID(String portletId, Long field1) {
		addUID(portletId, field1.longValue());
	}

	@Override
	public void addUID(String portletId, Long field1, String field2) {
		addUID(portletId, field1.longValue(), field2);
	}

	@Override
	public void addUID(String portletId, String field1) {
		addUID(portletId, field1, null);
	}

	@Override
	public void addUID(String portletId, String field1, String field2) {
		addUID(portletId, field1, field2, null);
	}

	@Override
	public void addUID(
		String portletId, String field1, String field2, String field3) {

		addUID(portletId, field1, field2, field3, null);
	}

	@Override
	public void addUID(
		String portletId, String field1, String field2, String field3,
		String field4) {

		String uid = portletId + _UID_PORTLET + field1;

		if (field2 != null) {
			uid += _UID_FIELD + field2;
		}

		if (field3 != null) {
			uid += _UID_FIELD + field3;
		}

		if (field4 != null) {
			uid += _UID_FIELD + field4;
		}

		addKeyword(Field.UID, uid);
	}

	@Override
	public Object clone() {
		DocumentImpl documentImpl = new DocumentImpl();

		documentImpl.setSortableTextFields(_sortableTextFields);

		return documentImpl;
	}

	@Override
	public String get(Locale locale, String name) {
		if (locale == null) {
			return get(name);
		}

		String localizedName = getLocalizedName(locale, name);

		Field field = _fields.get(localizedName);

		if (field == null) {
			field = _fields.get(name);
		}

		if (field == null) {
			return StringPool.BLANK;
		}

		return field.getValue();
	}

	@Override
	public String get(Locale locale, String name, String defaultName) {
		if (locale == null) {
			return get(name, defaultName);
		}

		String localizedName = getLocalizedName(locale, name);

		Field field = _fields.get(localizedName);

		if (field == null) {
			localizedName = getLocalizedName(locale, defaultName);

			field = _fields.get(localizedName);
		}

		if (field == null) {
			return StringPool.BLANK;
		}

		return field.getValue();
	}

	@Override
	public String get(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return StringPool.BLANK;
		}

		return field.getValue();
	}

	@Override
	public String get(String name, String defaultName) {
		Field field = _fields.get(name);

		if (field == null) {
			return get(defaultName);
		}

		return field.getValue();
	}

	@Override
	public Date getDate(String name) throws ParseException {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		return dateFormat.parse(get(name));
	}

	@Override
	public Field getField(String name) {
		return _fields.get(name);
	}

	@Override
	public Map<String, Field> getFields() {
		return _fields;
	}

	@Override
	public String getPortletId() {
		String uid = getUID();

		int pos = uid.indexOf(_UID_PORTLET);

		return uid.substring(0, pos);
	}

	@Override
	public String getUID() {
		Field field = _fields.get(Field.UID);

		if (field == null) {
			throw new RuntimeException("UID is not set");
		}

		return field.getValue();
	}

	@Override
	public String[] getValues(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return new String[] {StringPool.BLANK};
		}

		return field.getValues();
	}

	@Override
	public boolean hasField(String name) {
		if (_fields.containsKey(name)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDocumentSortableTextField(String name) {
		return _sortableTextFields.contains(name);
	}

	@Override
	public void remove(String name) {
		_fields.remove(name);
	}

	public void setFields(Map<String, Field> fields) {
		_fields = fields;
	}

	@Override
	public void setSortableTextFields(String[] sortableTextFields) {
		_sortableTextFields = SetUtil.fromArray(sortableTextFields);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5 * _fields.size());

		sb.append(StringPool.OPEN_CURLY_BRACE);

		boolean firstField = true;

		for (Field field : _fields.values()) {
			if (!firstField) {
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
			else {
				firstField = false;
			}

			sb.append(field.getName());
			sb.append(StringPool.EQUAL);
			sb.append(Arrays.toString(field.getValues()));
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	protected void setSortableTextFields(Set<String> sortableTextFields) {
		_sortableTextFields = sortableTextFields;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static final String _SORTABLE_FIELD_SUFFIX = "sortable";

	private static final int _SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.INDEX_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH));

	private static final String _UID_FIELD = "_FIELD_";

	private static final String _UID_PORTLET = "_PORTLET_";

	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);
	private static Set<String> _defaultSortableTextFields = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.INDEX_SORTABLE_TEXT_FIELDS));

	private Map<String, Field> _fields = new HashMap<String, Field>();
	private Set<String> _sortableTextFields = _defaultSortableTextFields;

}