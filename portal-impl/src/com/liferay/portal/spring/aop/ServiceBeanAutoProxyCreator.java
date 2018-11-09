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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.beans.PropertyDescriptor;

import java.lang.reflect.Constructor;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator
	implements BeanClassLoaderAware, SmartInstantiationAwareBeanPostProcessor {

	public void afterPropertiesSet() {

		// Backwards compatibility

		if (_beanMatcher == null) {
			_beanMatcher = new ServiceBeanMatcher();
		}

		_serviceBeanAopCacheManager = new ServiceBeanAopCacheManager(
			_methodInterceptor);
	}

	@Override
	public Constructor<?>[] determineCandidateConstructors(
		Class<?> beanClass, String beanName) {

		return null;
	}

	@Override
	public Object getEarlyBeanReference(Object bean, String beanName) {
		_earlyProxyReferences.add(_getCacheKey(bean.getClass(), beanName));

		return _wrapIfNecessary(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		Object cacheKey = _getCacheKey(bean.getClass(), beanName);

		if (!_earlyProxyReferences.contains(cacheKey)) {
			return _wrapIfNecessary(bean, beanName);
		}

		return bean;
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

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public void setBeanMatcher(BeanMatcher beanMatcher) {
		_beanMatcher = beanMatcher;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	private Object _getCacheKey(Class<?> beanClass, String beanName) {
		return beanClass.getName() + "_" + beanName;
	}

	private Object _wrapIfNecessary(Object bean, String beanName) {
		Class<?> beanClass = bean.getClass();

		if (!_beanMatcher.match(beanClass, beanName)) {
			return bean;
		}

		return ProxyUtil.newProxyInstance(
			_classLoader, ReflectionUtil.getInterfaces(bean),
			new ServiceBeanAopInvocationHandler(
				bean, _serviceBeanAopCacheManager));
	}

	private BeanMatcher _beanMatcher;
	private ClassLoader _classLoader;
	private final Set<Object> _earlyProxyReferences = Collections.newSetFromMap(
		new ConcurrentHashMap<>(16));
	private MethodInterceptor _methodInterceptor;
	private ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}