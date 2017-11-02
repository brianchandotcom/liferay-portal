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
import java.lang.ref.WeakReference;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class EqualityWeakReference<T> extends WeakReference<T> {

	public EqualityWeakReference(T referent) {
		super(referent);

		_hashCode = referent.hashCode();
	}

	public EqualityWeakReference(
		T referent, ReferenceQueue<? super T> referenceQueue) {

		super(referent, referenceQueue);

		_hashCode = referent.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EqualityWeakReference<?>)) {
			return false;
		}

		EqualityWeakReference<?> equalityWeakReference =
			(EqualityWeakReference<?>)obj;

		if (Objects.equals(get(), equalityWeakReference.get())) {
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