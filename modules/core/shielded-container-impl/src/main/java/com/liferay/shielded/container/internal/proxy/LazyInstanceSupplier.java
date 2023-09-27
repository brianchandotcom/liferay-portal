/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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