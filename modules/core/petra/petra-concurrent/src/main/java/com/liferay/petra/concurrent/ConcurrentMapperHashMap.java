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

package com.liferay.petra.concurrent;

import java.io.Serializable;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public abstract class ConcurrentMapperHashMap<K, IK, V, IV>
	extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {

	@Override
	public void clear() {
		innerConcurrentMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		return innerConcurrentMap.containsKey(mapKeyForQuery((K)key));
	}

	@Override
	public boolean containsValue(Object value) {
		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		return innerConcurrentMap.containsValue(mapValueForQuery((V)value));
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (entrySet == null) {
			entrySet = new UnwrapEntrySet();
		}

		return entrySet;
	}

	@Override
	public V get(Object key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		IV innerValue = innerConcurrentMap.get(mapKeyForQuery((K)key));

		if (innerValue == null) {
			return null;
		}

		return unmapValueForQuery(innerValue);
	}

	@Override
	public boolean isEmpty() {
		return innerConcurrentMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		if (keySet == null) {
			keySet = new UnwrapKeySet();
		}

		return keySet;
	}

	@Override
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		IK innerKey = mapKey(key);

		IV oldInnerValue = innerConcurrentMap.put(
			innerKey, mapValue(key, value));

		if (oldInnerValue == null) {
			return null;
		}

		unmapKey(innerKey);

		return unmapValue(oldInnerValue);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V putIfAbsent(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		IK innerKey = mapKey(key);
		IV innerValue = mapValue(key, value);

		IV previousInnerValue = innerConcurrentMap.putIfAbsent(
			innerKey, innerValue);

		if (previousInnerValue == null) {
			return null;
		}

		unmapKey(innerKey);
		unmapValue(innerValue);

		return unmapValueForQuery(previousInnerValue);
	}

	@Override
	public V remove(Object key) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		IK innerKey = mapKeyForQuery((K)key);

		IV innerValue = innerConcurrentMap.remove(innerKey);

		if (innerValue == null) {
			return null;
		}

		return unmapValue(innerValue);
	}

	@Override
	public boolean remove(Object key, Object value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		IK innerKey = mapKeyForQuery((K)key);
		IV innerValue = mapValueForQuery((V)value);

		IV previousInnerValue = innerConcurrentMap.get(innerKey);

		if (!innerValue.equals(previousInnerValue) ||
			!innerConcurrentMap.remove(innerKey, previousInnerValue)) {

			return false;
		}

		unmapValue(previousInnerValue);

		return true;
	}

	@Override
	public V replace(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (value == null) {
			throw new NullPointerException("Value is null");
		}

		IV newInnerValue = mapValue(key, value);

		IV oldInnerValue = innerConcurrentMap.replace(
			mapKeyForQuery(key), newInnerValue);

		if (oldInnerValue == null) {
			unmapValue(newInnerValue);

			return null;
		}

		return unmapValue(oldInnerValue);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		if (key == null) {
			throw new NullPointerException("Key is null");
		}

		if (oldValue == null) {
			throw new NullPointerException("Old value is null");
		}

		if (newValue == null) {
			throw new NullPointerException("New value is null");
		}

		IK innerKey = mapKeyForQuery(key);

		IV newInnerValue = mapValue(key, newValue);

		IV oldInnerValue = innerConcurrentMap.get(innerKey);

		if ((oldInnerValue == null) ||
			!oldValue.equals(unmapValueForQuery(oldInnerValue))) {

			unmapValue(newInnerValue);

			return false;
		}

		if (innerConcurrentMap.replace(
				innerKey, oldInnerValue, newInnerValue)) {

			unmapValue(oldInnerValue);

			return true;
		}

		unmapValue(newInnerValue);

		return false;
	}

	@Override
	public int size() {
		return innerConcurrentMap.size();
	}

	@Override
	public Collection<V> values() {
		if (values == null) {
			values = new UnwrapValues();
		}

		return values;
	}

	protected ConcurrentMapperHashMap(
		ConcurrentMap<IK, IV> innerConcurrentMap) {

		this.innerConcurrentMap = innerConcurrentMap;
	}

	protected abstract IK mapKey(K key);

	protected abstract IK mapKeyForQuery(K key);

	protected abstract IV mapValue(K key, V value);

	protected abstract IV mapValueForQuery(V value);

	protected abstract K unmapKey(IK key);

	protected abstract K unmapKeyForQuery(IK key);

	protected abstract V unmapValue(IV value);

	protected abstract V unmapValueForQuery(IV value);

	protected transient Set<Map.Entry<K, V>> entrySet;
	protected final ConcurrentMap<IK, IV> innerConcurrentMap;
	protected transient Set<K> keySet;
	protected transient Collection<V> values;

	private static final long serialVersionUID = 1L;

	private class UnwrapEntry implements Map.Entry<K, V> {

		public UnwrapEntry(Entry<IK, IV> innerEntry) {
			_innerEntry = innerEntry;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Map.Entry)) {
				return false;
			}

			Map.Entry<K, V> entry = (Map.Entry<K, V>)obj;

			if (Objects.equals(getKey(), entry.getKey()) &&
				Objects.equals(getValue(), entry.getValue())) {

				return true;
			}

			return false;
		}

		@Override
		public K getKey() {
			return unmapKeyForQuery(_innerEntry.getKey());
		}

		@Override
		public V getValue() {
			return unmapValueForQuery(_innerEntry.getValue());
		}

		@Override
		public int hashCode() {
			return _innerEntry.hashCode();
		}

		@Override
		public V setValue(V value) {
			K key = getKey();

			V v = unmapValueForQuery(
				_innerEntry.setValue(mapValueForQuery(value)));

			ConcurrentMapperHashMap.this.put(key, value);

			return v;
		}

		private final Entry<IK, IV> _innerEntry;

	}

	private class UnwrapEntryIterator implements Iterator<Map.Entry<K, V>> {

		public UnwrapEntryIterator() {
			Set<Entry<IK, IV>> entrySet = innerConcurrentMap.entrySet();

			_iterator = entrySet.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public Entry<K, V> next() {
			return new UnwrapEntry(_iterator.next());
		}

		@Override
		public void remove() {
			_iterator.remove();
		}

		private final Iterator<Map.Entry<IK, IV>> _iterator;

	}

	private class UnwrapEntrySet extends AbstractSet<Map.Entry<K, V>> {

		@Override
		public void clear() {
			ConcurrentMapperHashMap.this.clear();
		}

		@Override
		public boolean contains(Object obj) {
			if (!(obj instanceof Map.Entry<?, ?>)) {
				return false;
			}

			Map.Entry<K, V> entry = (Map.Entry<K, V>)obj;

			V value = ConcurrentMapperHashMap.this.get(entry.getKey());

			if ((value != null) && value.equals(entry.getValue())) {
				return true;
			}

			return false;
		}

		@Override
		public boolean isEmpty() {
			return ConcurrentMapperHashMap.this.isEmpty();
		}

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new UnwrapEntryIterator();
		}

		@Override
		public boolean remove(Object obj) {
			if (!(obj instanceof Map.Entry<?, ?>)) {
				return false;
			}

			Map.Entry<K, V> entry = (Map.Entry<K, V>)obj;

			return ConcurrentMapperHashMap.this.remove(
				entry.getKey(), entry.getValue());
		}

		@Override
		public int size() {
			return ConcurrentMapperHashMap.this.size();
		}

	}

	private class UnwrapKeyIterator implements Iterator<K> {

		public UnwrapKeyIterator() {
			Set<IK> keySet = innerConcurrentMap.keySet();

			_iterator = keySet.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public K next() {
			return unmapKeyForQuery(_iterator.next());
		}

		@Override
		public void remove() {
			_iterator.remove();
		}

		private final Iterator<IK> _iterator;

	}

	private class UnwrapKeySet extends AbstractSet<K> {

		@Override
		public void clear() {
			ConcurrentMapperHashMap.this.clear();
		}

		@Override
		public boolean contains(Object o) {
			return ConcurrentMapperHashMap.this.containsKey(o);
		}

		@Override
		public boolean isEmpty() {
			return ConcurrentMapperHashMap.this.isEmpty();
		}

		@Override
		public Iterator<K> iterator() {
			return new UnwrapKeyIterator();
		}

		@Override
		public boolean remove(Object o) {
			if (ConcurrentMapperHashMap.this.remove(o) != null) {
				return true;
			}

			return false;
		}

		@Override
		public int size() {
			return ConcurrentMapperHashMap.this.size();
		}

	}

	private class UnwrapValueIterator implements Iterator<V> {

		public UnwrapValueIterator() {
			Collection<IV> values = innerConcurrentMap.values();

			_iterator = values.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public V next() {
			return unmapValueForQuery(_iterator.next());
		}

		@Override
		public void remove() {
			_iterator.remove();
		}

		private final Iterator<IV> _iterator;

	}

	private class UnwrapValues extends AbstractCollection<V> {

		@Override
		public void clear() {
			ConcurrentMapperHashMap.this.clear();
		}

		@Override
		public boolean contains(Object obj) {
			return ConcurrentMapperHashMap.this.containsValue(obj);
		}

		@Override
		public boolean isEmpty() {
			return ConcurrentMapperHashMap.this.isEmpty();
		}

		@Override
		public Iterator<V> iterator() {
			return new UnwrapValueIterator();
		}

		@Override
		public int size() {
			return ConcurrentMapperHashMap.this.size();
		}

	}

}