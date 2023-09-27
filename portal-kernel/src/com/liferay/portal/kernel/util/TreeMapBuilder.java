/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Hugo Huijser
 */
public class TreeMapBuilder<K, V> extends BaseMapBuilder {

	public static <K, V> TreeMapWrapper<K, V> create(
		Comparator<? super K> comparator) {

		return new TreeMapWrapper<>(comparator);
	}

	public static <K, V> TreeMapWrapper<K, V> create(
		Map<? extends K, ? extends V> map) {

		return new TreeMapWrapper<>(map);
	}

	public static <K, V> TreeMapWrapper<K, V> create(
		SortedMap<K, ? extends V> sortedMap) {

		return new TreeMapWrapper<>(sortedMap);
	}

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

	public static <K, V> TreeMapWrapper<K, V> putAll(
		Map<? extends K, ? extends V> inputMap) {

		TreeMapWrapper<K, V> treeMapWrapper = new TreeMapWrapper<>();

		return treeMapWrapper.putAll(inputMap);
	}

	public static final class TreeMapWrapper<K, V>
		extends BaseMapWrapper<K, V> {

		public TreeMapWrapper() {
			_treeMap = new TreeMap<>();
		}

		public TreeMapWrapper(Comparator<? super K> comparator) {
			_treeMap = new TreeMap<>(comparator);
		}

		public TreeMapWrapper(Map<? extends K, ? extends V> map) {
			_treeMap = new TreeMap<>(map);
		}

		public TreeMapWrapper(SortedMap<K, ? extends V> sortedMap) {
			_treeMap = new TreeMap<>(sortedMap);
		}

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

		public TreeMapWrapper<K, V> putAll(
			Map<? extends K, ? extends V> inputMap) {

			doPutAll(inputMap);

			return this;
		}

		@Override
		protected TreeMap<K, V> getMap() {
			return _treeMap;
		}

		private final TreeMap<K, V> _treeMap;

	}

}