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

import com.liferay.portal.kernel.spring.aop.AdvisedSupport;
import com.liferay.portal.kernel.spring.aop.AopProxy;
import com.liferay.portal.kernel.spring.aop.AopProxyFactory;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @author Tina Tian
 */
public class AopProxyFactoryImpl implements AopProxyFactory, BeanFactoryAware {

	public AopProxyFactoryImpl() {
		_serviceBeanAopCacheManager = new ServiceBeanAopCacheManager();
	}

	public void afterPropertiesSet() {
		ServiceBeanAopCacheManagerUtil.registerServiceBeanAopCacheManager(
			_serviceBeanAopCacheManager);

		ListableBeanFactory listableBeanFactory =
			(ListableBeanFactory)_beanFactory;

		Map<String, ChainableMethodAdviceInjector>
			chainableMethodAdviceInjectors = listableBeanFactory.getBeansOfType(
				ChainableMethodAdviceInjector.class);

		for (ChainableMethodAdviceInjector chainableMethodAdviceInjector :
				chainableMethodAdviceInjectors.values()) {

			chainableMethodAdviceInjector.inject();
		}
	}

	public void destroy() {
		ServiceBeanAopCacheManagerUtil.unregisterServiceBeanAopCacheManager(
			_serviceBeanAopCacheManager);
	}

	@Override
	public AopProxy getAopProxy(AdvisedSupport advisedSupport) {
		return new ServiceBeanAopProxy(
			advisedSupport, _methodInterceptor, _serviceBeanAopCacheManager);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	private BeanFactory _beanFactory;
	private MethodInterceptor _methodInterceptor;
	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}