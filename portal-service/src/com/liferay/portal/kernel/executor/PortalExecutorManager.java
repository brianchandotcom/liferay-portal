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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public interface PortalExecutorManager {

	public <T> NoticeableFuture<T> execute(String name, Callable<T> callable);

	public <T> T execute(
			String name, Callable<T> callable, long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException;

	public ThreadPoolExecutor getPortalExecutor(String name);

	public ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent);

	public ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor);

	public void shutdown();

	public void shutdown(boolean interrupt);

	public void shutdown(String name);

	public void shutdown(String name, boolean interrupt);

}