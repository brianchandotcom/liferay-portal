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

package com.liferay.portal.kernel.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class LinkedHashMapBuilder<K, V> extends BaseMapBuilder {

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

		private final LinkedHashMap<K, V> _linkedHashMap =
			new LinkedHashMap<>();

	}

}