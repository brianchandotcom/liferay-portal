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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.SpringProxy;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisorChainFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.ClassUtils;

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

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport, MethodInterceptor methodInterceptor,
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_advisedSupport = advisedSupport;
		_advisorChainFactory = _advisedSupport.getAdvisorChainFactory();

		Class<?>[] proxyInterfaces = _advisedSupport.getProxiedInterfaces();

		_mergeSpringMethodInterceptors = !ArrayUtil.contains(
			proxyInterfaces, SpringProxy.class);

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

			chainableMethodAdvice.setServiceBeanAopCacheManager(
				serviceBeanAopCacheManager);

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

		_serviceBeanAopCacheManager = serviceBeanAopCacheManager;
	}

	@Override
	public AdvisedSupport getAdvisedSupport() {
		return _advisedSupport;
	}

	@Override
	public Object getProxy() {
		return getProxy(ClassUtils.getDefaultClassLoader());
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		Class<?>[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(
			_advisedSupport);

		InvocationHandler invocationHandler = _pacl.getInvocationHandler(
			this, _advisedSupport);

		return ProxyUtil.newProxyInstance(
			classLoader, proxiedInterfaces, invocationHandler);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		TargetSource targetSource = _advisedSupport.getTargetSource();

		Object target = null;

		try {
			target = targetSource.getTarget();

			ServiceBeanMethodInvocation serviceBeanMethodInvocation =
				new ServiceBeanMethodInvocation(
					target, targetSource.getTargetClass(), method, arguments);

			_setMethodInterceptors(serviceBeanMethodInvocation);

			return serviceBeanMethodInvocation.proceed();
		}
		finally {
			if ((target != null) && !targetSource.isStatic()) {
				targetSource.releaseTarget(target);
			}
		}
	}

	public interface PACL {

		public InvocationHandler getInvocationHandler(
			InvocationHandler invocationHandler, AdvisedSupport advisedSupport);

	}

	private List<MethodInterceptor> _getMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		List<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>(_fullMethodInterceptors);

		if (!_mergeSpringMethodInterceptors) {
			return methodInterceptors;
		}

		List<Object> list =
			_advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
				_advisedSupport, serviceBeanMethodInvocation.getMethod(),
				serviceBeanMethodInvocation.getTargetClass());

		Iterator<Object> itr = list.iterator();

		while (itr.hasNext()) {
			Object obj = itr.next();

			if (obj instanceof MethodInterceptor) {
				continue;
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Skipping unsupported interceptor type " + obj.getClass());
			}

			itr.remove();
		}

		if (list.isEmpty()) {
			return methodInterceptors;
		}

		for (Object object : list) {
			methodInterceptors.add((MethodInterceptor)object);
		}

		return methodInterceptors;
	}

	private void _setMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		MethodInterceptorsBag methodInterceptorsBag =
			_serviceBeanAopCacheManager.getMethodInterceptorsBag(
				serviceBeanMethodInvocation);

		if (methodInterceptorsBag == null) {
			List<MethodInterceptor> methodInterceptors = _getMethodInterceptors(
				serviceBeanMethodInvocation);

			methodInterceptorsBag = new MethodInterceptorsBag(
				_classLevelMethodInterceptors, methodInterceptors);

			_serviceBeanAopCacheManager.putMethodInterceptorsBag(
				serviceBeanMethodInvocation.toCacheKeyModel(),
				methodInterceptorsBag);
		}

		serviceBeanMethodInvocation.setMethodInterceptors(
			methodInterceptorsBag.getMergedMethodInterceptors());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceBeanAopProxy.class);

	private static final PACL _pacl = new NoPACL();

	private final AdvisedSupport _advisedSupport;
	private final AdvisorChainFactory _advisorChainFactory;
	private final List<MethodInterceptor> _classLevelMethodInterceptors;
	private final List<MethodInterceptor> _fullMethodInterceptors;
	private final boolean _mergeSpringMethodInterceptors;
	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

	private static class NoPACL implements PACL {

		@Override
		public InvocationHandler getInvocationHandler(
			InvocationHandler invocationHandler,
			AdvisedSupport advisedSupport) {

			return invocationHandler;
		}

	}

}