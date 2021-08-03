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

package com.liferay.headless.delivery.dynamic.data.mapping;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Javier de Arcos
 */
public class DDMStructureField {

	public static DDMStructureField from(String ddmStructureField) {
		String[] ddmStructureParts = StringUtil.split(
			ddmStructureField, DDMIndexer.DDM_FIELD_SEPARATOR);

		String fieldReference = StringUtil.removeSubstring(
			ddmStructureParts[3], "_sortable");

		String type = fieldReference.substring(
			fieldReference.lastIndexOf(StringPool.UNDERLINE) + 1);

		fieldReference = fieldReference.substring(
			0, fieldReference.lastIndexOf(StringPool.UNDERLINE));

		String locale = _getSuffixLocale(fieldReference);

		if (locale != null) {
			fieldReference = StringUtil.removeSubstring(
				fieldReference, StringPool.UNDERLINE + locale);
		}

		return new DDMStructureField(
			ddmStructureParts[2], fieldReference, ddmStructureParts[1], locale,
			type);
	}

	public static String getNestedFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_FIELD_NAME);
	}

	public String getDDMStructureFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_PREFIX, _indexType,
			DDMIndexer.DDM_FIELD_SEPARATOR, _ddmStructureId,
			DDMIndexer.DDM_FIELD_SEPARATOR, _fieldReference,
			_getLocaleSuffix());
	}

	public String getDDMStructureNestedFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(_indexType), _getLocaleSuffix());
	}

	public String getDDMStructureNestedTypeSortableFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(_indexType), _getLocaleSuffix(),
			StringPool.UNDERLINE, _type, StringPool.UNDERLINE,
			Field.SORTABLE_FIELD_SUFFIX);
	}

	public String getLocale() {
		return _locale;
	}

	private static String _getSuffixLocale(String string) {
		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			String availableLanguageId = LocaleUtil.toLanguageId(
				availableLocale);

			if (string.endsWith(availableLanguageId)) {
				return availableLanguageId;
			}
		}

		return null;
	}

	private DDMStructureField(
		String ddmStructureId, String fieldReference, String indexType,
		String locale, String type) {

		_ddmStructureId = ddmStructureId;
		_fieldReference = fieldReference;
		_indexType = indexType;
		_locale = locale;
		_type = type;
	}

	private String _getLocaleSuffix() {
		if (_locale == null) {
			return StringPool.BLANK;
		}

		return StringPool.UNDERLINE.concat(_locale);
	}

	private final String _ddmStructureId;
	private final String _fieldReference;
	private final String _indexType;
	private final String _locale;
	private final String _type;

}