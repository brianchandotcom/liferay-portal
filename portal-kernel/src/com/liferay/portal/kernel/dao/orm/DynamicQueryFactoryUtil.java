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

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryUtil {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #forClass(Class,
	 *             ClassLoader)}
	 */
	@Deprecated
	public static DynamicQuery forClass(Class<?> clazz) {
		return getDynamicQueryFactory().forClass(clazz);
	}

	public static DynamicQuery forClass(
		Class<?> clazz, ClassLoader classLoader) {

		return getDynamicQueryFactory().forClass(clazz, classLoader);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #forClass(Class,
	 *             String, ClassLoader)}
	 */
	@Deprecated
	public static DynamicQuery forClass(Class<?> clazz, String alias) {
		return getDynamicQueryFactory().forClass(clazz, alias);
	}

	public static DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		return getDynamicQueryFactory().forClass(clazz, alias, classLoader);
	}

	public static DynamicQueryFactory getDynamicQueryFactory() {
		return _dynamicQueryFactory;
	}

	public void setDynamicQueryFactory(
		DynamicQueryFactory dynamicQueryFactory) {

		_dynamicQueryFactory = dynamicQueryFactory;
	}

	private static DynamicQueryFactory _dynamicQueryFactory;

}