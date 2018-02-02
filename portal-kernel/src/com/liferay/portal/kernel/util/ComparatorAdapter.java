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