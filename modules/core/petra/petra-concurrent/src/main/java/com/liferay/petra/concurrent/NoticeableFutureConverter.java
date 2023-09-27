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
public abstract class NoticeableFutureConverter<T, V>
	extends FutureConverter<T, V> implements NoticeableFuture<T> {

	public NoticeableFutureConverter(NoticeableFuture<V> noticeableFuture) {
		super(noticeableFuture);

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
					catch (Throwable throwable) {
						if (throwable instanceof ExecutionException) {
							throwable = throwable.getCause();
						}

						_defaultNoticeableFuture.setException(throwable);
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

	private final DefaultNoticeableFuture<T> _defaultNoticeableFuture =
		new DefaultNoticeableFuture<>();

}