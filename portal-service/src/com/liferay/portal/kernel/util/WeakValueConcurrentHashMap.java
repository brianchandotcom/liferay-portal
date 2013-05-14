/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.memory.EqualityWeakReference;
import com.liferay.portal.kernel.memory.FinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;

import java.io.Serializable;

import java.lang.ref.Reference;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class WeakValueConcurrentHashMap<K, V>
	implements ConcurrentMap<K, V>, Serializable {

	public WeakValueConcurrentHashMap() {
		_map = new ConcurrentHashMap<K, Reference<V>>();
	}

	public WeakValueConcurrentHashMap(int initialCapacity) {
		_map = new ConcurrentHashMap<K, Reference<V>>(initialCapacity);
	}

	public WeakValueConcurrentHashMap(
		int initialCapacity, float loadFactor, int concurrencyLevel) {

		_map = new ConcurrentHashMap<K, Reference<V>>(
			initialCapacity, loadFactor, concurrencyLevel);
	}

	public WeakValueConcurrentHashMap(Map<? extends K, ? extends V> map) {
		_map = new ConcurrentHashMap<K, Reference<V>>();

		putAll(map);
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(new EqualityWeakReference<V>((V)value));
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		if (_entrySet == null) {
			_entrySet = new UnwrapEntrySet();
		}

		return _entrySet;
	}

	@Override
	public V get(Object key) {
		Reference<V> valueReference = _map.get(key);

		if (valueReference != null) {
			return valueReference.get();
		}

		return null;
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return _map.keySet();
	}

	public V put(K key, V value) {
		Reference<V> valueReference = wrapValue(key, value);

		valueReference = _map.putIfAbsent(key, valueReference);

		if (valueReference != null) {
			return valueReference.get();
		}

		return null;
	}

	@Override
	public final void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();

			Reference<V> valueReference = wrapValue(key, value);

			_map.put(key, valueReference);
		}
	}

	public V putIfAbsent(K key, V value) {
		Reference<V> valueReference = wrapValue(key, value);

		valueReference = _map.putIfAbsent(key, valueReference);

		if (valueReference != null) {
			return valueReference.get();
		}

		return null;
	}

	@Override
	public V remove(Object key) {
		Reference<V> valueReference = _map.remove(key);

		if (valueReference != null) {
			return valueReference.get();
		}

		return null;
	}

	@Override
	public boolean remove(Object key, Object value) {
		Reference<V> valueReference = wrapValue(key, value);

		return _map.remove(key, valueReference);
	}

	public V replace(K key, V value) {
		Reference<V> valueReference = wrapValue(key, value);

		valueReference = _map.replace(key, valueReference);

		if (valueReference != null) {
			return valueReference.get();
		}

		return null;
	}

	public boolean replace(K key, V oldValue, V newValue) {
		Reference<V> oldValueReference = wrapValue(key, oldValue);
		Reference<V> newValueReference = wrapValue(key, newValue);

		return _map.replace(key, oldValueReference, newValueReference);
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public Collection<V> values() {
		if (_values == null) {
			_values = new UnwrapValues();
		}

		return _values;
	}

	protected Reference<V> wrapValue(Object key, Object value) {
		return FinalizeManager.register(
			(V)value, new RemoveEntryFinalizeAction((K)key));
	}

	private transient Set<Map.Entry<K, V>> _entrySet;
	private final ConcurrentMap<K, Reference<V>> _map;
	private transient Collection<V> _values;

	private class RemoveEntryFinalizeAction implements FinalizeAction {

		public RemoveEntryFinalizeAction(K key) {
			_key = key;
		}

		public void doFinalize() {
			remove(_key);
		}

		private final K _key;

	}

	private class UnwrapEntry implements Map.Entry<K, V> {

		public UnwrapEntry(Entry<K, Reference<V>> entry) {
			_entry = entry;
		}

		public K getKey() {
			return _entry.getKey();
		}

		public V getValue() {
			Reference<V> valueReference = _entry.getValue();

			if (valueReference != null) {
				return valueReference.get();
			}

			return null;
		}

		public V setValue(V value) {
			return WeakValueConcurrentHashMap.this.put(_entry.getKey(), value);
		}

		private Map.Entry<K, Reference<V>> _entry;

	}

	private class UnwrapEntryIterator implements Iterator<Map.Entry<K, V>> {

		public UnwrapEntryIterator() {
			_iterator = _map.entrySet().iterator();
		}

		public boolean hasNext() {
			return _iterator.hasNext();
		}

		public Entry<K, V> next() {
			return new UnwrapEntry(_iterator.next());
		}

		public void remove() {
			_iterator.remove();
		}

		private Iterator<Map.Entry<K, Reference<V>>> _iterator;

	}

	private class UnwrapEntrySet extends AbstractSet<Map.Entry<K, V>> {

		@Override
		public void clear() {
			WeakValueConcurrentHashMap.this.clear();
		}

		@Override
		public boolean contains(Object obj) {
			if (!(obj instanceof Map.Entry<?, ?>)) {
				return false;
			}

			Map.Entry<K, V> entry = (Map.Entry<K, V>)obj;

			V value = WeakValueConcurrentHashMap.this.get(entry.getKey());

			if ((value != null) && value.equals(entry.getValue())) {
				return true;
			}
			else {
				return false;
			}
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

			return WeakValueConcurrentHashMap.this.remove(
				entry.getKey(), entry.getValue());
		}

		@Override
		public int size() {
			return WeakValueConcurrentHashMap.this.size();
		}

		@Override
		public Object[] toArray() {
			List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(size());

			Iterator<Map.Entry<K, V>> iterator = iterator();

			while (iterator.hasNext()) {
				list.add(iterator.next());
			}

			return list.toArray();
		}

		@Override
		public <T> T[] toArray(T[] array) {
			List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(size());

			Iterator<Map.Entry<K, V>> iterator = iterator();

			while (iterator.hasNext()) {
				list.add(iterator.next());
			}

			return list.toArray(array);
		}

	}

	private class UnwrapValueIterator implements Iterator<V> {

		public UnwrapValueIterator() {
			_iterator = _map.values().iterator();
		}

		public boolean hasNext() {
			return _iterator.hasNext();
		}

		public V next() {
			Reference<V> valueReference = _iterator.next();

			if (valueReference != null) {
				return valueReference.get();
			}

			return null;
		}

		public void remove() {
			_iterator.remove();
		}

		private Iterator<Reference<V>> _iterator;

	}

	private class UnwrapValues extends AbstractCollection<V> {

		@Override
		public void clear() {
			WeakValueConcurrentHashMap.this.clear();
		}

		@Override
		public boolean contains(Object obj) {
			return WeakValueConcurrentHashMap.this.containsValue(obj);
		}

		@Override
		public Iterator<V> iterator() {
			return new UnwrapValueIterator();
		}

		@Override
		public int size() {
			return WeakValueConcurrentHashMap.this.size();
		}

		@Override
		public Object[] toArray() {
			List<V> list = new ArrayList<V>();

			Iterator<V> iterator = iterator();

			while (iterator.hasNext()) {
				list.add(iterator.next());
			}

			return list.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			List<V> list = new ArrayList<V>();

			Iterator<V> iterator = iterator();

			while (iterator.hasNext()) {
				list.add(iterator.next());
			}

			return list.toArray(a);
		}

	}

}