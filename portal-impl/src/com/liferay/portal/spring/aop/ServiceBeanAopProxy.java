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
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopProxy
	implements AdvisedSupportProxy, AopProxy, InvocationHandler {

	public static AdvisedSupport getAdvisedSupport(Object proxy)
		throws Exception {

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			proxy);

		if (invocationHandler instanceof AdvisedSupportProxy) {
			AdvisedSupportProxy advisableSupportProxy =
				(AdvisedSupportProxy)invocationHandler;

			return advisableSupportProxy.getAdvisedSupport();
		}

		return null;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #ServiceBeanAopProxy(
	 *             AdvisedSupport, ServiceBeanAopCacheManager)}
	 */
	@Deprecated
	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport, MethodInterceptor methodInterceptor,
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		this(advisedSupport, new ServiceBeanAopCacheManager(methodInterceptor));
	}

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport,
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_advisedSupport = advisedSupport;
		_serviceBeanAopCacheManager = serviceBeanAopCacheManager;
	}

	@Override
	public AdvisedSupport getAdvisedSupport() {
		return _advisedSupport;
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		return ProxyUtil.newProxyInstance(
			classLoader, _advisedSupport.getProxiedInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(
				_advisedSupport.getTarget(), method, arguments);

		serviceBeanMethodInvocation.setMethodInterceptors(
			_serviceBeanAopCacheManager.getMethodInterceptors(
				serviceBeanMethodInvocation));

		return serviceBeanMethodInvocation.proceed();
	}

	public void setServiceBeanAopCacheManager(
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_serviceBeanAopCacheManager = serviceBeanAopCacheManager;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public InvocationHandler getInvocationHandler(
			InvocationHandler invocationHandler, AdvisedSupport advisedSupport);

	}

	private final AdvisedSupport _advisedSupport;
	private volatile ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}