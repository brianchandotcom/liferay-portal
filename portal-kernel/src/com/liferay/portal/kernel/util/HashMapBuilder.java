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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class HashMapBuilder<K, V> extends BaseMapBuilder {

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
		Map<? extends K, ? extends V> inputMap) {

		HashMapWrapper<K, V> hashMapWrapper = new HashMapWrapper<>();

		return hashMapWrapper.putAll(inputMap);
	}

	public static final class HashMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

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
			Map<? extends K, ? extends V> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected HashMap<K, V> getMap() {
			return _hashMap;
		}

		private final HashMap<K, V> _hashMap = new HashMap<>();

	}

}