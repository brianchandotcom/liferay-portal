/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Shuyang Zhou
 */
public abstract class FutureConverter<T, V> implements Future<T> {

	public FutureConverter(Future<V> future) {
		_future = future;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return _future.cancel(mayInterruptIfRunning);
	}

	@Override
	public T get() throws ExecutionException, InterruptedException {
		V v = _future.get();

		try {
			return convert(v);
		}
		catch (Throwable throwable) {
			throw new ExecutionException(throwable);
		}
	}

	@Override
	public T get(long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		V v = _future.get(timeout, timeUnit);

		try {
			return convert(v);
		}
		catch (Throwable throwable) {
			throw new ExecutionException(throwable);
		}
	}

	@Override
	public boolean isCancelled() {
		return _future.isCancelled();
	}

	@Override
	public boolean isDone() {
		return _future.isDone();
	}

	protected abstract T convert(V v) throws Throwable;

	private final Future<V> _future;

}