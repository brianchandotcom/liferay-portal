/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class LinkedHashMapBuilder<K, V> extends BaseMapBuilder {

	public static <K, V> LinkedHashMapWrapper<K, V> create(
		int initialCapacity) {

		return new LinkedHashMapWrapper<>(initialCapacity);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> create(
		int initialCapacity, float loadFactor) {

		return new LinkedHashMapWrapper<>(initialCapacity, loadFactor);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> create(
		int initialCapacity, float loadFactor, boolean accessOrder) {

		return new LinkedHashMapWrapper<>(
			initialCapacity, loadFactor, accessOrder);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> create(
		Map<? extends K, ? extends V> map) {

		return new LinkedHashMapWrapper<>(map);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> put(
		Collection<? extends K> inputCollection,
		UnsafeFunction<K, V, Exception> unsafeFunction) {

		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.put(inputCollection, unsafeFunction);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> put(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.put(key, valueUnsafeSupplier);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> put(K key, V value) {
		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.put(key, value);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.put(keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.put(keyUnsafeSupplier, value);
	}

	public static <K, V> LinkedHashMapWrapper<K, V> putAll(
		Map<? extends K, ? extends V> inputMap) {

		LinkedHashMapWrapper<K, V> linkedHashMapWrapper =
			new LinkedHashMapWrapper<>();

		return linkedHashMapWrapper.putAll(inputMap);
	}

	public static final class LinkedHashMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public LinkedHashMapWrapper() {
			_linkedHashMap = new LinkedHashMap<>();
		}

		public LinkedHashMapWrapper(int initialCapacity) {
			_linkedHashMap = new LinkedHashMap<>(initialCapacity);
		}

		public LinkedHashMapWrapper(int initialCapacity, float loadFactor) {
			_linkedHashMap = new LinkedHashMap<>(initialCapacity, loadFactor);
		}

		public LinkedHashMapWrapper(
			int initialCapacity, float loadFactor, boolean accessOrder) {

			_linkedHashMap = new LinkedHashMap<>(
				initialCapacity, loadFactor, accessOrder);
		}

		public LinkedHashMapWrapper(Map<? extends K, ? extends V> map) {
			_linkedHashMap = new LinkedHashMap<>(map);
		}

		public LinkedHashMap<K, V> build() {
			return _linkedHashMap;
		}

		public LinkedHashMapWrapper<K, V> put(
			Collection<? extends K> inputCollection,
			UnsafeFunction<K, V, Exception> unsafeFunction) {

			doPut(inputCollection, unsafeFunction);

			return this;
		}

		public LinkedHashMapWrapper<K, V> put(
			K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);

			return this;
		}

		public LinkedHashMapWrapper<K, V> put(K key, V value) {
			_linkedHashMap.put(key, value);

			return this;
		}

		public LinkedHashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier,
			UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);

			return this;
		}

		public LinkedHashMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

			doPut(keyUnsafeSupplier, value);

			return this;
		}

		public LinkedHashMapWrapper<K, V> putAll(
			Map<? extends K, ? extends V> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected LinkedHashMap<K, V> getMap() {
			return _linkedHashMap;
		}

		private final LinkedHashMap<K, V> _linkedHashMap;

	}

}