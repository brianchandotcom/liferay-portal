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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.ClassLoaderUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedFactory
	extends InstantiationAwareBeanPostProcessorAdapter {

	public static boolean isEarlyBeanReference(String beanName) {
		return _earlyBeanReferenceNames.contains(beanName);
	}

	public static <T> T wrap(T bean) {
		Class<?> clazz = bean.getClass();

		if (clazz.isPrimitive()) {
			return bean;
		}

		Package pkg = clazz.getPackage();

		if (pkg != null) {
			String packageName = pkg.getName();

			if (packageName.startsWith("java.")) {
				return bean;
			}
		}

		Class<?>[] interfaces = ReflectionUtil.getInterfaces(bean);

		if (interfaces.length <= 0) {
			return bean;
		}

		return AccessController.doPrivileged(
			new BeanPrivilegedAction<T>(bean, interfaces));
	}

	@Override
	public Object getEarlyBeanReference(Object bean, String beanName)
		throws BeansException {

		if (_isWrap(bean, beanName)) {
			_earlyBeanReferenceNames.add(beanName);
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		if (!SecurityManagerUtil.ENABLED) {
			return bean;
		}

		if (!_isWrap(bean, beanName)) {
			return bean;
		}

		if (isEarlyBeanReference(beanName)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Postpone wrapping early reference of " + beanName);
			}

			return bean;
		}

		if (_log.isDebugEnabled()) {
			Class<?> clazz = bean.getClass();

			_log.debug(
				"Wrapping calls to bean " + beanName + " of type " +
					clazz + " with access controller checking");
		}

		return wrap(bean);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	private boolean _isDoPrivileged(Class<?> beanClass) {
		DoPrivileged doPrivileged = beanClass.getAnnotation(DoPrivileged.class);

		while ((doPrivileged == null) &&
			   (beanClass = beanClass.getSuperclass()) != null) {

			doPrivileged = beanClass.getAnnotation(DoPrivileged.class);
		}

		if (doPrivileged != null) {
			return true;
		}

		return false;
	}

	private boolean _isFinderOrPersistence(String beanName) {
		if (beanName.endsWith(_BEAN_NAME_SUFFIX_FINDER) ||
			beanName.endsWith(_BEAN_NAME_SUFFIX_PERSISTENCE)) {

			return true;
		}

		return false;
	}

	private boolean _isWrap(Object bean, String beanName) {
		Class<?> clazz = bean.getClass();

		if (_isDoPrivileged(clazz) || _isFinderOrPersistence(beanName)) {
			return true;
		}

		return false;
	}

	private static final String _BEAN_NAME_SUFFIX_FINDER = "Finder";

	private static final String _BEAN_NAME_SUFFIX_PERSISTENCE = "Persistence";

	private static final Log _log = LogFactoryUtil.getLog(
		DoPrivilegedFactory.class);

	private static final Set<String> _earlyBeanReferenceNames =
		new HashSet<String>();

	private static class BeanPrivilegedAction <T>
		implements PrivilegedAction<T> {

		public BeanPrivilegedAction(T bean, Class<?>[] interfaces) {
			_bean = bean;
			_interfaces = ArrayUtil.append(interfaces, DoPrivilegedBean.class);
		}

		@Override
		public T run() {
			try {
				return (T)ProxyUtil.newProxyInstance(
					ClassLoaderUtil.getPortalClassLoader(), _interfaces,
					new DoPrivilegedHandler(_bean));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			return _bean;
		}

		private final T _bean;
		private final Class<?>[] _interfaces;

	}

}