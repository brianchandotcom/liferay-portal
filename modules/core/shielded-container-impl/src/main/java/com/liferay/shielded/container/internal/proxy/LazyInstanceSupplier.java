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

package com.liferay.shielded.container.internal.proxy;

import java.lang.reflect.Constructor;

import java.util.function.Supplier;

/**
 * @author Shuyang Zhou
 */
public class LazyInstanceSupplier<T> implements Supplier<T> {

	public LazyInstanceSupplier(Class<T> clazz) throws NoSuchMethodException {
		_constructor = clazz.getConstructor();
	}

	@Override
	public T get() {
		T t = _t;

		if (t != null) {
			return t;
		}

		synchronized (this) {
			if (_t == null) {
				try {
					_t = _constructor.newInstance();
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new RuntimeException(reflectiveOperationException);
				}
			}
		}

		return _t;
	}

	private final Constructor<T> _constructor;
	private volatile T _t;

}