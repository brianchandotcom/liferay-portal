/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Shuyang Zhou
 */
public class DefaultNoticeableFuture<T>
	extends FutureTask<T> implements NoticeableFuture<T> {

	@SuppressWarnings("unchecked")
	public DefaultNoticeableFuture() {
		super((Callable<T>)_emptyCallable);
	}

	public DefaultNoticeableFuture(Callable<T> callable) {
		super(callable);
	}

	public DefaultNoticeableFuture(Runnable runnable, T result) {
		super(runnable, result);
	}

	@Override
	public boolean addFutureListener(FutureListener<T> futureListener) {
		if (futureListener == null) {
			throw new NullPointerException("Future listener is null");
		}

		futureListener = new OnceFutureListener<>(futureListener);

		if (_futureListeners.add(futureListener)) {
			if (isDone()) {
				futureListener.complete(this);
			}

			return true;
		}

		return false;
	}

	@Override
	public boolean removeFutureListener(FutureListener<T> futureListener) {
		if (futureListener == null) {
			throw new NullPointerException("Future listener is null");
		}

		return _futureListeners.remove(
			new OnceFutureListener<T>(futureListener));
	}

	@Override
	public void set(T t) {
		super.set(t);
	}

	@Override
	public void setException(Throwable throwable) {
		super.setException(throwable);
	}

	@Override
	protected void done() {
		for (FutureListener<T> futureListener : _futureListeners) {
			futureListener.complete(this);
		}
	}

	private static final Callable<Object> _emptyCallable =
		new Callable<Object>() {

			@Override
			public Object call() {
				return null;
			}

		};

	private final Set<FutureListener<T>> _futureListeners =
		new CopyOnWriteArraySet<>();

	private static class OnceFutureListener<V> implements FutureListener<V> {

		public OnceFutureListener(FutureListener<V> futureListener) {
			_futureListener = futureListener;
		}

		@Override
		public void complete(Future<V> future) {
			if (_ran.compareAndSet(false, true)) {
				_futureListener.complete(future);
			}
		}

		@Override
		public boolean equals(Object object) {
			OnceFutureListener<V> onceFutureListener =
				(OnceFutureListener<V>)object;

			return _futureListener.equals(onceFutureListener._futureListener);
		}

		@Override
		public int hashCode() {
			return _futureListener.hashCode();
		}

		private final FutureListener<V> _futureListener;
		private final AtomicBoolean _ran = new AtomicBoolean();

	}

}