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

import java.util.Map;

/**
 * @author Hugo Huijser
 */
public abstract class BaseMapWrapper<K, V> {

	protected void doPut(
		K key, UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		try {
			V value = valueUnsafeSupplier.get();

			if (value != null) {
				Map<K, V> map = getMap();

				map.put(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		try {
			K key = keyUnsafeSupplier.get();
			V value = valueUnsafeSupplier.get();

			if ((key != null) && (value != null)) {
				Map<K, V> map = getMap();

				map.put(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(
		UnsafeSupplier<K, Exception> keyUnsafeSupplier, V value) {

		try {
			K key = keyUnsafeSupplier.get();

			if (key != null) {
				Map<K, V> map = getMap();

				map.put(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract Map<K, V> getMap();

}