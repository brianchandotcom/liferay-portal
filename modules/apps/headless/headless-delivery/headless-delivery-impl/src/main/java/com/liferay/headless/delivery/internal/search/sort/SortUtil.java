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

package com.liferay.headless.delivery.internal.search.sort;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.delivery.internal.dynamic.data.mapping.DDMStructureField;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier de Arcos
 */
public class SortUtil {

	public static FieldSort[] getFieldSorts(
		DDMIndexer ddmIndexer, Sort[] oldSorts, Queries queries, Sorts sorts) {

		List<FieldSort> fieldSorts = new ArrayList<>();

		for (Sort oldSort : oldSorts) {
			String sortFieldName = oldSort.getFieldName();
			SortOrder sortOrder =
				oldSort.isReverse() ? SortOrder.DESC : SortOrder.ASC;

			if (!sortFieldName.startsWith(DDMIndexer.DDM_FIELD_PREFIX) ||
				ddmIndexer.isLegacyDDMIndexFieldsEnabled()) {

				fieldSorts.add(sorts.field(sortFieldName, sortOrder));

				continue;
			}

			DDMStructureField ddmStructureField = DDMStructureField.from(
				sortFieldName);

			FieldSort fieldSort = sorts.field(
				_getFieldSortField(ddmStructureField), sortOrder);

			NestedSort nestedSort = sorts.nested(DDMIndexer.DDM_FIELD_ARRAY);

			nestedSort.setFilterQuery(
				queries.term(
					_getNestedSortFilterQueryTermField(),
					_getNestedSortFilterQueryValue(ddmStructureField)));

			fieldSort.setNestedSort(nestedSort);

			fieldSorts.add(fieldSort);
		}

		return fieldSorts.toArray(new FieldSort[0]);
	}

	private static String _getFieldSortField(
		DDMStructureField ddmStructureField) {

		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_VALUE_FIELD_NAME_PREFIX,
			StringUtil.upperCaseFirstLetter(ddmStructureField.getIndexType()),
			StringPool.UNDERLINE, ddmStructureField.getLocale(),
			StringPool.UNDERLINE, ddmStructureField.getType(),
			StringPool.UNDERLINE, Field.SORTABLE_FIELD_SUFFIX);
	}

	private static String _getNestedSortFilterQueryTermField() {
		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_ARRAY, StringPool.PERIOD,
			DDMIndexer.DDM_FIELD_NAME);
	}

	private static String _getNestedSortFilterQueryValue(
		DDMStructureField ddmStructureField) {

		return StringBundler.concat(
			DDMIndexer.DDM_FIELD_PREFIX, ddmStructureField.getIndexType(),
			DDMIndexer.DDM_FIELD_SEPARATOR, ddmStructureField.getStructureId(),
			DDMIndexer.DDM_FIELD_SEPARATOR, ddmStructureField.getName(),
			StringPool.UNDERLINE, ddmStructureField.getLocale());
	}

}