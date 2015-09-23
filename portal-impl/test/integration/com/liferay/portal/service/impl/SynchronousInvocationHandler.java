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

package com.liferay.portal.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
public class SynchronousInvocationHandler implements InvocationHandler {

	public static void disable() {
		_SYNCHRONIZE_THREAD_LOCAL.remove();
	}

	public static void enable() {
		_SYNCHRONIZE_THREAD_LOCAL.set(Boolean.TRUE);
	}

	public SynchronousInvocationHandler(
		int syncCount, Runnable syncRunnable, Method syncMethod,
		Object target) {

		_cyclicBarrier = new CyclicBarrier(syncCount, syncRunnable);
		_syncMethod = syncMethod;
		_target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		if ((_SYNCHRONIZE_THREAD_LOCAL.get() == Boolean.TRUE) &&
			_syncMethod.equals(method)) {

			_cyclicBarrier.await();
		}

		return method.invoke(_target, args);
	}

	private static final ThreadLocal<Boolean> _SYNCHRONIZE_THREAD_LOCAL =
		new InheritableThreadLocal<>();

	private final CyclicBarrier _cyclicBarrier;
	private final Method _syncMethod;
	private final Object _target;

}