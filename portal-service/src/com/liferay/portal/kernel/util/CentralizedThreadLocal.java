/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Shuyang Zhou
 */
public class CentralizedThreadLocal<T> extends ThreadLocal<T> {

	public static void clearLongLivedThreadLocals() {
		_longLivedThreadLocals.remove();
	}

	public static void clearShortLivedThreadLocals() {
		_shortLivedThreadLocals.remove();
	}

	public static Map<CentralizedThreadLocal<?>, Object>
		getLongLivedThreadLocals() {

		return _toMap(_longLivedThreadLocals.get());
	}

	public static Map<CentralizedThreadLocal<?>, Object>
		getShortLivedThreadLocals() {

		return _toMap(_shortLivedThreadLocals.get());
	}

	public static void setThreadLocals(
		Map<CentralizedThreadLocal<?>, Object> longLivedThreadLocals,
		Map<CentralizedThreadLocal<?>, Object> shortLivedThreadLocals) {

		ThreadLocalMap threadLocals = _longLivedThreadLocals.get();

		for (Map.Entry<CentralizedThreadLocal<?>, Object> entry :
			longLivedThreadLocals.entrySet()) {

			threadLocals.putEntry(entry.getKey(), entry.getValue());
		}

		threadLocals = _shortLivedThreadLocals.get();

		for (Map.Entry<CentralizedThreadLocal<?>, Object> entry :
			shortLivedThreadLocals.entrySet()) {

			threadLocals.putEntry(entry.getKey(), entry.getValue());
		}
	}

	private static Map<CentralizedThreadLocal<?>, Object> _toMap(
		ThreadLocalMap threadLocals) {

		Map<CentralizedThreadLocal<?>, Object> map =
			new HashMap<CentralizedThreadLocal<?>, Object>(
				threadLocals._table.length);

		for (Entry entry : threadLocals._table) {
			map.put(entry._key, entry._value);
		}

		return map;
	}

	public CentralizedThreadLocal(boolean shortLived) {
		_shortLived = shortLived;

		if (shortLived) {
			_hashCode = _shortLivedNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
		else {
			_hashCode = _longLivedNextHasCode.getAndAdd(_HASH_INCREMENT);
		}
	}

	@Override
	public T get() {
		ThreadLocalMap threadLocals = _getThreadLocals();

		Entry entry = threadLocals.getEntry(this);

		if (entry == null) {
			T value = initialValue();

			threadLocals.putEntry(this, value);

			return value;
		}
		else {
			return (T)entry._value;
		}
	}

	@Override
	public int hashCode() {
		return _hashCode;
	}

	@Override
	public void remove() {
		ThreadLocalMap threadLocals = _getThreadLocals();

		threadLocals.removeEntry(this);
	}

	@Override
	public void set(T value) {
		ThreadLocalMap threadLocals = _getThreadLocals();

		threadLocals.putEntry(this, value);
	}

	private ThreadLocalMap _getThreadLocals() {
		if (_shortLived) {
			return _shortLivedThreadLocals.get();
		}
		else {
			return _longLivedThreadLocals.get();
		}
	}

	private static final int _HASH_INCREMENT = 0x61c88647;

	private static final AtomicInteger _longLivedNextHasCode =
		new AtomicInteger();
	private static final ThreadLocal<ThreadLocalMap>
		_longLivedThreadLocals = new MapThreadLocal();
	private static final AtomicInteger _shortLivedNextHasCode =
		new AtomicInteger();
	private static final ThreadLocal<ThreadLocalMap>
		_shortLivedThreadLocals = new MapThreadLocal();

	private static class MapThreadLocal extends ThreadLocal<ThreadLocalMap> {

		@Override
		protected ThreadLocalMap initialValue() {
			return new ThreadLocalMap();
		}

	}

	private static class Entry {

		public Entry(CentralizedThreadLocal key, Object value, Entry next) {
			_key = key;
			_value = value;
			_next = next;
		}

		private CentralizedThreadLocal _key;
		private Entry _next;
		private Object _value;

	}

	private static class ThreadLocalMap {

		private void expand(int newCapacity) {
			if (_table.length == MAXIMUM_CAPACITY) {
				// Reach max capacity, stop further expanding
				_threshold = Integer.MAX_VALUE;

				return;
			}

			Entry[] newTable = new Entry[newCapacity];

			// Move entries to new table
			for (int i = 0; i < _table.length; i++) {
				Entry entry = _table[i];

				if (entry != null) {
					// Null out old reference to help GC
					_table[i] = null;

					do {
						Entry nextEntry = entry._next;

						// Reindex
						int index = entry._key._hashCode & (newCapacity - 1);

						// Linked in new table
						entry._next = newTable[index];
						newTable[index] = entry;

						entry = nextEntry;
					}
					while (entry != null);
				}
			}

			_table = newTable;

			_threshold = newCapacity * 2 / 3;
		}

		private Entry getEntry(CentralizedThreadLocal key) {
			int index = key._hashCode & (_table.length - 1);

			Entry entry = _table[index];

			if (entry == null) {
				// No such entry
				return null;
			}
			else if (entry._key == key) {
				// Direct hit
				return entry;
			}
			else {
				// Open list search
				while ((entry = entry._next) != null) {
					if (entry._key == key) {
						return entry;
					}
				}

				// No such entry
				return null;
			}
		}

		private void putEntry(CentralizedThreadLocal key, Object value) {
			int index = key._hashCode & (_table.length - 1);

			for (Entry entry = _table[index]; entry != null;
				entry = entry._next) {

				if (entry._key == key) {
					// Update exist value
					entry._value = value;

					return;
				}
			}

			// Insert new Entry
			_table[index] = new Entry(key, value, _table[index]);

			// Expand table
			if (_size++ >= _threshold) {
				expand(2 * _table.length);
			}
		}

		private void removeEntry(CentralizedThreadLocal key) {
			int index = key._hashCode & (_table.length - 1);

			Entry previousEntry = null;
			Entry entry = _table[index];

			while (entry != null) {
				Entry nextEntry = entry._next;

				if (entry._key == key) {
					_size--;

					if (previousEntry == null) {
						// First entry
						_table[index] = nextEntry;
					}
					else {
						// Relink
						previousEntry._next = nextEntry;
					}

					return;
				}

				previousEntry = entry;
				entry = nextEntry;
			}
		}

		private static final int INITIAL_CAPACITY = 16;

		private static final int MAXIMUM_CAPACITY = 1 << 30;

		private Entry[] _table = new Entry[INITIAL_CAPACITY];

		private int _size;

		private int _threshold = INITIAL_CAPACITY * 2 / 3;;
	}

	private final int _hashCode;
	private final boolean _shortLived;

}