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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
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

	public static void processSorts(
		DDMIndexer ddmIndexer, SearchRequestBuilder searchRequestBuilder,
		Sort[] oldSorts, Queries queries, Sorts sorts) {

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
				ddmStructureField.getDDMStructureNestedTypeSortableFieldName(),
				sortOrder);

			NestedSort nestedSort = sorts.nested(DDMIndexer.DDM_FIELD_ARRAY);

			nestedSort.setFilterQuery(
				queries.term(
					DDMStructureField.getNestedFieldName(),
					ddmStructureField.getDDMStructureFieldName()));

			fieldSort.setNestedSort(nestedSort);

			fieldSorts.add(fieldSort);
		}

		searchRequestBuilder.sorts(fieldSorts.toArray(new FieldSort[0]));
	}

}