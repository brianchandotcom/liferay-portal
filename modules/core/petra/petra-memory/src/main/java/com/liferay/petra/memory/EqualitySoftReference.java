/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof EqualitySoftReference<?>)) {
			return false;
		}

		EqualitySoftReference<?> equalitySoftReference =
			(EqualitySoftReference<?>)object;

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