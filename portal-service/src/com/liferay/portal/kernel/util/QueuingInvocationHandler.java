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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Michael C. Han
 */
public class QueuingInvocationHandler implements InvocationHandler {

	public QueuingInvocationHandler(int capacity) {
		_methodHandlers = new LimitedFIFOQueue<>(capacity);
	}

	public void flush() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Flush " + _methodHandlers.size() + " events from queue");
		}

		_methodHandlers.clear();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		MethodHandler methodHandler = new MethodHandler(method, args);

		_methodHandlers.offer(methodHandler);

		return null;
	}

	public void invokeQueued(Object target) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Processing " + _methodHandlers.size() + " queued requests");
		}

		MethodHandler methodHandler = null;

		while ((methodHandler = _methodHandlers.poll()) != null) {
			methodHandler.invoke(target);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Completed processing queued requests");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		QueuingInvocationHandler.class);

	private final LimitedFIFOQueue<MethodHandler> _methodHandlers;

}