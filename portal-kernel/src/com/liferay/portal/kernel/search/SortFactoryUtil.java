/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SortFactoryUtil {

	public static Sort create(String fieldName, boolean reverse) {
		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.create(fieldName, reverse);
	}

	public static Sort create(String fieldName, int type, boolean reverse) {
		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.create(fieldName, type, reverse);
	}

	public static Sort[] getDefaultSorts() {
		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.getDefaultSorts();
	}

	public static Sort getSort(
		Class<?> clazz, int type, String orderByCol, boolean inferSortField,
		String orderByType) {

		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.getSort(
			clazz, type, orderByCol, inferSortField, orderByType);
	}

	public static Sort getSort(
		Class<?> clazz, int type, String orderByCol, String orderByType) {

		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.getSort(clazz, type, orderByCol, orderByType);
	}

	public static Sort getSort(
		Class<?> clazz, String orderByCol, String orderByType) {

		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.getSort(clazz, orderByCol, orderByType);
	}

	public static Sort[] toArray(List<Sort> sorts) {
		SortFactory sortFactory = _sortFactorySnapshot.get();

		return sortFactory.toArray(sorts);
	}

	private static final Snapshot<SortFactory> _sortFactorySnapshot =
		new Snapshot<>(SortFactoryUtil.class, SortFactory.class);

}