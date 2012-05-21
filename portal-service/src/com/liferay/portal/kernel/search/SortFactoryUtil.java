/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SortFactoryUtil {

	public static Sort create(String fieldName, boolean reverse) {
		return getSortFactory().create(fieldName, reverse);
	}

	public static Sort create(String fieldName, int type, boolean reverse) {
		return getSortFactory().create(fieldName, type, reverse);
	}

	public static Sort[] getDefaultSorts() {
		return getSortFactory().getDefaultSorts();
	}

	public static Sort getSort(
		Class<?> clazz, String orderByCol, String orderByType) {

		return getSortFactory().getSort(clazz, orderByCol, orderByType);
	}

	public static SortFactory getSortFactory() {
		PortalRuntimePermission.checkGetBeanProperty(SortFactoryUtil.class);

		return _sortFactory;
	}

	public static Sort[] toArray(List<Sort> sorts) {
		return getSortFactory().toArray(sorts);
	}

	public void setSortFactory(SortFactory sortFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_sortFactory = sortFactory;
	}

	private static SortFactory _sortFactory;

}