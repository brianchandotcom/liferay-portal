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
import com.liferay.portal.kernel.spring.aop.AdvisedSupport;
import com.liferay.portal.kernel.util.ProxyUtil;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator
	extends AbstractAdvisorAutoProxyCreator {

	public void afterPropertiesSet() {

		// Backwards compatibility

		if (_beanMatcher == null) {
			_beanMatcher = new ServiceBeanMatcher();
		}

		_serviceBeanAopCacheManager = new ServiceBeanAopCacheManager(
			_methodInterceptor);
	}

	@Override
	public Object postProcessBeforeInstantiation(
		Class<?> beanClass, String beanName) {

		return null;
	}

	public void setBeanMatcher(BeanMatcher beanMatcher) {
		_beanMatcher = beanMatcher;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	@Override
	protected Object wrapIfNecessary(
		Object bean, String beanName, Object cacheKey) {

		Class<?> beanClass = bean.getClass();

		if (!_beanMatcher.match(beanClass, beanName)) {
			return bean;
		}

		Class<?>[] interfaces = ReflectionUtil.getInterfaces(bean);

		ServiceBeanAopProxy serviceBeanAopProxy = new ServiceBeanAopProxy(
			new AdvisedSupportImpl(interfaces, bean),
			_serviceBeanAopCacheManager);

		return ProxyUtil.newProxyInstance(
			getProxyClassLoader(), interfaces, serviceBeanAopProxy);
	}

	private BeanMatcher _beanMatcher;
	private MethodInterceptor _methodInterceptor;
	private ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

	private static class AdvisedSupportImpl implements AdvisedSupport {

		@Override
		public Class<?>[] getProxiedInterfaces() {
			return _interfaces;
		}

		@Override
		public Object getTarget() {
			return _target;
		}

		@Override
		public void setTarget(Object target) {
			_target = target;
		}

		private AdvisedSupportImpl(Class<?>[] interfaces, Object target) {
			_interfaces = interfaces;
			_target = target;
		}

		private final Class<?>[] _interfaces;
		private Object _target;

	}

}