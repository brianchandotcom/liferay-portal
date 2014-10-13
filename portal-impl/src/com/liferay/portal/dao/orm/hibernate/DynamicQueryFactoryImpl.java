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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.lang.DoPrivilegedUtil;
import com.liferay.portal.util.ClassLoaderUtil;

import java.security.PrivilegedAction;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryImpl implements DynamicQueryFactory {

	@Override
	public DynamicQuery forClass(Class<?> clazz) {
		clazz = getImplClass(clazz, null);

		return DoPrivilegedUtil.wrap(
			new DynamicQueryPrivilegedAction(clazz, null));
	}

	@Override
	public DynamicQuery forClass(Class<?> clazz, ClassLoader classLoader) {
		clazz = getImplClass(clazz, classLoader);

		return DoPrivilegedUtil.wrap(
			new DynamicQueryPrivilegedAction(clazz, null));
	}

	@Override
	public DynamicQuery forClass(Class<?> clazz, String alias) {
		clazz = getImplClass(clazz, null);

		return DoPrivilegedUtil.wrap(
			new DynamicQueryPrivilegedAction(clazz, alias));
	}

	@Override
	public DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		clazz = getImplClass(clazz, classLoader);

		return DoPrivilegedUtil.wrap(
			new DynamicQueryPrivilegedAction(clazz, alias));
	}

	protected Class<?> getImplClass(Class<?> clazz, ClassLoader classLoader) {
		Class<?> implClass = clazz;

		String className = clazz.getName();

		if (!className.endsWith("Impl")) {
			if (classLoader == null) {
				classLoader = ClassLoaderUtil.getContextClassLoader();
			}

			Package pkg = clazz.getPackage();

			String implClassName =
				pkg.getName() + ".impl." + clazz.getSimpleName() + "Impl";

			try {
				implClass = getImplClass(implClassName, classLoader);
			}
			catch (Exception e1) {
				if (classLoader != _portalClassLoader) {
					try {
						implClass = getImplClass(
							implClassName, _portalClassLoader);
					}
					catch (Exception e2) {
						_log.error("Unable find model " + implClassName, e2);
					}
				}
				else {
					_log.error("Unable find model " + implClassName, e1);
				}
			}
		}

		PortalRuntimePermission.checkDynamicQuery(implClass);

		return implClass;
	}

	protected Class<?> getImplClass(
			String implClassName, ClassLoader classLoader)
		throws ClassNotFoundException {

		Map<String, Class<?>> classes = _classes.get(classLoader);

		if (classes == null) {
			classes = new HashMap<String, Class<?>>();

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

	private final Map<ClassLoader, Map<String, Class<?>>> _classes =
		new HashMap<ClassLoader, Map<String, Class<?>>>();
	private final ClassLoader _portalClassLoader =
		DynamicQueryFactoryImpl.class.getClassLoader();

	private class DynamicQueryPrivilegedAction
		implements PrivilegedAction<DynamicQuery> {

		public DynamicQueryPrivilegedAction(Class<?> clazz, String alias) {
			_clazz = clazz;
			_alias = alias;
		}

		@Override
		public DynamicQuery run() {
			if (_alias != null) {
				return new DynamicQueryImpl(
					DetachedCriteria.forClass(_clazz, _alias));
			}

			return new DynamicQueryImpl(DetachedCriteria.forClass(_clazz));
		}

		private final String _alias;
		private final Class<?> _clazz;

	}

}