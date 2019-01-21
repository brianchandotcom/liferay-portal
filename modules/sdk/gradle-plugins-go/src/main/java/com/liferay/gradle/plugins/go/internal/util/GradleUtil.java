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

package com.liferay.gradle.plugins.go.internal.util;

/**
 * @author Peter Shin
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	/**
	 * Copied from <code>com.liferay.portal.kernel.util.ThreadUtil</code>.
	 */
	public static Thread[] getThreads() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup();

		while (threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}

		int threadCountGuess = threadGroup.activeCount();

		Thread[] threads = new Thread[threadCountGuess];

		int threadCountActual = threadGroup.enumerate(threads);

		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;

			threads = new Thread[threadCountGuess];

			threadCountActual = threadGroup.enumerate(threads);
		}

		return threads;
	}

	public static boolean isRunningInsideDaemon() {
		for (Thread thread : getThreads()) {
			if (thread == null) {
				continue;
			}

			String name = thread.getName();

			if (name.startsWith("Daemon worker")) {
				return true;
			}
		}

		return false;
	}

}