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

package com.liferay.portal.file.install.internal.collections;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Tambara
 */
public class DictionaryAsMap<K, V> extends AbstractMap<K, V> {

	public DictionaryAsMap(Dictionary<K, V> dictionary) {
		_dictionary = dictionary;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {

			@Override
			public Iterator<Entry<K, V>> iterator() {
				Enumeration<K> enumeration = _dictionary.keys();

				return new Iterator<Entry<K, V>>() {

					@Override
					public boolean hasNext() {
						return enumeration.hasMoreElements();
					}

					@Override
					public Entry<K, V> next() {
						_key = enumeration.nextElement();

						return new KeyEntry(_key);
					}

					@Override
					public void remove() {
						if (_key == null) {
							throw new IllegalStateException();
						}

						_dictionary.remove(_key);
					}

					private K _key;

				};
			}

			@Override
			public int size() {
				return _dictionary.size();
			}

		};
	}

	@Override
	public V put(K key, V value) {
		return _dictionary.put(key, value);
	}

	private final Dictionary<K, V> _dictionary;

	private class KeyEntry implements Map.Entry<K, V> {

		public KeyEntry(K key) {
			_key = key;
		}

		@Override
		public K getKey() {
			return _key;
		}

		@Override
		public V getValue() {
			return _dictionary.get(_key);
		}

		@Override
		public V setValue(V value) {
			return DictionaryAsMap.this.put(_key, value);
		}

		private final K _key;

	}

}