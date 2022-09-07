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

package com.liferay.portal.test.function;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeSupplier;

/**
 * @author Raymond Augé
 */
public class CloseableHolder<T>
	implements AutoCloseable, UnsafeSupplier<T, Exception> {

	public CloseableHolder(
			UnsafeConsumer<T, Exception> onCloseUnsafeConsumer,
			UnsafeSupplier<T, Exception> onInitUnsafeSupplier)
		throws Exception {

		_onCloseUnsafeConsumer = onCloseUnsafeConsumer;

		_t = onInitUnsafeSupplier.get();
	}

	@Override
	public void close() throws Exception {
		if (_t != null) {
			_onCloseUnsafeConsumer.accept(_t);
		}
	}

	@Override
	public T get() throws Exception {
		return _t;
	}

	private final UnsafeConsumer<T, Exception> _onCloseUnsafeConsumer;
	private final T _t;

}