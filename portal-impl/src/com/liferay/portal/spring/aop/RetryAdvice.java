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
import com.liferay.portal.kernel.service.RetryAcceptor;
import com.liferay.portal.kernel.spring.aop.Property;
import com.liferay.portal.kernel.spring.aop.Retry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PropsValues;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class RetryAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Retry retry = (Retry)annotations.get(Retry.class);

		if (retry == null) {
			return null;
		}

		int retries = retry.retries();

		if (retries < 0) {
			retries = PropsValues.RETRY_ADVICE_MAX_RETRIES;
		}

		Map<String, String> properties = new HashMap<>();

		for (Property property : retry.properties()) {
			properties.put(property.name(), property.value());
		}

		Class<? extends RetryAcceptor> clazz = retry.acceptor();

		try {
			RetryAcceptor retryAcceptor = clazz.newInstance();

			return new RetryContext(retryAcceptor, properties, retries);
		}
		catch (ReflectiveOperationException roe) {
			_log.error(roe, roe);

			return null;
		}
	}

	@Override
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		RetryContext retryContext =
			serviceBeanMethodInvocation.getAdviceMethodContext();

		RetryAcceptor retryAcceptor = retryContext._retryAcceptor;
		Map<String, String> properties = retryContext._properties;

		int retries = retryContext._retries;

		int totalRetries = retries;

		if (retries >= 0) {
			retries++;
		}

		Object returnValue = null;
		Throwable throwable = null;

		while ((retries < 0) || (retries-- > 0)) {
			try {
				returnValue = serviceBeanMethodInvocation.proceed(arguments);

				if (!retryAcceptor.acceptResult(returnValue, properties)) {
					return returnValue;
				}

				if (_log.isWarnEnabled() && (retries != 0)) {
					String number = String.valueOf(retries);

					if (retries < 0) {
						number = "unlimited";
					}

					_log.warn(
						StringBundler.concat(
							"Retry on ",
							String.valueOf(serviceBeanMethodInvocation),
							" for ", number, " more times due to result ",
							String.valueOf(returnValue)));
				}
			}
			catch (Throwable t) {
				throwable = t;

				if (!retryAcceptor.acceptException(t, properties)) {
					throw t;
				}

				if (_log.isWarnEnabled() && (retries != 0)) {
					String number = String.valueOf(retries);

					if (retries < 0) {
						number = "unlimited";
					}

					_log.warn(
						StringBundler.concat(
							"Retry on ",
							String.valueOf(serviceBeanMethodInvocation),
							" for ", number, " more times due to exception ",
							String.valueOf(throwable)),
						throwable);
				}
			}
		}

		if (throwable != null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Give up retrying on ",
						String.valueOf(serviceBeanMethodInvocation), " after ",
						String.valueOf(totalRetries),
						" retries and rethrow last retry's exception ",
						String.valueOf(throwable)),
					throwable);
			}

			throw throwable;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Give up retrying on ",
					String.valueOf(serviceBeanMethodInvocation), " after ",
					String.valueOf(totalRetries),
					" retries and returning the last retry's result ",
					String.valueOf(returnValue)));
		}

		return returnValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(RetryAdvice.class);

	private static class RetryContext {

		private RetryContext(
			RetryAcceptor retryAcceptor, Map<String, String> properties,
			int retries) {

			_retryAcceptor = retryAcceptor;
			_properties = properties;
			_retries = retries;
		}

		private final Map<String, String> _properties;
		private final int _retries;
		private final RetryAcceptor _retryAcceptor;

	}

}