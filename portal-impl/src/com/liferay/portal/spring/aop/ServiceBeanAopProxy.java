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
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

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

	public static void clearMethodInterceptorCache() {
		_methodInterceptorBags.clear();
	}

	public static void removeMethodInterceptor(
		MethodInvocation methodInvocation,
		MethodInterceptor methodInterceptor) {

		if (!(methodInvocation instanceof ServiceBeanMethodInvocation)) {
			return;
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)methodInvocation;

		MethodInterceptorsBag methodInterceptorsBag =
			_methodInterceptorBags.get(serviceBeanMethodInvocation);

		if (methodInterceptorsBag == null) {
			return;
		}

		ArrayList<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>(
				methodInterceptorsBag._mergedMethodInterceptors);

		methodInterceptors.remove(methodInterceptor);

		MethodInterceptorsBag newMethodInterceptorsBag = null;

		if (methodInterceptors.equals(
				methodInterceptorsBag._classLevelMethodInterceptors)) {

			newMethodInterceptorsBag = new MethodInterceptorsBag(
				methodInterceptorsBag._classLevelMethodInterceptors,
				methodInterceptorsBag._classLevelMethodInterceptors);
		}
		else {
			methodInterceptors.trimToSize();

			newMethodInterceptorsBag = new MethodInterceptorsBag(
				methodInterceptorsBag._classLevelMethodInterceptors,
				methodInterceptors);
		}

		_methodInterceptorBags.put(
			serviceBeanMethodInvocation.toCacheKeyModel(),
			newMethodInterceptorsBag);
	}

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport, MethodInterceptor methodInterceptor) {

		_advisedSupport = advisedSupport;
		_advisorChainFactory = _advisedSupport.getAdvisorChainFactory();

		ArrayList<MethodInterceptor> classLevelMethodInterceptors =
			new ArrayList<MethodInterceptor>();
		ArrayList<MethodInterceptor> fullMethodInterceptors =
			new ArrayList<MethodInterceptor>();

		while (true) {
			if (!(methodInterceptor instanceof ChainableMethodAdvice)) {
				classLevelMethodInterceptors.add(methodInterceptor);
				fullMethodInterceptors.add(methodInterceptor);

				break;
			}

			ChainableMethodAdvice chainableMethodAdvice =
				(ChainableMethodAdvice)methodInterceptor;

			if (methodInterceptor instanceof AnnotationChainableMethodAdvice) {
				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)methodInterceptor;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				Target target = annotationClass.getAnnotation(Target.class);

				if (target == null) {
					classLevelMethodInterceptors.add(methodInterceptor);
				}
				else {
					for (ElementType elementType : target.value()) {
						if (elementType == ElementType.TYPE) {
							classLevelMethodInterceptors.add(methodInterceptor);

							break;
						}
					}
				}
			}
			else {
				classLevelMethodInterceptors.add(methodInterceptor);
			}

			fullMethodInterceptors.add(methodInterceptor);

			methodInterceptor = chainableMethodAdvice.nextMethodInterceptor;
		}

		classLevelMethodInterceptors.trimToSize();

		_classLevelMethodInterceptors = classLevelMethodInterceptors;
		_fullMethodInterceptors = fullMethodInterceptors;

		AnnotationChainableMethodAdvice.registerAnnotationClass(Skip.class);
	}

	public Object getProxy() {
		return getProxy(ClassUtils.getDefaultClassLoader());
	}

	public Object getProxy(ClassLoader classLoader) {
		Class<?>[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(
			_advisedSupport);

		return ProxyUtil.newProxyInstance(classLoader, proxiedInterfaces, this);
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

			Skip skip = ServiceMethodAnnotationCache.get(
				serviceBeanMethodInvocation, Skip.class, null);

			if (skip != null) {
				serviceBeanMethodInvocation.setMethodInterceptors(
					Collections.<MethodInterceptor>emptyList());
			}
			else {
				_setMethodInterceptors(serviceBeanMethodInvocation);
			}

			return serviceBeanMethodInvocation.proceed();
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

		List<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>(_fullMethodInterceptors);

		if (!list.isEmpty()) {
			for (Object object : list) {
				methodInterceptors.add((MethodInterceptor)object);
			}
		}

		return methodInterceptors;
	}

	private void _setMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		MethodInterceptorsBag methodInterceptorsBag =
			_methodInterceptorBags.get(serviceBeanMethodInvocation);

		if (methodInterceptorsBag == null) {
			List<MethodInterceptor> methodInterceptors = _getMethodInterceptors(
				serviceBeanMethodInvocation);

			methodInterceptorsBag = new MethodInterceptorsBag(
				_classLevelMethodInterceptors, methodInterceptors);

			_methodInterceptorBags.put(
				serviceBeanMethodInvocation.toCacheKeyModel(),
				methodInterceptorsBag);
		}

		serviceBeanMethodInvocation.setMethodInterceptors(
			methodInterceptorsBag._mergedMethodInterceptors);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServiceBeanAopProxy.class);

	private static Map <ServiceBeanMethodInvocation, MethodInterceptorsBag>
		_methodInterceptorBags = new ConcurrentHashMap
			<ServiceBeanMethodInvocation, MethodInterceptorsBag>();

	private AdvisedSupport _advisedSupport;
	private AdvisorChainFactory _advisorChainFactory;
	private final List<MethodInterceptor> _classLevelMethodInterceptors;
	private final List<MethodInterceptor> _fullMethodInterceptors;

	private static class MethodInterceptorsBag {

		public MethodInterceptorsBag(
			List<MethodInterceptor> classLevelMethodInterceptors,
			List<MethodInterceptor> mergedMethodInterceptors) {

			_classLevelMethodInterceptors = classLevelMethodInterceptors;
			_mergedMethodInterceptors = mergedMethodInterceptors;
		}

		private List<MethodInterceptor> _classLevelMethodInterceptors;
		private List<MethodInterceptor> _mergedMethodInterceptors;

	}

}