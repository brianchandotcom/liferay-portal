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

package com.liferay.petra.memory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class EqualitySoftReference<T> extends SoftReference<T> {

	public EqualitySoftReference(T referent) {
		super(referent);

		_hashCode = referent.hashCode();
	}

	public EqualitySoftReference(
		T referent, ReferenceQueue<? super T> referenceQueue) {

		super(referent, referenceQueue);

		_hashCode = referent.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EqualitySoftReference<?>)) {
			return false;
		}

		EqualitySoftReference<?> equalitySoftReference =
			(EqualitySoftReference<?>)obj;

		if (Objects.equals(get(), equalitySoftReference.get())) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _hashCode;
	}

	private final int _hashCode;

}