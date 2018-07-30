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

import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryImpl implements DynamicQueryFactory {

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

		Map<String, Class<?>> classes = _classes.get(classLoader);

		if (classes == null) {
			classes = new HashMap<>();

			_classes.put(classLoader, classes);
		}

		Class<?> clazz = classes.get(implClassName);

		if (clazz == null) {
			clazz = classLoader.loadClass(implClassName);

			classes.put(implClassName, clazz);
		}

		return clazz;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DynamicQueryFactoryImpl.class);

	private static final
		ConcurrentMap<ClassLoader, Map<String, Class<?>>> _classes =
			new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

	private final ClassLoader _portalClassLoader =
		DynamicQueryFactoryImpl.class.getClassLoader();

}