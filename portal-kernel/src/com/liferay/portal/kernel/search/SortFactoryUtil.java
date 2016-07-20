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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SortFactoryUtil {

	public static Sort create(String fieldName, boolean reverse) {
		return _getSortFactory().create(fieldName, reverse);
	}

	public static Sort create(String fieldName, int type, boolean reverse) {
		return _getSortFactory().create(fieldName, type, reverse);
	}

	public static Sort[] getDefaultSorts() {
		return _getSortFactory().getDefaultSorts();
	}

	public static Sort getSort(
		Class<?> clazz, int type, String orderByCol, boolean inferSortField,
		String orderByType) {

		return _getSortFactory().getSort(
			clazz, type, orderByCol, inferSortField, orderByType);
	}

	public static Sort getSort(
		Class<?> clazz, int type, String orderByCol, String orderByType) {

		return _getSortFactory().getSort(clazz, type, orderByCol, orderByType);
	}

	public static Sort getSort(
		Class<?> clazz, String orderByCol, String orderByType) {

		return _getSortFactory().getSort(clazz, orderByCol, orderByType);
	}

	/**
	 * @deprecated As of 7.1.0, replaced by {@link #_getSortFactory()}
	 */
	@Deprecated
	public static SortFactory getSortFactory() {
		return _getSortFactory();
	}

	public static Sort[] toArray(List<Sort> sorts) {
		return _getSortFactory().toArray(sorts);
	}

	private static SortFactory _getSortFactory() {
		PortalRuntimePermission.checkGetBeanProperty(SortFactoryUtil.class);

		return _sortFactory;
	}

	private static volatile SortFactory _sortFactory =
		ProxyFactory.newServiceTrackedInstance(
			SortFactory.class, SortFactoryUtil.class, "_sortFactory");

}