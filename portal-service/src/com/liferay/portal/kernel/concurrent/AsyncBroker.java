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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.memory.FinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.ref.Reference;
import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class AsyncBroker<K, V> {

	public Map<K, NoticeableFuture<V>> getOpenBids() {
		return Collections.<K, NoticeableFuture<V>>unmodifiableMap(
			_defaultNoticeableFutures);
	}

	public NoticeableFuture<V> post(K key) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<V>();

		NoticeableFuture<V> previousNoticeableFuture = post(
			key, defaultNoticeableFuture);

		if (previousNoticeableFuture == null) {
			return defaultNoticeableFuture;
		}

		return previousNoticeableFuture;
	}

	public NoticeableFuture<V> post(
		final K key, final DefaultNoticeableFuture<V> defaultNoticeableFuture) {

		DefaultNoticeableFuture<V> previousDefaultNoticeableFuture =
			_defaultNoticeableFutures.putIfAbsent(key, defaultNoticeableFuture);

		if (previousDefaultNoticeableFuture != null) {
			return previousDefaultNoticeableFuture;
		}

		defaultNoticeableFuture.addFutureListener(
			new FutureListener<V>() {

				@Override
				public void complete(Future<V> future) {
					_defaultNoticeableFutures.remove(
						key, defaultNoticeableFuture);
				}

			});

		if (_REFERENT_FIELD != null) {
			FinalizeManager.register(
				defaultNoticeableFuture, new CancellationFinalizeAction(key),
				FinalizeManager.PHANTOM_REFERENCE_FACTORY);
		}

		return null;
	}

	public NoticeableFuture<V> take(K key) {
		return _defaultNoticeableFutures.remove(key);
	}

	public boolean takeWithException(K key, Throwable throwable) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			_defaultNoticeableFutures.remove(key);

		if (defaultNoticeableFuture == null) {
			return false;
		}

		defaultNoticeableFuture.setException(throwable);

		return true;
	}

	public boolean takeWithResult(K key, V result) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			_defaultNoticeableFutures.remove(key);

		if (defaultNoticeableFuture == null) {
			return false;
		}

		defaultNoticeableFuture.set(result);

		return true;
	}

	private static final Field _REFERENT_FIELD;

	private static final Log _log = LogFactoryUtil.getLog(AsyncBroker.class);

	static {
		Field referentField = null;

		try {
			referentField = ReflectionUtil.getDeclaredField(
				Reference.class, "referent");
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cancellation of orphaned noticeable futures is disabled " +
						"because the JVM does not support phantom reference " +
							"resurrection",
					t);
			}
		}

		_REFERENT_FIELD = referentField;
	}

	private final ConcurrentMap<K, DefaultNoticeableFuture<V>>
		_defaultNoticeableFutures =
			new ConcurrentReferenceValueHashMap<K, DefaultNoticeableFuture<V>>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

	private static class CancellationFinalizeAction implements FinalizeAction {

		public CancellationFinalizeAction(Object key) {
			_key = key;
		}

		@Override
		public void doFinalize(final Reference<?> reference) {
			try {
				NoticeableFuture<?> noticeableFuture =
					(NoticeableFuture<?>)_REFERENT_FIELD.get(reference);

				if (noticeableFuture.cancel(true) && _log.isWarnEnabled()) {
					_log.warn(
						"Cancelled orphan noticeable future " +
							noticeableFuture + " with key " + _key);
				}
			}
			catch (Exception e) {
				_log.error("Unable to access referent of " + reference, e);
			}
		}

		private final Object _key;

	}

}