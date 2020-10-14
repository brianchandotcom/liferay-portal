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
import com.liferay.petra.string.StringPool;
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
			ddmStructureParts[1], ddmFieldParts[1] + "_" + ddmFieldParts[2],
			ddmFieldParts[0], ddmStructureParts[2], ddmFieldParts[3]);
	}

	public String getIndexType() {
		return _indexType;
	}

	public String getLocale() {
		return _locale;
	}

	public String getName() {
		return _name;
	}

	public String getStructureId() {
		return _structureId;
	}

	public String getType() {
		return _type;
	}

	private DDMStructureField(
		String indexType, String locale, String name, String structureId,
		String type) {

		_indexType = indexType;
		_locale = locale;
		_name = name;
		_structureId = structureId;
		_type = type;
	}

	private final String _indexType;
	private final String _locale;
	private final String _name;
	private final String _structureId;
	private final String _type;

}