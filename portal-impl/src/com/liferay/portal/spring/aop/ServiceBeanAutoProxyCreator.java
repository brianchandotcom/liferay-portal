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

import com.liferay.portal.kernel.spring.aop.AopProxyFactory;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.target.SingletonTargetSource;

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
	protected Object createProxy(
		Class<?> beanClass, String beanName, Object[] specificInterceptors,
		TargetSource targetSource) {

		ProxyFactory proxyFactory = new ProxyFactory();

		proxyFactory.copyFrom(this);

		evaluateProxyInterfaces(beanClass, proxyFactory);

		proxyFactory.setTargetSource(targetSource);

		customizeProxyFactory(proxyFactory);

		proxyFactory.setFrozen(isFrozen());

		return proxyFactory.getProxy(getProxyClassLoader());
	}

	@Override
	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
		proxyFactory.setAopProxyFactory(
			new org.springframework.aop.framework.AopProxyFactory() {

				@Override
				public AopProxy createAopProxy(AdvisedSupport advisedSupport)
					throws AopConfigException {

					return new AopProxyAdapter(
						_aopProxyFactory.getAopProxy(
							new AdvisedSupportAdapter(advisedSupport)));
				}

			});
	}

	@Override
	protected Object wrapIfNecessary(
		Object bean, String beanName, Object cacheKey) {

		Class<?> beanClass = bean.getClass();

		if (_beanMatcher.match(beanClass, beanName)) {
			return createProxy(
				beanClass, beanName, new Object[0],
				new SingletonTargetSource(bean));
		}

		return bean;
	}

	private AopProxyFactory _aopProxyFactory;
	private BeanMatcher _beanMatcher;

}