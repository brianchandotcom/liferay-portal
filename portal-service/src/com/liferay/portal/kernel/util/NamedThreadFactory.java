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

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class NamedThreadFactory implements ThreadFactory {

	public NamedThreadFactory(
		String name, int priority, ClassLoader contextClassLoader) {

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			_group = securityManager.getThreadGroup();
		}
		else {
			Thread currentThread = Thread.currentThread();

			_group = currentThread.getThreadGroup();
		}

		_name = name;
		_priority = priority;
		_contextClassLoader = contextClassLoader;
	}

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(
			_group, runnable,
			_name.concat(StringPool.MINUS).concat(
				String.valueOf(_counter.incrementAndGet())));

		thread.setDaemon(true);
		thread.setPriority(_priority);

		if (_contextClassLoader != null) {
			thread.setContextClassLoader(_contextClassLoader);
		}

		return thread;
	}

	private final ClassLoader _contextClassLoader;
	private final AtomicInteger _counter = new AtomicInteger();
	private final ThreadGroup _group;
	private final String _name;
	private final int _priority;

}