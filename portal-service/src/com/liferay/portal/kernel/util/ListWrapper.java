/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Brian Wing Shun Chan
 */
public class ListWrapper<E> implements List<E> {

	public ListWrapper(List<E> list) {
		_list = list;
	}

	public boolean add(E o) {
		return _list.add(o);
	}

	public void add(int index, E element) {
		_list.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		return _list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return _list.addAll(index, c);
	}

	public void clear() {
		_list.clear();
	}

	public boolean contains(Object o) {
		return _list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return _list.containsAll(c);
	}

	public E get(int index) {
		return _list.get(index);
	}

	public int indexOf(Object o) {
		return _list.indexOf(o);
	}

	public boolean isEmpty() {
		return _list.isEmpty();
	}

	public Iterator<E> iterator() {
		return _list.iterator();
	}

	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return _list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return _list.listIterator(index);
	}

	public E remove(int index) {
		return _list.remove(index);
	}

	public boolean remove(Object o) {
		return _list.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return _list.retainAll(c);
	}

	public E set(int index, E element) {
		return _list.set(index, element);
	}

	public int size() {
		return _list.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return _list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	private List<E> _list;

}