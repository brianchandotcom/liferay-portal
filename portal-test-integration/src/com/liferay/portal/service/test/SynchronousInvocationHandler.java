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

package com.liferay.portal.service.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
public class SynchronousInvocationHandler implements InvocationHandler {

	public static void disable() {
		_synchronizeThreadLocal.remove();
	}

	public static void enable() {
		_synchronizeThreadLocal.set(Boolean.TRUE);
	}

	public SynchronousInvocationHandler(
		int syncCount, Runnable syncRunnable, Method syncMethod,
		Object target) {

		_syncMethod = syncMethod;
		_target = target;

		_cyclicBarrier = new CyclicBarrier(syncCount, syncRunnable);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		if ((_synchronizeThreadLocal.get() == Boolean.TRUE) &&
			_syncMethod.equals(method)) {

			_cyclicBarrier.await();
		}

		return method.invoke(_target, args);
	}

	private static final ThreadLocal<Boolean> _synchronizeThreadLocal =
		new InheritableThreadLocal<>();

	private final CyclicBarrier _cyclicBarrier;
	private final Method _syncMethod;
	private final Object _target;

}