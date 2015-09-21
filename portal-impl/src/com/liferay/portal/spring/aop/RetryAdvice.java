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
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.service.RetryAcceptor;
import com.liferay.portal.util.PropsValues;

import java.lang.annotation.Annotation;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Matthew Tambara
 */
public class RetryAdvice extends AnnotationChainableMethodAdvice<Retry> {

	@Override
	public Retry getNullAnnotation() {
		return _nullRetry;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Retry retry = findAnnotation(methodInvocation);

		if (retry == _nullRetry) {
			return methodInvocation.proceed();
		}

		int numberOfRetries = retry.retries();

		if (numberOfRetries < 0) {
			numberOfRetries = GetterUtil.getInteger(
				PropsValues.RETRY_ADVICE_DEFAULT_RETRIES, numberOfRetries);
		}

		int totalRetries = numberOfRetries;

		if (numberOfRetries >= 0) {
			numberOfRetries++;
		}

		Property[] properties = retry.properties();

		Map<String, String> propertyMap = new HashMap<>();

		for (Property property : properties) {
			propertyMap.put(property.name(), property.value());
		}

		Class<? extends RetryAcceptor> clazz = retry.acceptor();

		RetryAcceptor retryAdviceAcceptor = clazz.newInstance();

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)methodInvocation;

		serviceBeanMethodInvocation.mark();

		Object returnValue = null;

		Throwable throwable = null;

		while ((numberOfRetries < 0) || (numberOfRetries-- > 0)) {
			try {
				returnValue = serviceBeanMethodInvocation.proceed();

				if (!retryAdviceAcceptor.accept(
						returnValue, null, propertyMap)) {

					return returnValue;
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unsatisfactory result from " +
								methodInvocation.getMethod() + ". Retrying " +
									numberOfRetries + " more times.");
					}
				}
			}
			catch (Throwable t) {
				throwable = t;

				if (!retryAdviceAcceptor.accept(null, t, propertyMap)) {
					throw t;
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.error(
							"Exception thrown from " +
								methodInvocation.getMethod() + ". Retrying " +
									numberOfRetries + " more times.");
					}
				}
			}

			serviceBeanMethodInvocation.reset();
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to get a result after " + totalRetries + " retries.");
		}

		if (returnValue != null) {
			return returnValue;
		}

		throw throwable;
	}

	private static final Log _log = LogFactoryUtil.getLog(RetryAdvice.class);

	private static final Retry _nullRetry = new Retry() {

		@Override
		public Class<? extends RetryAcceptor> acceptor() {
			return null;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return Retry.class;
		}

		@Override
		public Property[] properties() {
			return null;
		}

		@Override
		public int retries() {
			return 0;
		}

	};

}