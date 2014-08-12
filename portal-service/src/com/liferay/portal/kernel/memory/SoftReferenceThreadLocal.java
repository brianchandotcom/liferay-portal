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

package com.liferay.portal.kernel.memory;

import com.liferay.portal.kernel.util.CentralizedThreadLocal;

import java.lang.ref.SoftReference;

/**
 * @author Shuyang Zhou
 */
public class SoftReferenceThreadLocal<T> extends CentralizedThreadLocal<T> {

	public SoftReferenceThreadLocal() {
		super(false);
	}

	@Override
	public T get() {
		SoftReference<T> softReference = _softReferenceThreadLocal.get();

		if (softReference == _nullSoftReference) {
			return null;
		}

		T value = null;

		if (softReference != null) {
			value = softReference.get();
		}

		if (value == null) {
			value = initialValue();

			set(value);
		}

		return value;
	}

	@Override
	public void remove() {
		_softReferenceThreadLocal.remove();
	}

	@Override
	public void set(T value) {
		if (value == null) {
			_softReferenceThreadLocal.set((SoftReference<T>)_nullSoftReference);
		}
		else {
			_softReferenceThreadLocal.set(new SoftReference<T>(value));
		}
	}

	private static final SoftReference<Object> _nullSoftReference =
		new SoftReference<Object>(null);

	private final ThreadLocal<SoftReference<T>> _softReferenceThreadLocal =
		new CentralizedThreadLocal<SoftReference<T>>(false);

}