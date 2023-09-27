/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.testray.extra.gcp.function;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class HashMapBuilder<K, V> extends BaseMapBuilder {

	public static <K, V> HashMapWrapper<K, V> create(int initialCapacity) {
		return new HashMapWrapper<>(initialCapacity);
	}

	public static <K, V> HashMapWrapper<K, V> create(
		int initialCapacity, float loadFactor) {

		return new HashMapWrapper<>(initialCapacity, loadFactor);
	}

	public static <K, V> HashMapWrapper<K, V> create(
		Map<? extends K, ? extends V> map) {

		return new HashMapWrapper<>(map);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		Collection<? extends K> inputCollection,
		UnsafeFunction<K, V, Exception> unsafeFunction) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.put(inputCollection, unsafeFunction);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.put(key, valueUnsafeSupplier);
	}

	public static <K, V> HashMapWrapper<K, V> put(K key, V value) {
		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.put(key, value);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.put(keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.put(keyUnsafeSupplier, value);
	}

	public static <K, V> HashMapWrapper<K, V> putAll(
		Dictionary<? extends K, ? extends V> dictionary) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.putAll(dictionary);
	}

	public static <K, V> HashMapWrapper<K, V> putAll(
		Map<? extends K, ? extends V> inputMap) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.putAll(inputMap);
	}

	public static final class HashMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public HashMapWrapper() {
			_hashMap = new HashMap<>();
		}

		public HashMapWrapper(int initialCapacity) {
			_hashMap = new HashMap<>(initialCapacity);
		}

		public HashMapWrapper(int initialCapacity, float loadFactor) {
			_hashMap = new HashMap<>(initialCapacity, loadFactor);
		}

		public HashMapWrapper(Map<? extends K, ? extends V> map) {
			_hashMap = new HashMap<>(map);
		}

		public HashMap<K, V> build() {
			return _hashMap;
		}

		public HashMapWrapper<K, V> put(
			Collection<? extends K> inputCollection,
			UnsafeFunction<K, V, Exception> unsafeFunction) {

			doPut(inputCollection, unsafeFunction);

			return this;
		}

		public HashMapWrapper<K, V> put(
			K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);

			return this;
		}

		public HashMapWrapper<K, V> put(K key, V value) {
			_hashMap.put(key, value);

			return this;
		}

		public HashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier,
			UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);

			return this;
		}

		public HashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

			doPut(keyUnsafeSupplier, value);

			return this;
		}

		public HashMapWrapper<K, V> putAll(
			Dictionary<? extends K, ? extends V> dictionary) {

			if (dictionary == null) {
				return this;
			}

			Enumeration<? extends K> enumeration = dictionary.keys();

			while (enumeration.hasMoreElements()) {
				K key = enumeration.nextElement();

				_hashMap.put(key, dictionary.get(key));
			}

			return this;
		}

		public HashMapWrapper<K, V> putAll(
			Map<? extends K, ? extends V> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected HashMap<K, V> getMap() {
			return _hashMap;
		}

		private final HashMap<K, V> _hashMap;

	}

}