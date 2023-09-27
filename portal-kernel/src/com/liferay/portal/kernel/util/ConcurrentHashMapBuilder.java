/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hugo Huijser
 */
public class ConcurrentHashMapBuilder<K, V> extends BaseMapBuilder {

	public static <K, V> ConcurrentHashMapWrapper<K, V> create(
		int initialCapacity) {

		return new ConcurrentHashMapWrapper<>(initialCapacity);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> create(
		int initialCapacity, float loadFactor) {

		return new ConcurrentHashMapWrapper<>(initialCapacity, loadFactor);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> create(
		int initialCapacity, float loadFactor, int concurrencyLevel) {

		return new ConcurrentHashMapWrapper<>(
			initialCapacity, loadFactor, concurrencyLevel);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> create(
		Map<? extends K, ? extends V> map) {

		return new ConcurrentHashMapWrapper<>(map);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> put(
		Collection<? extends K> inputCollection,
		UnsafeFunction<K, V, Exception> unsafeFunction) {

		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.put(inputCollection, unsafeFunction);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> put(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.put(key, valueUnsafeSupplier);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> put(K key, V value) {
		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.put(key, value);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.put(
			keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.put(keyUnsafeSupplier, value);
	}

	public static <K, V> ConcurrentHashMapWrapper<K, V> putAll(
		Map<? extends K, ? extends V> inputMap) {

		ConcurrentHashMapWrapper<K, V> concurrentHashMapWrapper =
			new ConcurrentHashMapWrapper<>();

		return concurrentHashMapWrapper.putAll(inputMap);
	}

	public static final class ConcurrentHashMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public ConcurrentHashMapWrapper() {
			_concurrentHashMap = new ConcurrentHashMap<>();
		}

		public ConcurrentHashMapWrapper(int initialCapacity) {
			_concurrentHashMap = new ConcurrentHashMap<>(initialCapacity);
		}

		public ConcurrentHashMapWrapper(int initialCapacity, float loadFactor) {
			_concurrentHashMap = new ConcurrentHashMap<>(
				initialCapacity, loadFactor);
		}

		public ConcurrentHashMapWrapper(
			int initialCapacity, float loadFactor, int concurrencyLevel) {

			_concurrentHashMap = new ConcurrentHashMap<>(
				initialCapacity, loadFactor, concurrencyLevel);
		}

		public ConcurrentHashMapWrapper(Map<? extends K, ? extends V> map) {
			_concurrentHashMap = new ConcurrentHashMap<>(map);
		}

		public ConcurrentHashMap<K, V> build() {
			return _concurrentHashMap;
		}

		public ConcurrentHashMapWrapper<K, V> put(
			Collection<? extends K> inputCollection,
			UnsafeFunction<K, V, Exception> unsafeFunction) {

			doPut(inputCollection, unsafeFunction);

			return this;
		}

		public ConcurrentHashMapWrapper<K, V> put(
			K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);

			return this;
		}

		public ConcurrentHashMapWrapper<K, V> put(K key, V value) {
			_concurrentHashMap.put(key, value);

			return this;
		}

		public ConcurrentHashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier,
			UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);

			return this;
		}

		public ConcurrentHashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

			doPut(keyUnsafeSupplier, value);

			return this;
		}

		public ConcurrentHashMapWrapper<K, V> putAll(
			Map<? extends K, ? extends V> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected ConcurrentHashMap<K, V> getMap() {
			return _concurrentHashMap;
		}

		private final ConcurrentHashMap<K, V> _concurrentHashMap;

	}

}