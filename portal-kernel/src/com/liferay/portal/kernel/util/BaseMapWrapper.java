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
import java.util.Map;

/**
 * @author Hugo Huijser
 */
public abstract class BaseMapWrapper<K, V> {

	protected void doPut(
		BaseMapBuilder.UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		BaseMapBuilder.UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		if ((keyUnsafeSupplier == null) || (valueUnsafeSupplier == null)) {
			throw new NullPointerException();
		}

		try {
			K key = keyUnsafeSupplier.get();

			if (key == null) {
				return;
			}

			V value = valueUnsafeSupplier.get();

			if (value != null) {
				doPut(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(
		BaseMapBuilder.UnsafeSupplier<K, Exception> keyUnsafeSupplier,
		V value) {

		if ((keyUnsafeSupplier == null) || (value == null)) {
			throw new NullPointerException();
		}

		try {
			K key = keyUnsafeSupplier.get();

			if (key != null) {
				doPut(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(
		Collection<? extends K> keyCollection,
		BaseMapBuilder.UnsafeFunction<K, V, Exception> unsafeFunction) {

		if ((keyCollection == null) || (unsafeFunction == null)) {
			throw new NullPointerException();
		}

		try {
			for (K key : keyCollection) {
				V value = unsafeFunction.apply(key);

				if (value != null) {
					doPut(key, value);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(
		K key,
		BaseMapBuilder.UnsafeSupplier<V, Exception> valueUnsafeSupplier) {

		if ((key == null) || (valueUnsafeSupplier == null)) {
			throw new NullPointerException();
		}

		try {
			V value = valueUnsafeSupplier.get();

			if (value != null) {
				doPut(key, value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void doPut(K key, V value) {
		if ((key == null) || (value == null)) {
			throw new NullPointerException();
		}

		Map<K, V> map = getMap();

		map.put(key, value);
	}

	protected abstract Map<K, V> getMap();

}