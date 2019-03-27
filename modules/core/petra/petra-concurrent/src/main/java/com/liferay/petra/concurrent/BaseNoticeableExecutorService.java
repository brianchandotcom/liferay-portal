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

package com.liferay.petra.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseNoticeableExecutorService
	extends AbstractExecutorService implements NoticeableExecutorService {

	@Override
	public <T> NoticeableFuture<T> submit(Callable<T> callable) {
		if (callable == null) {
			throw new NullPointerException("Callable is null");
		}

		DefaultNoticeableFuture<T> defaultNoticeableFuture = newTaskFor(
			callable);

		execute(defaultNoticeableFuture);

		return defaultNoticeableFuture;
	}

	@Override
	public NoticeableFuture<?> submit(Runnable runnable) {
		return submit(runnable, null);
	}

	@Override
	public <T> NoticeableFuture<T> submit(Runnable runnable, T result) {
		if (runnable == null) {
			throw new NullPointerException("Runnable is null");
		}

		DefaultNoticeableFuture<T> defaultNoticeableFuture = newTaskFor(
			runnable, result);

		execute(defaultNoticeableFuture);

		return defaultNoticeableFuture;
	}

	@Override
	protected <T> DefaultNoticeableFuture<T> newTaskFor(Callable<T> callable) {
		return new DefaultNoticeableFuture<>(callable);
	}

	@Override
	protected <T> DefaultNoticeableFuture<T> newTaskFor(
		Runnable runnable, T value) {

		return new DefaultNoticeableFuture<>(runnable, value);
	}

}