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

package com.liferay.portal.kernel.concurrent;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class TaskQueue<E> {

	public TaskQueue() {
		this(Integer.MAX_VALUE);
	}

	public TaskQueue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}

		_capacity = capacity;

		_headNode = new Node<>(null);

		_tailNode = _headNode;

		_notEmptyCondition = _takeLock.newCondition();
	}

	public int drainTo(Collection<E> collection) {
		if (collection == null) {
			throw new NullPointerException();
		}

		_takeLock.lock();

		try {
			Node<E> headNode = _headNode;

			int size = _count.get();

			int count = 0;

			try {
				while (count < size) {
					Node<E> currentNode = headNode._nextNode;

					collection.add(currentNode._element);

					currentNode._element = null;

					headNode._nextNode = null;

					headNode = currentNode;

					count++;
				}

				return count;
			}
			finally {
				if (count > 0) {
					_headNode = headNode;

					_count.getAndAdd(-count);
				}
			}
		}
		finally {
			_takeLock.unlock();
		}
	}

	public boolean isEmpty() {
		if (_count.get() == 0) {
			return true;
		}

		return false;
	}

	public boolean offer(E element, boolean[] hasWaiterMarker) {
		if ((element == null) || (hasWaiterMarker == null)) {
			throw new NullPointerException();
		}

		if (hasWaiterMarker.length == 0) {
			throw new IllegalArgumentException();
		}

		if (_count.get() == _capacity) {
			return false;
		}

		int count = -1;

		_putLock.lock();

		try {

			// Take a snapshot of count before enqueue

			count = _count.get();

			if (count < _capacity) {
				_enqueue(element);

				if (_count.getAndIncrement() == 0) {

					// Signal takers right after enqueue to increase the
					// possibility of a concurrent token

					_takeLock.lock();

					try {
						_notEmptyCondition.signal();
					}
					finally {
						_takeLock.unlock();
					}
				}

				// After enqueue, a non-increasing count implies a concurrent
				// token because there are spare threads

				if (count >= _count.get()) {
					hasWaiterMarker[0] = true;
				}
			}
		}
		finally {
			_putLock.unlock();
		}

		if (count >= 0) {
			return true;
		}

		return false;
	}

	public E poll() {
		if (_count.get() == 0) {
			return null;
		}

		E element = null;

		_takeLock.lock();

		try {
			if (_count.get() > 0) {
				element = _dequeue();

				if (_count.getAndDecrement() > 1) {
					_notEmptyCondition.signal();
				}
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	public E poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
		E element = null;

		long nanos = timeUnit.toNanos(timeout);

		_takeLock.lockInterruptibly();

		try {
			while (_count.get() == 0) {
				if (nanos <= 0) {
					return null;
				}

				nanos = _notEmptyCondition.awaitNanos(nanos);
			}

			element = _dequeue();

			if (_count.getAndDecrement() > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	public int remainingCapacity() {
		return _capacity - _count.get();
	}

	public boolean remove(E element) {
		if (element == null) {
			return false;
		}

		_fullyLock();

		try {
			Node<E> previousNode = _headNode;

			Node<E> currentNode = previousNode._nextNode;

			while (currentNode != null) {
				if (element.equals(currentNode._element)) {
					_unlink(currentNode, previousNode);

					return true;
				}

				previousNode = currentNode;
				currentNode = currentNode._nextNode;
			}

			return false;
		}
		finally {
			_fullyUnlock();
		}
	}

	public int size() {
		return _count.get();
	}

	public E take() throws InterruptedException {
		E element = null;

		_takeLock.lockInterruptibly();

		try {
			while (_count.get() == 0) {
				_notEmptyCondition.await();
			}

			element = _dequeue();

			if (_count.getAndDecrement() > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	protected ReentrantLock getPutLock() {
		return _putLock;
	}

	protected ReentrantLock getTakeLock() {
		return _takeLock;
	}

	private E _dequeue() {
		Node<E> headNode = _headNode;

		Node<E> firstNode = headNode._nextNode;

		headNode._nextNode = null;

		_headNode = firstNode;

		E element = firstNode._element;

		firstNode._element = null;

		return element;
	}

	private void _enqueue(E element) {
		_tailNode._nextNode = new Node<>(element);

		_tailNode = _tailNode._nextNode;
	}

	private void _fullyLock() {
		_putLock.lock();
		_takeLock.lock();
	}

	private void _fullyUnlock() {
		_takeLock.unlock();
		_putLock.unlock();
	}

	private void _unlink(Node<E> currentNode, Node<E> previousNode) {
		currentNode._element = null;
		previousNode._nextNode = currentNode._nextNode;

		if (_tailNode == currentNode) {
			_tailNode = previousNode;
		}

		_count.getAndDecrement();
	}

	private final int _capacity;
	private final AtomicInteger _count = new AtomicInteger();
	private Node<E> _headNode;
	private final Condition _notEmptyCondition;
	private final ReentrantLock _putLock = new ReentrantLock();
	private Node<E> _tailNode;
	private final ReentrantLock _takeLock = new ReentrantLock(true);

	private static class Node<E> {

		private Node(E element) {
			_element = element;
		}

		private E _element;
		private Node<E> _nextNode;

	}

}