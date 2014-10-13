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

import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator
	extends AbstractAdvisorAutoProxyCreator {

	public ServiceBeanAutoProxyCreator() {
		_serviceBeanAopCacheManager = new ServiceBeanAopCacheManager();
	}

	public void afterPropertiesSet() {
		ServiceBeanAopCacheManagerUtil.registerServiceBeanAopCacheManager(
			_serviceBeanAopCacheManager);

		// Backwards compatibility

		if (_beanMatcher == null) {
			_beanMatcher = new ServiceBeanMatcher();
		}

		ListableBeanFactory listableBeanFactory =
			(ListableBeanFactory)getBeanFactory();

		Map<String, ChainableMethodAdviceInjector>
			chainableMethodAdviceInjectors =
				listableBeanFactory.getBeansOfType(
					ChainableMethodAdviceInjector.class);

		for (ChainableMethodAdviceInjector chainableMethodAdviceInjector :
				chainableMethodAdviceInjectors.values()) {

			chainableMethodAdviceInjector.inject();
		}

		if (!listableBeanFactory.containsBean(
				ChainableMethodAdviceInjectorCollector.BEAN_NAME)) {

			return;
		}

		ChainableMethodAdviceInjectorCollector
			chainableMethodAdviceInjectorCollector =
				(ChainableMethodAdviceInjectorCollector)
					listableBeanFactory.getBean(
						ChainableMethodAdviceInjectorCollector.BEAN_NAME);

		List<String> beanNames =
			chainableMethodAdviceInjectorCollector.getBeanNames();

		for (String beanName : beanNames) {
			Object bean = listableBeanFactory.getBean(beanName);

			if (bean instanceof ChainableMethodAdviceInjector) {
				ChainableMethodAdviceInjector chainableMethodAdviceInjector =
					(ChainableMethodAdviceInjector)bean;

				chainableMethodAdviceInjector.inject();
			}
		}
	}

	public void destroy() {
		ServiceBeanAopCacheManagerUtil.unregisterServiceBeanAopCacheManager(
			_serviceBeanAopCacheManager);
	}

	public void setBeanMatcher(BeanMatcher beanMatcher) {
		_beanMatcher = beanMatcher;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	@Override
	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
		proxyFactory.setAopProxyFactory(
			new AopProxyFactory() {

				@Override
				public AopProxy createAopProxy(AdvisedSupport advisedSupport)
					throws AopConfigException {

					return new ServiceBeanAopProxy(
						advisedSupport, _methodInterceptor,
						_serviceBeanAopCacheManager);
				}

			}
		);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Object[] getAdvicesAndAdvisorsForBean(
		Class beanClass, String beanName, TargetSource targetSource) {

		Object[] advices = DO_NOT_PROXY;

		if (_beanMatcher.match(beanClass, beanName)) {
			advices = super.getAdvicesAndAdvisorsForBean(
				beanClass, beanName, targetSource);

			if (advices == DO_NOT_PROXY) {
				advices = PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
			}
		}

		return advices;
	}

	private BeanMatcher _beanMatcher;
	private MethodInterceptor _methodInterceptor;
	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}