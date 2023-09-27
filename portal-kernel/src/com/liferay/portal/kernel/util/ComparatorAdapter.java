/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Comparator;

/**
 * @author Sergio González
 */
public abstract class ComparatorAdapter<T, V> implements Comparator<T> {

	public ComparatorAdapter(Comparator<V> comparator) {
		_comparator = comparator;
	}

	public abstract V adapt(T t);

	@Override
	public int compare(T o1, T o2) {
		return _comparator.compare(adapt(o1), adapt(o2));
	}

	public Comparator<V> getAdaptedComparator() {
		return _comparator;
	}

	@Override
	public String toString() {
		return _comparator.toString();
	}

	private final Comparator<V> _comparator;

}