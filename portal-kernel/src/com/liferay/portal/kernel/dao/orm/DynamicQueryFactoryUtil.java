/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryUtil {

	public static DynamicQuery forClass(
		Class<?> clazz, ClassLoader classLoader) {

		return _dynamicQueryFactory.forClass(clazz, classLoader);
	}

	public static DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		return _dynamicQueryFactory.forClass(clazz, alias, classLoader);
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