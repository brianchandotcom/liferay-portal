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
public abstract class NoticeableFutureConverter<T, V>
	extends FutureConverter<T, V> implements NoticeableFuture<T> {

	public NoticeableFutureConverter(NoticeableFuture<V> noticeableFuture) {
		super(noticeableFuture);

		_defaultNoticeableFuture = new DefaultNoticeableFuture<>();

		noticeableFuture.addFutureListener(
			new FutureListener<V>() {

				@Override
				public void complete(Future<V> future) {
					if (future.isCancelled()) {
						_defaultNoticeableFuture.cancel(true);

						return;
					}

					try {
						_defaultNoticeableFuture.set(convert(future.get()));
					}
					catch (Throwable t) {
						if (t instanceof ExecutionException) {
							t = t.getCause();
						}

						_defaultNoticeableFuture.setException(t);
					}
				}

			});
	}

	@Override
	public boolean addFutureListener(FutureListener<T> futureListener) {
		return _defaultNoticeableFuture.addFutureListener(futureListener);
	}

	@Override
	public T get() throws ExecutionException, InterruptedException {
		return _defaultNoticeableFuture.get();
	}

	@Override
	public T get(long timeout, TimeUnit timeUnit)
		throws ExecutionException, InterruptedException, TimeoutException {

		return _defaultNoticeableFuture.get(timeout, timeUnit);
	}

	@Override
	public boolean removeFutureListener(FutureListener<T> futureListener) {
		return _defaultNoticeableFuture.removeFutureListener(futureListener);
	}

	private final DefaultNoticeableFuture<T> _defaultNoticeableFuture;

}