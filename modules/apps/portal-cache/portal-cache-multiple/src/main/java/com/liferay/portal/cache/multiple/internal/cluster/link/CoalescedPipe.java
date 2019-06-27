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

package com.liferay.portal.cache.multiple.internal.cluster.link;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class CoalescedPipe<E> {

	public CoalescedPipe() {
		this(null);
	}

	public CoalescedPipe(Comparator<E> comparator) {
		_comparator = comparator;

		_notEmptyCondition = _takeLock.newCondition();

		_headElementLink = new ElementLink<>(null);

		_lastElementLink = _headElementLink;
	}

	public long coalescedCount() {
		return _coalescedCount.get();
	}

	public int pendingCount() {
		return _pendingCount.get();
	}

	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new NullPointerException();
		}

		int pendingElements = -1;

		_putLock.lockInterruptibly();

		try {
			if (_coalesceElement(e)) {
				return;
			}

			_lastElementLink._nextElementLink = new ElementLink<>(e);

			_lastElementLink = _lastElementLink._nextElementLink;

			pendingElements = _pendingCount.getAndIncrement();
		}
		finally {
			_putLock.unlock();
		}

		if (pendingElements == 0) {
			_takeLock.lock();

			try {
				_notEmptyCondition.signal();
			}
			finally {
				_takeLock.unlock();
			}
		}
	}

	public E take() throws InterruptedException {
		E element = null;

		_takeLock.lockInterruptibly();

		try {
			while (_pendingCount.get() == 0) {
				_notEmptyCondition.await();
			}

			ElementLink<E> garbageELementLink = _headElementLink;

			_headElementLink = _headElementLink._nextElementLink;

			garbageELementLink._nextElementLink = null;

			element = _headElementLink._element;

			_headElementLink._element = null;

			int pendingElements = _pendingCount.getAndDecrement();

			if (pendingElements > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	public Object[] takeSnapshot() {
		_putLock.lock();
		_takeLock.lock();

		try {
			Object[] pendingElements = new Object[_pendingCount.get()];

			ElementLink<E> currentElementLink =
				_headElementLink._nextElementLink;

			for (int i = 0; currentElementLink != null; i++) {
				pendingElements[i] = currentElementLink._element;

				currentElementLink = currentElementLink._nextElementLink;
			}

			return pendingElements;
		}
		finally {
			_putLock.unlock();
			_takeLock.unlock();
		}
	}

	private boolean _coalesceElement(E e) {
		try {
			_takeLock.lockInterruptibly();

			try {
				ElementLink<E> currentElementLink =
					_headElementLink._nextElementLink;

				if (_comparator != null) {
					while (currentElementLink != null) {
						int compare = _comparator.compare(
							currentElementLink._element, e);

						if (compare == 0) {
							_coalescedCount.incrementAndGet();

							return true;
						}

						currentElementLink =
							currentElementLink._nextElementLink;
					}
				}
				else {
					while (currentElementLink != null) {
						if (currentElementLink._element.equals(e)) {
							_coalescedCount.incrementAndGet();

							return true;
						}

						currentElementLink =
							currentElementLink._nextElementLink;
					}
				}
			}
			finally {
				_takeLock.unlock();
			}
		}
		catch (InterruptedException ie) {

			// Continue to let the current element enter the pipe

		}

		return false;
	}

	private final AtomicLong _coalescedCount = new AtomicLong(0);
	private final Comparator<E> _comparator;
	private ElementLink<E> _headElementLink;
	private ElementLink<E> _lastElementLink;
	private final Condition _notEmptyCondition;
	private final AtomicInteger _pendingCount = new AtomicInteger(0);
	private final ReentrantLock _putLock = new ReentrantLock();
	private final ReentrantLock _takeLock = new ReentrantLock();

	private static class ElementLink<E> {

		private ElementLink(E element) {
			_element = element;
		}

		private E _element;
		private ElementLink<E> _nextElementLink;

	}

}