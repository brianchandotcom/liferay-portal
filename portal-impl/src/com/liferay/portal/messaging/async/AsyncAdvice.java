/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.messaging.async;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import com.liferay.portal.util.ClassLoaderUtil;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class AsyncAdvice extends AnnotationChainableMethodAdvice<Async> {

	@Override
	public Object before(final MethodInvocation methodInvocation)
		throws Throwable {

		Async async = findAnnotation(methodInvocation);

		if (async == _nullAsync) {
			return null;
		}

		Method method = methodInvocation.getMethod();

		if (method.getReturnType() != void.class) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Async annotation on method " + method.getName() +
						" does not return void");
			}

			return null;
		}

		String destinationName = null;

		if ((_destinationNames != null) && !_destinationNames.isEmpty()) {
			Object thisObject = methodInvocation.getThis();

			destinationName = _destinationNames.get(thisObject.getClass());
		}

		if (destinationName == null) {
			destinationName = _defaultDestinationName;
		}

		MessageBusUtil.sendMessage(
			destinationName,
			new Runnable() {

				@Override
				public void run() {
					try {
						methodInvocation.proceed();
					}
					catch (Throwable t) {
						throw new RuntimeException(t);
					}
				}

				@Override
				public String toString() {
					return methodInvocation.toString();
				}

			});

		return nullResult;
	}

	public String getDefaultDestinationName() {
		return _defaultDestinationName;
	}

	@Override
	public Async getNullAnnotation() {
		return _nullAsync;
	}

	public void setDefaultDestinationName(String defaultDestinationName) {
		_defaultDestinationName = defaultDestinationName;
	}

	public void setDestinationNames(Map<Class<?>, String> destinationNames) {
		_destinationNames = destinationNames;
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String servletContextName,
			String beanIdentifier)
		throws Exception {

		if (Validator.isNull(servletContextName)) {
			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortalBeanLocatorUtil.locate(beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				servletContextName);

			ClassLoaderUtil.setContextClassLoader(classLoader);

			if (Validator.isNull(beanIdentifier)) {
				return methodHandler.invoke(true);
			}
			else {
				Object bean = PortletBeanLocatorUtil.locate(
					servletContextName, beanIdentifier);

				return methodHandler.invoke(bean);
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	private static Async _nullAsync =
		new Async() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return Async.class;
			}

		};

	private String _defaultDestinationName;
	private Map<Class<?>, String> _destinationNames;

}