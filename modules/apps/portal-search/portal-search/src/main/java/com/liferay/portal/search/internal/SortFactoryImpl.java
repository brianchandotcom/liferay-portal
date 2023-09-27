/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactory;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.sort.SortFieldBuilder;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Hugo Huijser
 */
@Component(service = SortFactory.class)
public class SortFactoryImpl implements SortFactory {

	@Override
	public Sort create(String fieldName, boolean reverse) {
		return new Sort(fieldName, reverse);
	}

	@Override
	public Sort create(String fieldName, int type, boolean reverse) {
		return new Sort(fieldName, type, reverse);
	}

	@Override
	public Sort[] getDefaultSorts() {
		return _DEFAULT_SORTS;
	}

	@Override
	public Sort getSort(
		Class<?> clazz, int type, String orderByCol, boolean inferSortField,
		String orderByType) {

		String sortFieldName = orderByCol;

		if (orderByCol.equals(Field.PRIORITY)) {
			inferSortField = false;
		}

		if (inferSortField) {
			sortFieldName = sortFieldBuilder.getSortField(
				clazz, orderByCol, type);
		}

		if (Validator.isNull(orderByType)) {
			orderByType = "asc";
		}

		return new Sort(
			sortFieldName, type,
			!StringUtil.equalsIgnoreCase(orderByType, "asc"));
	}

	@Override
	public Sort getSort(
		Class<?> clazz, int type, String orderByCol, String orderByType) {

		return getSort(clazz, type, orderByCol, true, orderByType);
	}

	@Override
	public Sort getSort(Class<?> clazz, String orderByCol, String orderByType) {
		return getSort(clazz, Sort.STRING_TYPE, orderByCol, orderByType);
	}

	@Override
	public Sort[] toArray(List<Sort> sorts) {
		if (ListUtil.isEmpty(sorts)) {
			return new Sort[0];
		}

		Sort[] sortsArray = new Sort[sorts.size()];

		for (int i = 0; i < sorts.size(); i++) {
			sortsArray[i] = sorts.get(i);
		}

		return sortsArray;
	}

	@Reference
	protected SortFieldBuilder sortFieldBuilder;

	private static final Sort[] _DEFAULT_SORTS = {
		new Sort(null, Sort.SCORE_TYPE, false),
		new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, true)
	};

}