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

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Michael C. Han
 */
public class LimitedFIFOQueue<E> extends AbstractQueue<E> {

	public LimitedFIFOQueue(int capacity) {
		_capacity = capacity;
	}

	@Override
	public Iterator<E> iterator() {
		return _linkedList.iterator();
	}

	@Override
	public boolean offer(E e) {
		if (size() >= _capacity) {
			_linkedList.removeFirst();
		}

		_linkedList.offerLast(e);

		return true;
	}

	@Override
	public E peek() {
		return _linkedList.peek();
	}

	@Override
	public E poll() {
		return _linkedList.poll();
	}

	@Override
	public int size() {
		return _linkedList.size();
	}

	private final int _capacity;
	private final LinkedList<E> _linkedList = new LinkedList<>();

}