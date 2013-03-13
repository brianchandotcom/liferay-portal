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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.util.ClassLoaderUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Raymond Augé
 */
public class DoPrivilegedFactory implements BeanPostProcessor {

	public DoPrivilegedFactory() {
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		if (SecurityManagerUtil.isPACLDisabled()) {
			return bean;
		}

		Class<?> beanClass = bean.getClass();

		if (!isDoPrivileged(beanClass)) {
			return bean;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Wrapping calls to bean " + beanName + " of type " +
					bean.getClass() + " with access controller checking");
		}

		return wrap(bean);
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public static <T> T wrap(T bean) {
		return AccessController.doPrivileged(new WrapAction<T>(bean));
	}

	private static Class<?>[] getInterfaces(Object object) {
		List<Class<?>> interfaceClasses = new UniqueList<Class<?>>();

		Class<?> clazz = object.getClass();

		getInterfaces(interfaceClasses, clazz);

		Class<?> superClass = clazz.getSuperclass();

		while (superClass != null) {
			getInterfaces(interfaceClasses, superClass);

			superClass = superClass.getSuperclass();
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
	}

	private static void getInterfaces(
		List<Class<?>> interfaceClasses, Class<?> clazz) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			interfaceClasses.add(interfaceClass);
		}
	}

	private boolean isDoPrivileged(Class<?> beanClass) {
		DoPrivileged annotation = beanClass.getAnnotation(DoPrivileged.class);

		while ((annotation == null) &&
			   (beanClass = beanClass.getSuperclass()) != null) {

			annotation = beanClass.getAnnotation(DoPrivileged.class);
		}

		if (annotation != null) {
			return true;
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(DoPrivilegedFactory.class);

	public static class WrapAction <T> implements PrivilegedAction<T> {

		public WrapAction(T bean) {
			_bean = bean;
		}

		@SuppressWarnings("unchecked")
		public T run() {
			Class<?> clazz = _bean.getClass();
			String packageName = clazz.getPackage().getName();

			if (clazz.isPrimitive() || packageName.startsWith("java.")) {
				return _bean;
			}

			Class<?>[] interfaces = getInterfaces(_bean);

			if (interfaces.length <= 0) {
				return _bean;
			}

			interfaces = ArrayUtil.append(interfaces, DoPrivilegedBean.class);

			try {
				return (T)ProxyUtil.newProxyInstance(
					ClassLoaderUtil.getPortalClassLoader(), interfaces,
					new DoPrivilegedHandler(_bean));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			return _bean;
		}

		private T _bean;

	}

}