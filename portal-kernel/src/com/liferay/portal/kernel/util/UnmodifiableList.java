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

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * This is a read-only wrapper around any <code>List</code>. Query operations
 * will "read through" to the specified list. Attempts to modify the list
 * directly or via its iterator will result in a
 * <code>UnsupportedOperationException</code>.
 * </p>
 *
 * @author     Alexander Chow
 * @deprecated As of 7.0.0, replaced by {@link
 *             java.util.Collections#unmodifiableList(List)}
 */
@Deprecated
public class UnmodifiableList<E> implements List<E>, Serializable {

	public UnmodifiableList(List<? extends E> list) {
		if (list == null) {
			throw new NullPointerException();
		}

		_list = list;
	}

	@Override
	public boolean add(E element) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean contains(Object object) {
		return _list.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return _list.containsAll(collection);
	}

	@Override
	public boolean equals(Object object) {
		return _list.equals(object);
	}

	@Override
	public E get(int index) {
		return _list.get(index);
	}

	@Override
	public int hashCode() {
		return _list.hashCode();
	}

	@Override
	public int indexOf(Object object) {
		return _list.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				return _itr.hasNext();
			}

			@Override
			public E next() {
				return _itr.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			private final Iterator<? extends E> _itr = _list.iterator();

		};
	}

	@Override
	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(final int index) {
		return new ListIterator<E>() {

			@Override
			public void add(E element) {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			@Override
			public boolean hasNext() {
				return _itr.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return _itr.hasPrevious();
			}

			@Override
			public E next() {
				return _itr.next();
			}

			@Override
			public int nextIndex() {
				return _itr.nextIndex();
			}

			@Override
			public E previous() {
				return _itr.previous();
			}

			@Override
			public int previousIndex() {
				return _itr.previousIndex();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			@Override
			public void set(E element) {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			private final ListIterator<? extends E> _itr = _list.listIterator(
				index);

		};
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean remove(Object object) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new UnmodifiableList<>(_list.subList(fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	@Override
	public String toString() {
		return _list.toString();
	}

	private static final String _MESSAGE =
		"Please make a copy of this read-only list before modifying it.";

	private final List<? extends E> _list;

}