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

package com.liferay.portal.kernel.dependency.manager;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncUtil {

	public static void registerSyncCallable(Callable<Void> syncCallable) {
		_dependencyManagerSync.registerSyncCallable(syncCallable);
	}

	public static void registerSyncFutureTask(
		FutureTask<Void> syncFutureTask, String taskName) {

		_dependencyManagerSync.registerSyncFutureTask(syncFutureTask, taskName);
	}

	public static void sync() {
		_dependencyManagerSync.sync();
	}

	private static volatile DependencyManagerSync _dependencyManagerSync =
		ServiceProxyFactory.newServiceTrackedInstance(
			DependencyManagerSync.class, DependencyManagerSyncUtil.class,
			"_dependencyManagerSync", true);

}