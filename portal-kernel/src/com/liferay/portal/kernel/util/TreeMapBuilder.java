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
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class TreeMapBuilder<K, V> extends BaseMapBuilder {

	public static <K, V> TreeMapWrapper<K, V> put(
		Collection<? extends K> inputCollection,
		UnsafeFunction<K, V, Exception> unsafeFunction) {

		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.put(inputCollection, unsafeFunction);
	}

	public static <K, V> TreeMapWrapper<K, V> put(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.put(key, valueUnsafeSupplier);
	}

	public static <K, V> TreeMapWrapper<K, V> put(K key, V value) {
		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.put(key, value);
	}

	public static <K, V> TreeMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.put(keyUnsafeSupplier, valueUnsafeSupplier);
	}

	public static <K, V> TreeMapWrapper<K, V> put(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.put(keyUnsafeSupplier, value);
	}

	public static final class TreeMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public TreeMap<K, V> build() {
			return _treeMap;
		}

		public TreeMapWrapper<K, V> put(
			Collection<? extends K> inputCollection,
			UnsafeFunction<K, V, Exception> unsafeFunction) {

			doPut(inputCollection, unsafeFunction);

			return this;
		}

		public TreeMapWrapper<K, V> put(
			K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(key, valueUnsafeSupplier);

			return this;
		}

		public TreeMapWrapper<K, V> put(K key, V value) {
			_treeMap.put(key, value);

			return this;
		}

		public TreeMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier,
			UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

			doPut(keyUnsafeSupplier, valueUnsafeSupplier);

			return this;
		}

		public TreeMapWrapper<K, V> put(
			UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

			doPut(keyUnsafeSupplier, value);

			return this;
		}

		@Override
		protected TreeMap<K, V> getMap() {
			return _treeMap;
		}

		private final TreeMap<K, V> _treeMap = new TreeMap<>();

	}

}