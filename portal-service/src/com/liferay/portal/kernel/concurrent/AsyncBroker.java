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

import com.liferay.portal.kernel.memory.FinalizeManager;

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

	public NoticeableFuture<V> post(final K key) {
		DefaultNoticeableFuture<V> defaultNoticeableFuture =
			new DefaultNoticeableFuture<V>();

		DefaultNoticeableFuture<V> previousDefaultNoticeableFuture =
			_defaultNoticeableFutures.putIfAbsent(key, defaultNoticeableFuture);

		if (previousDefaultNoticeableFuture != null) {
			return previousDefaultNoticeableFuture;
		}

		defaultNoticeableFuture.addFutureListener(
			new FutureListener<V>() {

				@Override
				public void complete(Future<V> future) {
					_defaultNoticeableFutures.remove(key);
				}

			});

		return defaultNoticeableFuture;
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

	private final ConcurrentMap<K, DefaultNoticeableFuture<V>>
		_defaultNoticeableFutures =
			new ConcurrentReferenceValueHashMap<K, DefaultNoticeableFuture<V>>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

}