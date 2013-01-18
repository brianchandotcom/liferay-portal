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

package com.liferay.portal.increment;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.increment.IncrementFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Zsolt Berentey
 * @author Shuyang Zhou
 */
public class BufferedIncrementAdvice
	extends AnnotationChainableMethodAdvice<BufferedIncrement> {

	@Override
	@SuppressWarnings("rawtypes")
	public Object before(MethodInvocation methodInvocation) throws Throwable {
		BufferedIncrement bufferedIncrement = findAnnotation(methodInvocation);

		if (bufferedIncrement == _nullBufferedIncrement) {
			return null;
		}

		String configuration = bufferedIncrement.configuration();

		BufferedIncrementConfiguration bufferedIncrementConfiguration =
			_configurationMap.get(configuration);

		if (bufferedIncrementConfiguration == null) {
			bufferedIncrementConfiguration = new BufferedIncrementConfiguration(
				configuration);

			_configurationMap.put(
				configuration, bufferedIncrementConfiguration);
		}

		if (!bufferedIncrementConfiguration.isEnabled()) {
			return nullResult;
		}

		Method method = methodInvocation.getMethod();

		BufferedIncrementProcessor bufferedIncrementProcessor =
			_bufferedIncrementProcessorMap.get(method);

		if (bufferedIncrementProcessor == null) {
			bufferedIncrementProcessor = new BufferedIncrementProcessor(
				bufferedIncrementConfiguration, method);

			BufferedIncrementProcessor previousBufferedIncrementProcessor =
				_bufferedIncrementProcessorMap.putIfAbsent(
					method, bufferedIncrementProcessor);

			if (previousBufferedIncrementProcessor != null) {
				bufferedIncrementProcessor = previousBufferedIncrementProcessor;
			}
		}

		Object[] arguments = methodInvocation.getArguments();

		Object value = arguments[arguments.length - 1];

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				BufferedIncrementAdvice.class.getName());

		for (int i = 0; i < arguments.length - 1; i++) {
			cacheKeyGenerator.append(StringUtil.toHexString(arguments[i]));
		}

		Serializable batchKey = cacheKeyGenerator.finish();

		Increment<?> increment = IncrementFactory.createIncrement(
			bufferedIncrement.incrementClass(), value);

		BufferedIncreasableEntry bufferedIncreasableEntry =
			new BufferedIncreasableEntry(methodInvocation, batchKey, increment);

		bufferedIncrementProcessor.process(bufferedIncreasableEntry);

		return nullResult;
	}

	public void destroy() {
		for (BufferedIncrementProcessor bufferedIncrementProcessor :
				_bufferedIncrementProcessorMap.values()) {

			bufferedIncrementProcessor.destroy();
		}
	}

	@Override
	public BufferedIncrement getNullAnnotation() {
		return _nullBufferedIncrement;
	}

	private static BufferedIncrement _nullBufferedIncrement =
		new BufferedIncrement() {

			public Class<? extends Annotation> annotationType() {
				return BufferedIncrement.class;
			}

			public String configuration() {
				return "default";
			}

			public Class<? extends Increment<?>> incrementClass() {
				return null;
			}

			public boolean parallel() {
				return true;
			}

		};

	private ConcurrentMap<Method, BufferedIncrementProcessor>
		_bufferedIncrementProcessorMap =
			new ConcurrentHashMap<Method, BufferedIncrementProcessor>();

	private Map<String, BufferedIncrementConfiguration> _configurationMap =
		new ConcurrentHashMap<String, BufferedIncrementConfiguration>();

}