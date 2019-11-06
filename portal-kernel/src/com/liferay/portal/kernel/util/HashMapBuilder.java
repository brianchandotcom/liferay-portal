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

import com.liferay.petra.function.UnsafeSupplier;

import java.util.HashMap;

/**
 * @author Hugo Huijser
 */
public class HashMapBuilder<K, V> {

	public static <K, V> HashMapWrapper<K, V> put(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		return new HashMapWrapper<>(key, valueUnsafeSupplier);
	}

	public static <K, V> HashMapWrapper<K, V> put(K key, V value) {
		return new HashMapWrapper<>(key, value);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		return new HashMapWrapper<>(keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static <K, V> HashMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		return new HashMapWrapper<>(keyUnsafeSupplier, value);
	}

	public static final class HashMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public HashMapWrapper(
			K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);
		}

		public HashMapWrapper(K key, V value) {
			_hashMap.put(key, value);
		}

		public HashMapWrapper(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier,
			UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);
		}

		public HashMapWrapper(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

			doPut(keyUnsafeSupplier, value);
		}

		public HashMap<K, V> build() {
			return _hashMap;
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

		@Override
		protected HashMap<K, V> getMap() {
			return _hashMap;
		}

		private final HashMap<K, V> _hashMap = new HashMap<>();

	}

}