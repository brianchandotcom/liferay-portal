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

package com.liferay.headless.delivery.internal.dynamic.data.mapping;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Javier de Arcos
 */
public class DDMStructureField {

	public static DDMStructureField from(String ddmStructureField) {
		String[] ddmStructureParts = StringUtil.split(
			ddmStructureField, DDMIndexer.DDM_FIELD_SEPARATOR);

		String[] ddmFieldParts = StringUtil.split(
			ddmStructureParts[3], StringPool.UNDERLINE);

		return new DDMStructureField(
			ddmStructureParts[2], ddmStructureParts[1],
			ddmFieldParts[1] + "_" + ddmFieldParts[2], ddmFieldParts[0],
			ddmFieldParts[3]);
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
			DDMIndexer.DDM_FIELD_SEPARATOR, _name, StringPool.UNDERLINE,
			_locale);
	}

	public String getDDMStructureNestedFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(_indexType), StringPool.UNDERLINE,
			_locale);
	}

	public String getDDMStructureNestedTypeSortableFieldName() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(_indexType), StringPool.UNDERLINE,
			_locale, StringPool.UNDERLINE, _type, StringPool.UNDERLINE,
			Field.SORTABLE_FIELD_SUFFIX);
	}

	public String getLocale() {
		return _locale;
	}

	private DDMStructureField(
		String ddmStructureId, String indexType, String locale, String name,
		String type) {

		_ddmStructureId = ddmStructureId;
		_indexType = indexType;
		_locale = locale;
		_name = name;
		_type = type;
	}

	private final String _ddmStructureId;
	private final String _indexType;
	private final String _locale;
	private final String _name;
	private final String _type;

}