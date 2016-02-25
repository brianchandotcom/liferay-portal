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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * @author Igor Spasic
 */
public class SortedArrayList<E> extends ArrayList<E> {

	public SortedArrayList() {
	}

	public SortedArrayList(Collection<? extends E> c) {
		addAll(c);
	}

	public SortedArrayList(Comparator<E> comparator) {
		_comparator = comparator;
	}

	@Override
	public boolean add(E e) {
		int index = 0;

		if (!isEmpty()) {
			index = _findInsertionPoint(e);
		}

		super.add(index, e);

		return true;
	}

	@Override
	public void add(int index, E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;

		for (E e : c) {
			add(e);

			modified = true;
		}

		return modified;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E e) {
		throw new UnsupportedOperationException();
	}

	protected int compare(E e1, E e2) {
		if (_comparator == null) {
			Comparable<E> comparator1 = (Comparable<E>)e1;

			return comparator1.compareTo(e2);
		}

		return _comparator.compare(e1, e2);
	}

	private int _findInsertionPoint(E e) {
		return _findInsertionPoint(e, 0, size() - 1);
	}

	private int _findInsertionPoint(E e, int low, int high) {
		while (low <= high) {
			int mid = (low + high) >>> 1;

			int delta = compare(get(mid), e);

			if (delta > 0) {
				high = mid - 1;
			}
			else {
				low = mid + 1;
			}
		}

		return low;
	}

	private Comparator<E> _comparator;

}