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

package com.liferay.portal.kernel.concurrent;

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
		catch (Throwable t) {
			throw new ExecutionException(t);
		}
	}

	@Override
	public T get(long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		V v = _future.get(timeout, timeUnit);

		try {
			return convert(v);
		}
		catch (Throwable t) {
			throw new ExecutionException(t);
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