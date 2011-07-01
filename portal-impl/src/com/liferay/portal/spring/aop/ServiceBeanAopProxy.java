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

import com.liferay.portal.kernel.spring.aop.Skip;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
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

			List<Object> interceptors =
				_advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(
					method, targetClass);

			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				new ServiceBeanMethodInvocation(
					target, targetClass, method, arguments, interceptors);

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

	private final AdvisedSupport _advisedSupport;
	private final MethodInterceptor _methodInterceptor;

}