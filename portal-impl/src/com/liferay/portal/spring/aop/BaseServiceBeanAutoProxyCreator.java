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

package com.liferay.portal.spring.aop;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Constructor;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseServiceBeanAutoProxyCreator
	implements SmartInstantiationAwareBeanPostProcessor {

	public BaseServiceBeanAutoProxyCreator(
		BeanMatcher beanMatcher, ClassLoader classLoader) {

		_beanMatcher = beanMatcher;
		_classLoader = classLoader;
	}

	@Override
	public Constructor<?>[] determineCandidateConstructors(
		Class<?> beanClass, String beanName) {

		return null;
	}

	@Override
	public Object getEarlyBeanReference(Object bean, String beanName) {
		Class<?> beanClass = bean.getClass();

		if (!_beanMatcher.match(beanClass, beanName)) {
			return bean;
		}

		_earlyProxyReferences.add(new CacheKey(beanClass, beanName));

		return _createProxy(bean);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		Class<?> beanClass = bean.getClass();

		if (!_beanMatcher.match(beanClass, beanName) ||
			_earlyProxyReferences.contains(new CacheKey(beanClass, beanName))) {

			return bean;
		}

		return _createProxy(bean);
	}

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) {
		return true;
	}

	@Override
	public Object postProcessBeforeInitialization(
		Object bean, String beanName) {

		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(
		Class<?> beanClass, String beanName) {

		return null;
	}

	@Override
	public PropertyValues postProcessPropertyValues(
		PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors,
		Object bean, String beanName) {

		return propertyValues;
	}

	@Override
	public Class<?> predictBeanType(Class<?> beanClass, String beanName) {
		return null;
	}

	protected abstract AopInvocationHandler createAopInvocationHandler(
		Object bean);

	private Object _createProxy(Object bean) {
		AopInvocationHandler aopInvocationHandler = createAopInvocationHandler(
			bean);

		return ProxyUtil.newProxyInstance(
			_classLoader, ReflectionUtil.getInterfaces(bean),
			aopInvocationHandler);
	}

	private final BeanMatcher _beanMatcher;
	private final ClassLoader _classLoader;
	private final Set<CacheKey> _earlyProxyReferences =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	private static class CacheKey {

		@Override
		public boolean equals(Object obj) {
			CacheKey cacheKey = (CacheKey)obj;

			if (_clazz.equals(cacheKey._clazz) &&
				Objects.equals(_beanName, cacheKey._beanName)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _clazz);

			return HashUtil.hash(hash, _beanName);
		}

		private CacheKey(Class<?> clazz, String beanName) {
			_clazz = clazz;
			_beanName = beanName;
		}

		private final String _beanName;
		private final Class<?> _clazz;

	}

}