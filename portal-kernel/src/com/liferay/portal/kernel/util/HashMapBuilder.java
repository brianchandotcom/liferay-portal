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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class HashMapBuilder<K, V> {

	public static <K, V> HashMapWrapper<K, V> put(K key, V value) {
		return new HashMapWrapper<>(key, value);
	}

	public static final class HashMapWrapper<K, V> {

		public HashMapWrapper(K key, V value) {
			_map.put(key, value);
		}

		public Map<K, V> build() {
			return _map;
		}

		public HashMapWrapper<K, V> put(K key, V value) {
			_map.put(key, value);

			return this;
		}

		private final Map<K, V> _map = new HashMap<>();

	}

}