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

package com.liferay.portal.increment;

import com.liferay.portal.internal.increment.BufferedIncreasableEntry;
import com.liferay.portal.internal.increment.BufferedIncrementProcessor;
import com.liferay.portal.internal.increment.BufferedIncrementProcessorUtil;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.Increment;
import com.liferay.portal.kernel.increment.IncrementFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Zsolt Berentey
 * @author Shuyang Zhou
 */
public class BufferedIncrementAdvice extends ChainableMethodAdvice {

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		BufferedIncrement bufferedIncrement =
			(BufferedIncrement)annotations.get(BufferedIncrement.class);

		if (bufferedIncrement == null) {
			return null;
		}

		BufferedIncrementProcessor bufferedIncrementProcessor =
			BufferedIncrementProcessorUtil.getBufferedIncrementProcessor(
				bufferedIncrement.configuration());

		if (bufferedIncrementProcessor == null) {
			return null;
		}

		return new BufferedIncrementContext(
			bufferedIncrementProcessor, bufferedIncrement.incrementClass());
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Object before(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		BufferedIncrementContext bufferedIncrementContext =
			aopMethodInvocation.getAdviceMethodContext();

		BufferedIncrementProcessor bufferedIncrementProcessor =
			bufferedIncrementContext._bufferedIncrementProcessor;
		Class<? extends Increment<?>> incrementClass =
			bufferedIncrementContext._incrementClass;

		Object value = arguments[arguments.length - 1];

		CacheKeyGenerator cacheKeyGenerator =
			CacheKeyGeneratorUtil.getCacheKeyGenerator(
				BufferedIncrementAdvice.class.getName());

		for (int i = 0; i < (arguments.length - 1); i++) {
			cacheKeyGenerator.append(StringUtil.toHexString(arguments[i]));
		}

		Serializable batchKey = cacheKeyGenerator.finish();

		try {
			Increment<?> increment = IncrementFactory.createIncrement(
				incrementClass, value);

			BufferedIncreasableEntry bufferedIncreasableEntry =
				new BufferedIncreasableEntry(
					aopMethodInvocation, arguments, batchKey, increment);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					bufferedIncrementProcessor.process(
						bufferedIncreasableEntry);

					return null;
				});
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to increment", e);
			}
		}

		return nullResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BufferedIncrementAdvice.class);

	private static class BufferedIncrementContext {

		private BufferedIncrementContext(
			BufferedIncrementProcessor bufferedIncrementProcessor,
			Class<? extends Increment<?>> incrementClass) {

			_bufferedIncrementProcessor = bufferedIncrementProcessor;
			_incrementClass = incrementClass;
		}

		private final BufferedIncrementProcessor _bufferedIncrementProcessor;
		private final Class<? extends Increment<?>> _incrementClass;

	}

}