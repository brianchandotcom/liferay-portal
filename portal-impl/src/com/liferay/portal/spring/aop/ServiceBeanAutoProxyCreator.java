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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.util.PreloadClassLoader;
import com.liferay.portal.kernel.util.PrototypeBean;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAutoProxyCreator
	extends AbstractAdvisorAutoProxyCreator implements PrototypeBean {

	public PrototypeBean create(Object... args) {
		if ((args.length != 1) || !(args[0] instanceof ClassLoader)) {
			throw new IllegalArgumentException();
		}

		ClassLoader classLoader = (ClassLoader)args[0];

		classLoader = new PreloadClassLoader(classLoader, _preloadClasses);

		ServiceBeanAutoProxyCreator serviceBeanAutoProxyCreator =
			new ServiceBeanAutoProxyCreator();

		serviceBeanAutoProxyCreator._methodInterceptor = _methodInterceptor;

		serviceBeanAutoProxyCreator.setProxyClassLoader(classLoader);

		return serviceBeanAutoProxyCreator;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		_methodInterceptor = methodInterceptor;
	}

	@Override
	protected void customizeProxyFactory(ProxyFactory proxyFactory) {
		proxyFactory.setAopProxyFactory(
			new AopProxyFactory() {

				public AopProxy createAopProxy(AdvisedSupport advisedSupport)
					throws AopConfigException {

					return new ServiceBeanAopProxy(
						advisedSupport, _methodInterceptor);
				}

			}
		);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Object[] getAdvicesAndAdvisorsForBean(
		Class beanClass, String beanName, TargetSource targetSource) {

		Object[] advices = DO_NOT_PROXY;

		if (beanName.endsWith(_SERVICE_SUFFIX)) {
			advices = super.getAdvicesAndAdvisorsForBean(
				beanClass, beanName, targetSource);

			if (advices == DO_NOT_PROXY) {
				advices = PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
			}
		}

		return advices;
	}

	private static final Map<String, Class<?>> _preloadClasses =
		new HashMap<String, Class<?>>();

	private static final String _SERVICE_SUFFIX = "Service";

	private MethodInterceptor _methodInterceptor;

	static {
		_preloadClasses.put(Advice.class.getName(), Advice.class);
		_preloadClasses.put(Advised.class.getName(), Advised.class);
		_preloadClasses.put(Advisor.class.getName(), Advisor.class);
		_preloadClasses.put(SpringProxy.class.getName(), SpringProxy.class);
		_preloadClasses.put(TargetSource.class.getName(), TargetSource.class);
	}

}