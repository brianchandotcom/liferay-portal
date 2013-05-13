/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This is a read-only wrapper around any <code>java.util.List</code>. Query
 * operations will "read through" to the specified list. Attempts to modify the
 * list directly or via its iterator will result in a
 * <code>java.lang.UnsupportedOperationException</code>.
 * </p>
 *
 * @author Alexander Chow
 */
public class UnmodifiableList<E> implements List<E>, Serializable {

	public UnmodifiableList(List<? extends E> list) {
		if (list == null) {
			throw new NullPointerException();
		}

		_list = list;
	}

	public boolean add(E element) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

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

			Iterator<? extends E> itr = _list.iterator();

			public boolean hasNext() {
				return itr.hasNext();
			}

			public E next() {
				return itr.next();
			}

			public void remove() {
				throw new UnsupportedOperationException(_MESSAGE);
			}

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

			ListIterator<? extends E> itr = _list.listIterator(index);

			public void add(E element) {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			public boolean hasNext() {
				return itr.hasNext();
			}

			public E next() {
				return itr.next();
			}

			public boolean hasPrevious() {
				return itr.hasPrevious();
			}

			public E previous() {
				return itr.previous();
			}

			public int nextIndex() {
				return itr.nextIndex();
			}

			public int previousIndex() {
				return itr.previousIndex();
			}

			public void remove() {
				throw new UnsupportedOperationException(_MESSAGE);
			}

			public void set(E element) {
				throw new UnsupportedOperationException(_MESSAGE);
			}

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

	public E set(int index, E element) {
		throw new UnsupportedOperationException(_MESSAGE);
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new UnmodifiableList<E>(_list.subList(fromIndex, toIndex));
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	@Override
	public String toString() {
		return _list.toString();
	}

	private static final String _MESSAGE =
		"Please make a copy of this read-only list before modifying it.";

	private List<? extends E> _list;

}