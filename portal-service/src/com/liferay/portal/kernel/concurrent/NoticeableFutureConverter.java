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

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public abstract class NoticeableFutureConverter<T, V>
	extends FutureConverter<T, V> implements NoticeableFuture<T> {

	public NoticeableFutureConverter(NoticeableFuture<V> noticeableFuture) {
		super(noticeableFuture);

		_noticeableFuture = noticeableFuture;
	}

	@Override
	public boolean addFutureListener(FutureListener<T> futureListener) {
		return _noticeableFuture.addFutureListener(
			new FutureListenerConverter(futureListener));
	}

	@Override
	public boolean removeFutureListener(FutureListener<T> futureListener) {
		return _noticeableFuture.removeFutureListener(
			new FutureListenerConverter(futureListener));
	}

	private final NoticeableFuture<V> _noticeableFuture;

	private class FutureListenerConverter implements FutureListener<V> {

		public FutureListenerConverter(FutureListener<T> futureListener) {
			_futureListener = futureListener;
		}

		@Override
		public void complete(Future<V> future) {
			_futureListener.complete(new FutureConverter<T, V>(future) {

				@Override
				protected T convert(V v) throws Throwable {
					return NoticeableFutureConverter.this.convert(v);
				}

			});
		}

		@Override
		public boolean equals(Object obj) {
			FutureListenerConverter futureListenerConverter =
				(FutureListenerConverter)obj;

			return _futureListener.equals(
				futureListenerConverter._futureListener);
		}

		private final FutureListener<T> _futureListener;

	}

}