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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TaskQueueTest extends TestCase {

	@Test
	public void testConstructor() {
		try {
			new TaskQueue<Object>(0);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			new TaskQueue<Object>(-1);
			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		TaskQueue<Object> taskQueue = new TaskQueue<Object>(10);

		assertEquals(10, taskQueue.remainingCapacity());

		taskQueue = new TaskQueue<Object>();

		assertEquals(Integer.MAX_VALUE, taskQueue.remainingCapacity());
	}

	@Test
	public void testDrainTo() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>();

		try {
			taskQueue.drainTo(null);

			fail();
		}
		catch (NullPointerException npe) {
		}

		Object object1 = new Object();
		Object object2 = new Object();
		Object object3 = new Object();
		Object object4 = new Object();

		taskQueue = new TaskQueue<Object>();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertTrue(taskQueue.offer(object2, new boolean[1]));
		assertTrue(taskQueue.offer(object3, new boolean[1]));
		assertTrue(taskQueue.offer(object4, new boolean[1]));

		Set<Object> set = new HashSet<Object>();

		taskQueue.drainTo(set);

		assertEquals(4, set.size());
		assertTrue(set.contains(object1));
		assertTrue(set.contains(object2));
		assertTrue(set.contains(object3));
		assertTrue(set.contains(object4));

		object1 = new Object();
		object2 = new Object();
		object3 = new Object();
		object4 = new Object();

		taskQueue = new TaskQueue<Object>();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertTrue(taskQueue.offer(object2, new boolean[1]));
		assertTrue(taskQueue.offer(object3, new boolean[1]));
		assertTrue(taskQueue.offer(object4, new boolean[1]));

		List<Object> list = new ArrayList<Object>() {

			@Override
			public boolean add(Object e) {
				if (size() >= 2) {
					throw new IllegalStateException();
				}

				return super.add(e);
			}

		};

		try {
			taskQueue.drainTo(list);

			fail();
		}
		catch (IllegalStateException ise) {
		}

		assertEquals(2, list.size());
		assertSame(object1, list.get(0));
		assertSame(object2, list.get(1));
		assertEquals(2, taskQueue.size());
		assertSame(object3, taskQueue.poll());
		assertSame(object4, taskQueue.poll());
	}

	@Test
	public void testIsEmpty() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>();

		assertTrue(taskQueue.isEmpty());
		assertTrue(taskQueue.offer(new Object(), new boolean[1]));
		assertFalse(taskQueue.isEmpty());
		assertNotNull(taskQueue.poll());
		assertTrue(taskQueue.isEmpty());
	}

	@Test
	public void testOffer() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>(10);

		try {
			taskQueue.offer(null, new boolean[1]);

			fail();
		}
		catch (NullPointerException npe) {
		}

		taskQueue = new TaskQueue<Object>(10);

		try {
			taskQueue.offer(new Object(), null);

			fail();
		}
		catch (NullPointerException npe) {
		}

		taskQueue = new TaskQueue<Object>(10);

		try {
			taskQueue.offer(new Object(), new boolean[0]);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		taskQueue = new TaskQueue<Object>(10);

		boolean[] hasWaiterMarker = new boolean[1];

		boolean result = taskQueue.offer(new Object(), hasWaiterMarker);

		assertTrue(result);
		assertFalse(hasWaiterMarker[0]);
	}

	@Test
	public void testPoll() throws InterruptedException {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>();

		assertNull(taskQueue.poll());

		taskQueue = new TaskQueue<Object>();

		Object object1 = new Object();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertSame(object1, taskQueue.poll());

		taskQueue = new TaskQueue<Object>();

		object1 = new Object();
		Object object2 = new Object();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertTrue(taskQueue.offer(object2, new boolean[1]));
		assertSame(object1, taskQueue.poll());

		taskQueue = new TaskQueue<Object>();

		assertNull(taskQueue.poll(0, TimeUnit.MILLISECONDS));

		taskQueue = new TaskQueue<Object>();

		assertNull(taskQueue.poll(-1, TimeUnit.MILLISECONDS));

		taskQueue = new TaskQueue<Object>();

		assertNull(taskQueue.poll(100, TimeUnit.MILLISECONDS));

		taskQueue = new TaskQueue<Object>();

		object1 = new Object();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertSame(object1, taskQueue.poll(100, TimeUnit.MILLISECONDS));

		taskQueue = new TaskQueue<Object>();

		object1 = new Object();
		object2 = new Object();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertTrue(taskQueue.offer(object2, new boolean[1]));
		assertSame(object1, taskQueue.poll(100, TimeUnit.MILLISECONDS));
	}

	@Test
	public void testRemainingCapacity() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>(10);

		assertEquals(10, taskQueue.remainingCapacity());

		for (int i = 1; i <= 10; i++) {
			assertTrue(taskQueue.offer(new Object(), new boolean[1]));
			assertEquals(10 - i, taskQueue.remainingCapacity());
		}
	}

	@Test
	public void testRemove() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>(10);

		assertFalse(taskQueue.remove(null));
		assertFalse(taskQueue.remove(new Object()));

		Object object1 = new Object();
		Object object2 = new Object();
		Object object3 = new Object();

		assertTrue(taskQueue.offer(object1, new boolean[1]));
		assertTrue(taskQueue.offer(object2, new boolean[1]));
		assertTrue(taskQueue.offer(object3, new boolean[1]));
		assertEquals(3, taskQueue.size());
		assertTrue(taskQueue.remove(object2));
		assertEquals(2, taskQueue.size());
		assertTrue(taskQueue.remove(object1));
		assertEquals(1, taskQueue.size());
		assertTrue(taskQueue.remove(object3));
		assertEquals(0, taskQueue.size());
	}

	@Test
	public void testSize() {
		TaskQueue<Object> taskQueue = new TaskQueue<Object>(10);

		assertEquals(0, taskQueue.size());

		for (int i = 1; i <= 10; i++) {
			assertTrue(taskQueue.offer(new Object(), new boolean[1]));
			assertEquals(i, taskQueue.size());
		}
	}

	@Test
	public void testTake() throws InterruptedException {
		final TaskQueue<Object> taskQueue = new TaskQueue<Object>();
		final Object object = new Object();

		assertTrue(taskQueue.offer(object, new boolean[1]));
		assertSame(object, taskQueue.take());

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					for (int i = 0; i < 10; i++) {
						assertEquals(i, taskQueue.take());
					}
				}
				catch (InterruptedException ie) {
					fail();
				}

				try {
					taskQueue.take();
					fail();
				}
				catch (InterruptedException ie) {
				}
			}

		};

		thread.start();

		for (int i = 0; i < 10; i++) {
			assertTrue(taskQueue.offer(i, new boolean[1]));
		}

		Thread.sleep(TestUtil.SHORT_WAIT);

		thread.interrupt();
		thread.join();
	}

}