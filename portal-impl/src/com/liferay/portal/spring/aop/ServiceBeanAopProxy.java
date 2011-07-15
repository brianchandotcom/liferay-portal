/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.aop.Skip;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisorChainFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.ClassUtils;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopProxy implements AopProxy, InvocationHandler {

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport, MethodInterceptor methodInterceptor) {

		_advisedSupport = advisedSupport;
		_advisorChainFactory = _advisedSupport.getAdvisorChainFactory();
		_methodInterceptor = methodInterceptor;

		AnnotationChainableMethodAdvice.registerAnnotationType(Skip.class);
	}

	public Object getProxy() {
		return getProxy(ClassUtils.getDefaultClassLoader());
	}

	public Object getProxy(ClassLoader classLoader) {
		Class<?>[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(
			_advisedSupport);

		return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		TargetSource targetSource = _advisedSupport.getTargetSource();

		Object target = null;

		try {
			Class<?> targetClass = null;

			target = targetSource.getTarget();

			if (target != null) {
				targetClass = target.getClass();
			}

			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				new ServiceBeanMethodInvocation(
					target, targetClass, method, arguments);

			_setMethodInterceptors(serviceBeanMethodInvocation);

			Skip skip = ServiceMethodAnnotationCache.get(
				serviceBeanMethodInvocation, Skip.class, null);

			if (skip == null) {
				return _methodInterceptor.invoke(serviceBeanMethodInvocation);
			}
			else {
				return serviceBeanMethodInvocation.proceed();
			}
		}
		finally {
			if ((target != null) && !targetSource.isStatic()) {
				targetSource.releaseTarget(target);
			}
		}
	}

	private List<MethodInterceptor> _getMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		List<Object> list =
			_advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
				_advisedSupport, serviceBeanMethodInvocation.getMethod(),
				serviceBeanMethodInvocation.getTargetClass());

		Iterator<Object> itr = list.iterator();

		while (itr.hasNext()) {
			Object obj = itr.next();

			if (!(obj instanceof MethodInterceptor)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping unsupported interceptor type " +
							obj.getClass());
				}

				itr.remove();
			}
		}

		List<MethodInterceptor> methodInterceptors = null;

		if (list.isEmpty()) {
			methodInterceptors = Collections.emptyList();
		}
		else {
			methodInterceptors = new ArrayList<MethodInterceptor>(list.size());

			for (Object obj : list) {
				methodInterceptors.add((MethodInterceptor)obj);
			}
		}

		return methodInterceptors;
	}

	private void _setMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		List<MethodInterceptor> methodInterceptors = _methodInterceptors.get(
			serviceBeanMethodInvocation);

		if (methodInterceptors == null) {
			methodInterceptors = _getMethodInterceptors(
				serviceBeanMethodInvocation);

			_methodInterceptors.put(
				serviceBeanMethodInvocation.toCacheKeyModel(),
				methodInterceptors);
		}

		serviceBeanMethodInvocation.setMethodInterceptors(methodInterceptors);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServiceBeanAopProxy.class);

	private static Map <ServiceBeanMethodInvocation, List<MethodInterceptor>>
		_methodInterceptors = new ConcurrentHashMap
			<ServiceBeanMethodInvocation, List<MethodInterceptor>>();

	private AdvisedSupport _advisedSupport;
	private AdvisorChainFactory _advisorChainFactory;
	private MethodInterceptor _methodInterceptor;

}