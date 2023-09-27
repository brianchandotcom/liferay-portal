/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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