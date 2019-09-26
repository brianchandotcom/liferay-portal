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

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ConcurrentLFUCache<K, V> {

	public ConcurrentLFUCache(int maxSize) {
		this(maxSize, 0.75F);
	}

	public ConcurrentLFUCache(int maxSize, float loadFactor) {
		if ((maxSize <= 0) || (loadFactor <= 0) || (loadFactor >= 1)) {
			throw new IllegalArgumentException();
		}

		_maxSize = maxSize;

		_expectedSize = (int)(maxSize * loadFactor);

		if (_expectedSize == 0) {
			throw new IllegalArgumentException(
				"maxSize and loadFactor are too small");
		}

		_readLock = _readWriteLock.readLock();
		_writeLock = _readWriteLock.writeLock();
	}

	public void clear() {
		_writeLock.lock();

		try {
			_cache.clear();
		}
		finally {
			_writeLock.unlock();
		}
	}

	public long evictCount() {
		return _evictCount.get();
	}

	public int expectedSize() {
		return _expectedSize;
	}

	public V get(K key) {
		_readLock.lock();

		try {
			ValueWrapper valueWrapper = _cache.get(key);

			if (valueWrapper != null) {
				valueWrapper._hitCount.getAndIncrement();

				_hitCount.getAndIncrement();

				return valueWrapper._value;
			}
		}
		finally {
			_readLock.unlock();
		}

		_missCount.getAndIncrement();

		return null;
	}

	public long hitCount() {
		return _hitCount.get();
	}

	public int maxSize() {
		return _maxSize;
	}

	public long missCount() {
		return _missCount.get();
	}

	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		ValueWrapper valueWrapper = new ValueWrapper(value);

		_writeLock.lock();

		try {
			if (!_cache.containsKey(key) && (_cache.size() >= _maxSize)) {
				_cleanUp();
			}

			_cache.put(key, valueWrapper);
		}
		finally {
			_writeLock.unlock();
		}

		_putCount.getAndIncrement();
	}

	public long putCount() {
		return _putCount.get();
	}

	public int size() {
		_readLock.lock();

		try {
			return _cache.size();
		}
		finally {
			_readLock.unlock();
		}
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{evictCount=");
		sb.append(_evictCount.get());
		sb.append(", expectedSize=");
		sb.append(_expectedSize);
		sb.append(", hitCount=");
		sb.append(_hitCount.get());
		sb.append(", maxSize=");
		sb.append(_maxSize);
		sb.append(", missCount=");
		sb.append(_missCount.get());
		sb.append(", putCount=");
		sb.append(_putCount.get());
		sb.append(", size=");
		sb.append(size());
		sb.append("}");

		return sb.toString();
	}

	protected void onRemove(K key, V value) {
	}

	private void _cleanUp() {
		List<Map.Entry<K, ValueWrapper>> valueWrappers = new ArrayList<>(
			_cache.entrySet());

		Collections.sort(valueWrappers, _entryComparator);

		int cleanUpSize = _cache.size() - _expectedSize;

		_evictCount.getAndAdd(cleanUpSize);

		Iterator<Map.Entry<K, ValueWrapper>> itr = valueWrappers.iterator();

		while ((cleanUpSize-- > 0) && itr.hasNext()) {
			Map.Entry<K, ValueWrapper> entry = itr.next();

			K key = entry.getKey();

			ValueWrapper valueWrapper = entry.getValue();

			V value = valueWrapper._value;

			_cache.remove(key);

			onRemove(key, value);

			itr.remove();
		}
	}

	private final Map<K, ValueWrapper> _cache = new HashMap<>();
	private final EntryComparator _entryComparator = new EntryComparator();
	private final AtomicLong _evictCount = new AtomicLong();
	private final int _expectedSize;
	private final AtomicLong _hitCount = new AtomicLong();
	private final int _maxSize;
	private final AtomicLong _missCount = new AtomicLong();
	private final AtomicLong _putCount = new AtomicLong();
	private final Lock _readLock;
	private final ReentrantReadWriteLock _readWriteLock =
		new ReentrantReadWriteLock();
	private final Lock _writeLock;

	private class EntryComparator
		implements Comparator<Map.Entry<K, ValueWrapper>> {

		@Override
		public int compare(
			Map.Entry<K, ValueWrapper> entry1,
			Map.Entry<K, ValueWrapper> entry2) {

			ValueWrapper valueWrapper1 = entry1.getValue();
			ValueWrapper valueWrapper2 = entry2.getValue();

			long hitCount1 = valueWrapper1._hitCount.get();
			long hitCount2 = valueWrapper2._hitCount.get();

			return (int)(hitCount1 - hitCount2);
		}

	}

	private class ValueWrapper {

		public ValueWrapper(V v) {
			_value = v;
		}

		private final AtomicLong _hitCount = new AtomicLong();
		private final V _value;

	}

}