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

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;

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
			new DefaultNoticeableFuture<V>() {

				@Override
				protected void finalize() {
					cancel(true);
				}

			};

		NoticeableFuture<V> previousNoticeableFuture = _post(
			key, defaultNoticeableFuture);

		if (previousNoticeableFuture == null) {
			return defaultNoticeableFuture;
		}

		return previousNoticeableFuture;
	}

	public NoticeableFuture<V> post(K key, boolean[] newMarker) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<>();

		NoticeableFuture<V> previousNoticeableFuture = _post(
			key, defaultNoticeableFuture);

		if (previousNoticeableFuture == null) {
			newMarker[0] = true;

			return defaultNoticeableFuture;
		}

		newMarker[0] = false;

		return previousNoticeableFuture;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #post(Object,
	 *             boolean[])}
	 */
	@Deprecated
	public NoticeableFuture<V> post(
		final K key, final DefaultNoticeableFuture<V> defaultNoticeableFuture) {

		return _post(key, defaultNoticeableFuture);
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

	private NoticeableFuture<V> _post(
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

		return null;
	}

	private final ConcurrentMap<K, DefaultNoticeableFuture<V>>
		_defaultNoticeableFutures = new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}