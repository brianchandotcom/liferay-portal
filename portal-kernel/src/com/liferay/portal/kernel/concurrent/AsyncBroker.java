/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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