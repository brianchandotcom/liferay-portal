/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CoalescedPipeTest {

	@Test
	public void testBlockingTake() throws InterruptedException {
		final CoalescedPipe<String> coalescedPipe = new CoalescedPipe<>();

		ScheduledExecutorService scheduledExecutorService =
			Executors.newScheduledThreadPool(1);

		scheduledExecutorService.schedule(
			new Runnable() {

				@Override
				public void run() {
					try {
						coalescedPipe.put("test1");
					}
					catch (InterruptedException ie) {
						Assert.fail(ie.getMessage());
					}
				}

			},
			500, TimeUnit.MILLISECONDS);

		long startTime = System.currentTimeMillis();

		Assert.assertEquals("test1", coalescedPipe.take());
		Assert.assertTrue((System.currentTimeMillis() - startTime) > 250L);

		scheduledExecutorService.shutdownNow();
		scheduledExecutorService.awaitTermination(120, TimeUnit.SECONDS);
	}

	@Test
	public void testNonBlockingTake() throws InterruptedException {
		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<>();

		coalescedPipe.put("test2");
		coalescedPipe.put("test3");

		long startTime = System.currentTimeMillis();

		Assert.assertEquals("test2", coalescedPipe.take());
		Assert.assertTrue((System.currentTimeMillis() - startTime) < 100);

		startTime = System.currentTimeMillis();

		Assert.assertEquals("test3", coalescedPipe.take());
		Assert.assertTrue((System.currentTimeMillis() - startTime) < 100);
	}

	@Test
	public void testPut() throws InterruptedException {

		// Without comparator

		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<>();

		// Null

		try {
			coalescedPipe.put(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// Normal

		coalescedPipe.put("test1");

		Assert.assertEquals(1, coalescedPipe.pendingCount());
		Assert.assertEquals(0, coalescedPipe.coalescedCount());

		coalescedPipe.put("test2");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(0, coalescedPipe.coalescedCount());

		// Coalesce

		coalescedPipe.put("test1");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(1, coalescedPipe.coalescedCount());

		coalescedPipe.put("test2");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(2, coalescedPipe.coalescedCount());

		// With comparator

		coalescedPipe = new CoalescedPipe<String>(
			new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.length() - o2.length();
				}

			});

		// Null

		try {
			coalescedPipe.put(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		// Normal

		coalescedPipe.put("a");

		Assert.assertEquals(1, coalescedPipe.pendingCount());
		Assert.assertEquals(0, coalescedPipe.coalescedCount());

		coalescedPipe.put("ab");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(0, coalescedPipe.coalescedCount());

		// Coalesce

		coalescedPipe.put("c");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(1, coalescedPipe.coalescedCount());

		coalescedPipe.put("cd");

		Assert.assertEquals(2, coalescedPipe.pendingCount());
		Assert.assertEquals(2, coalescedPipe.coalescedCount());
	}

	@Test
	public void testTakeSnapshot() throws InterruptedException {
		CoalescedPipe<String> coalescedPipe = new CoalescedPipe<>();

		Object[] snapShot = coalescedPipe.takeSnapshot();

		Assert.assertEquals(Arrays.toString(snapShot), 0, snapShot.length);

		coalescedPipe.put("test1");

		snapShot = coalescedPipe.takeSnapshot();

		Assert.assertEquals(Arrays.toString(snapShot), 1, snapShot.length);
		Assert.assertEquals("test1", snapShot[0]);

		coalescedPipe.put("test2");

		snapShot = coalescedPipe.takeSnapshot();

		Assert.assertEquals(Arrays.toString(snapShot), 2, snapShot.length);
		Assert.assertEquals("test1", snapShot[0]);
		Assert.assertEquals("test2", snapShot[1]);
	}

}