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
import com.liferay.portal.kernel.spring.aop.AopProxy;
import com.liferay.portal.kernel.spring.aop.AopProxyFactory;

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
	}

	@Override
	public Object postProcessBeforeInstantiation(
		Class<?> beanClass, String beanName) {

		return null;
	}

	public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
		_aopProxyFactory = aopProxyFactory;
	}

	public void setBeanMatcher(BeanMatcher beanMatcher) {
		_beanMatcher = beanMatcher;
	}

	@Override
	protected Object wrapIfNecessary(
		Object bean, String beanName, Object cacheKey) {

		Class<?> beanClass = bean.getClass();

		if (!_beanMatcher.match(beanClass, beanName)) {
			return bean;
		}

		AopProxy aopProxy = _aopProxyFactory.getAopProxy(
			new AdvisedSupportImpl(ReflectionUtil.getInterfaces(bean), bean));

		return aopProxy.getProxy(getProxyClassLoader());
	}

	private AopProxyFactory _aopProxyFactory;
	private BeanMatcher _beanMatcher;

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

		/**
		 * @deprecated As of Judson (7.1.x), with no direct replacement
		 */
		@Deprecated
		@Override
		public void setTarget(Object target, Class<?> targetClass) {
			setTarget(target);
		}

		private AdvisedSupportImpl(Class<?>[] interfaces, Object target) {
			_interfaces = interfaces;
			_target = target;
		}

		private final Class<?>[] _interfaces;
		private Object _target;

	}

}