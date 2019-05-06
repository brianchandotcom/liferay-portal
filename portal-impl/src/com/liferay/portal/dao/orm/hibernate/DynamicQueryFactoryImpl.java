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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryImpl implements DynamicQueryFactory {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #forClass(Class,
	 *             ClassLoader)}
	 */
	@Deprecated
	@Override
	public DynamicQuery forClass(Class<?> clazz) {
		clazz = getImplClass(clazz, null);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	@Override
	public DynamicQuery forClass(Class<?> clazz, ClassLoader classLoader) {
		clazz = getImplClass(clazz, classLoader);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #forClass(Class,
	 *             String, ClassLoader)}
	 */
	@Deprecated
	@Override
	public DynamicQuery forClass(Class<?> clazz, String alias) {
		clazz = getImplClass(clazz, null);

		if (alias != null) {
			return new DynamicQueryImpl(
				DetachedCriteria.forClass(clazz, alias));
		}

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	@Override
	public DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		clazz = getImplClass(clazz, classLoader);

		if (alias != null) {
			return new DynamicQueryImpl(
				DetachedCriteria.forClass(clazz, alias));
		}

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	protected Class<?> getImplClass(Class<?> clazz, ClassLoader classLoader) {
		ImplementationClassName implementationClassName = clazz.getAnnotation(
			ImplementationClassName.class);

		if (implementationClassName == null) {
			String className = clazz.getName();

			if (!className.endsWith("Impl")) {
				_log.error("Unable find model for " + clazz);
			}

			return clazz;
		}

		Class<?> implClass = clazz;

		String implClassName = implementationClassName.value();

		try {
			implClass = getImplClass(implClassName, classLoader);
		}
		catch (Exception e1) {
			if (classLoader != _portalClassLoader) {
				try {
					implClass = getImplClass(implClassName, _portalClassLoader);
				}
				catch (Exception e2) {
					_log.error("Unable find model " + implClassName, e2);
				}
			}
			else {
				_log.error("Unable find model " + implClassName, e1);
			}
		}

		return implClass;
	}

	protected Class<?> getImplClass(
			String implClassName, ClassLoader classLoader)
		throws ClassNotFoundException {

		return classLoader.loadClass(implClassName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicQueryFactoryImpl.class);

	private final ClassLoader _portalClassLoader =
		DynamicQueryFactoryImpl.class.getClassLoader();

}