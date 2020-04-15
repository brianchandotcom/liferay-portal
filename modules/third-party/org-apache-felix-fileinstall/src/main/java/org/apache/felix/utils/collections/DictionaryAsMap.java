/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.utils.collections;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A wrapper around a dictionary access it as a Map
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
/* @generated */