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

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return _list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return _list.addAll(index, c);
	}

	@Override
	public void clear() {
		_list.clear();
	}

	@Override
	public boolean contains(Object o) {
		return _list.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return _list.containsAll(c);
	}

	@Override
	public E get(int index) {
		return _list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return _list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return _list.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return _list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return _list.listIterator(index);
	}

	@Override
	public E remove(int index) {
		return _list.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return _list.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return _list.retainAll(c);
	}

	public E set(int index, E element) {
		return _list.set(index, element);
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	private List<E> _list;

}