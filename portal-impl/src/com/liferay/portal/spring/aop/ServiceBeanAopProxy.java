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

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

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

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport, MethodInterceptor methodInterceptor,
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_advisedSupport = advisedSupport;

		ArrayList<MethodInterceptor> classLevelMethodInterceptors =
			new ArrayList<>();
		ArrayList<MethodInterceptor> fullMethodInterceptors = new ArrayList<>();

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

		_setMethodInterceptors(serviceBeanMethodInvocation);

		return serviceBeanMethodInvocation.proceed();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public InvocationHandler getInvocationHandler(
			InvocationHandler invocationHandler, AdvisedSupport advisedSupport);

	}

	private void _setMethodInterceptors(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation) {

		MethodInterceptorsBag methodInterceptorsBag =
			_serviceBeanAopCacheManager.getMethodInterceptorsBag(
				serviceBeanMethodInvocation);

		if (methodInterceptorsBag == null) {
			List<MethodInterceptor> methodInterceptors = new ArrayList<>(
				_fullMethodInterceptors);

			methodInterceptorsBag = new MethodInterceptorsBag(
				_classLevelMethodInterceptors, methodInterceptors);

			_serviceBeanAopCacheManager.putMethodInterceptorsBag(
				serviceBeanMethodInvocation, methodInterceptorsBag);
		}

		serviceBeanMethodInvocation.setMethodInterceptors(
			methodInterceptorsBag.getMergedMethodInterceptors());
	}

	private final AdvisedSupport _advisedSupport;
	private final List<MethodInterceptor> _classLevelMethodInterceptors;
	private final List<MethodInterceptor> _fullMethodInterceptors;
	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}