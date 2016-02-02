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

import com.liferay.portal.kernel.concurrent.BatchablePipe.IncreasableEntryWrapper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class BatchablePipeTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBatchPutAndGet() {
		BatchablePipe<String, Integer> batchablePipe = new BatchablePipe<>();

		// Batch same entry

		IncreasableEntry<String, Integer> increasbleEntry1 =
			new IntegerIncreasableEntry("1st", 1);

		Assert.assertTrue(batchablePipe.put(increasbleEntry1));
		Assert.assertFalse(batchablePipe.put(increasbleEntry1));

		IncreasableEntry<String, Integer> batchedIncreasableEntry =
			batchablePipe.take();

		Assert.assertNotSame(increasbleEntry1, batchedIncreasableEntry);
		Assert.assertEquals(
			increasbleEntry1.getKey(), batchedIncreasableEntry.getKey());
		Assert.assertEquals(2, (int)batchedIncreasableEntry.getValue());
		Assert.assertNull(batchablePipe.take());

		// Batch 2 entries

		IncreasableEntry<String, Integer> increasbleEntry2 =
			new IntegerIncreasableEntry("2nd", 1);
		IncreasableEntry<String, Integer> increasbleEntry3 =
			new IntegerIncreasableEntry("2nd", 2);

		Assert.assertTrue(batchablePipe.put(increasbleEntry2));
		Assert.assertFalse(batchablePipe.put(increasbleEntry3));

		batchedIncreasableEntry = batchablePipe.take();

		Assert.assertNotSame(increasbleEntry2, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry3, batchedIncreasableEntry);
		Assert.assertEquals("2nd", batchedIncreasableEntry.getKey());
		Assert.assertEquals(3, (int)batchedIncreasableEntry.getValue());
		Assert.assertNull(batchablePipe.take());

		// Mix batch

		IncreasableEntry<String, Integer> increasbleEntry4 =
			new IntegerIncreasableEntry("3rd", 1);
		IncreasableEntry<String, Integer> increasbleEntry5 =
			new IntegerIncreasableEntry("4th", 1);
		IncreasableEntry<String, Integer> increasbleEntry6 =
			new IntegerIncreasableEntry("4th", 2);
		IncreasableEntry<String, Integer> increasbleEntry7 =
			new IntegerIncreasableEntry("3rd", 3);
		IncreasableEntry<String, Integer> increasbleEntry8 =
			new IntegerIncreasableEntry("5th", 1);

		Assert.assertTrue(batchablePipe.put(increasbleEntry4));
		Assert.assertTrue(batchablePipe.put(increasbleEntry5));
		Assert.assertFalse(batchablePipe.put(increasbleEntry6));
		Assert.assertFalse(batchablePipe.put(increasbleEntry7));
		Assert.assertTrue(batchablePipe.put(increasbleEntry8));

		batchedIncreasableEntry = batchablePipe.take();

		Assert.assertNotSame(increasbleEntry4, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry5, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry6, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry7, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry8, batchedIncreasableEntry);
		Assert.assertEquals("4th", batchedIncreasableEntry.getKey());
		Assert.assertEquals(3, (int)batchedIncreasableEntry.getValue());

		batchedIncreasableEntry = batchablePipe.take();

		Assert.assertNotSame(increasbleEntry4, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry5, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry6, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry7, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry8, batchedIncreasableEntry);
		Assert.assertEquals("3rd", batchedIncreasableEntry.getKey());
		Assert.assertEquals(4, (int)batchedIncreasableEntry.getValue());

		batchedIncreasableEntry = batchablePipe.take();

		Assert.assertNotSame(increasbleEntry4, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry5, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry6, batchedIncreasableEntry);
		Assert.assertNotSame(increasbleEntry7, batchedIncreasableEntry);
		Assert.assertSame(increasbleEntry8, batchedIncreasableEntry);
		Assert.assertNull(batchablePipe.take());
	}

	@Test
	public void testConcurrent() throws InterruptedException {
		final BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<>();

		final BlockingQueue<IncreasableEntry<String, Integer>>
			resultBlockingQueue = new LinkedBlockingQueue<>();

		ExecutorService putThreadPool = Executors.newFixedThreadPool(5);
		ExecutorService takeThreadPool = Executors.newFixedThreadPool(5);

		Runnable putRunnable = new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					batchablePipe.put(
						new IntegerIncreasableEntry(String.valueOf(i % 10), 1));
				}
			}

		};

		Runnable takeRunnable = new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						IncreasableEntry<String, Integer> increasableEntry =
							batchablePipe.take();

						if (increasableEntry != null) {
							String key = increasableEntry.getKey();

							if (key.equals("exit")) {
								int value = increasableEntry.getValue();

								if (value > 1) {
									Assert.assertTrue(
										batchablePipe.put(
											new IntegerIncreasableEntry(
												"exit", value - 1)));
								}

								return;
							}

							resultBlockingQueue.put(increasableEntry);
						}
					}
					catch (InterruptedException ie) {
					}
				}
			}

		};

		// Submit jobs

		for (int i = 0; i < 10; i++) {
			putThreadPool.submit(putRunnable);
			takeThreadPool.submit(takeRunnable);
		}

		// Wait until put finish

		putThreadPool.shutdown();
		putThreadPool.awaitTermination(240, TimeUnit.SECONDS);

		// Poison take thread pool

		IncreasableEntry<String, Integer> poisonIncreasableEntry =
			new IntegerIncreasableEntry("exit", 10);

		Assert.assertTrue(batchablePipe.put(poisonIncreasableEntry));

		takeThreadPool.shutdown();
		takeThreadPool.awaitTermination(240, TimeUnit.SECONDS);

		// Do statistics

		Map<String, Integer> verifyMap = new HashMap<>();

		for (IncreasableEntry<String, Integer> increasableEntry :
				resultBlockingQueue) {

			String key = increasableEntry.getKey();
			Integer value = increasableEntry.getValue();

			Integer sum = verifyMap.get(key);

			if (sum == null) {
				verifyMap.put(key, value);
			}
			else {
				verifyMap.put(key, sum + value);
			}
		}

		// Verify statistics

		for (int i = 0; i < 10; i++) {
			Integer sum = verifyMap.get(String.valueOf(i));

			Assert.assertEquals(100, (int)sum);
		}
	}

	@Test
	public void testConcurrentPut() {
		BatchablePipe<String, Integer> batchablePipe = new BatchablePipe<>();

		final IncreasableEntry<String, Integer> increasbleEntry1 =
			new IntegerIncreasableEntry("test", 1);
		final IncreasableEntry<String, Integer> increasbleEntry2 =
			new IntegerIncreasableEntry("test", 2);
		IncreasableEntry<String, Integer> increasbleEntry3 =
			new IntegerIncreasableEntry("test", 3);

		ReflectionTestUtil.setFieldValue(
			batchablePipe, "concurrentMap",
			new ConcurrentHashMap
				<String, IncreasableEntryWrapper<String, Integer>>() {

				@Override
				public boolean replace(
					String key,
					IncreasableEntryWrapper<String, Integer> oldValue,
					IncreasableEntryWrapper<String, Integer> newValue) {

					if (oldValue.increasableEntry == increasbleEntry1) {
						put(
							key,
							new IncreasableEntryWrapper<String, Integer>(
								increasbleEntry2));
					}

					return super.replace(key, oldValue, newValue);
				}

			});

		Assert.assertTrue(batchablePipe.put(increasbleEntry1));
		Assert.assertFalse(batchablePipe.put(increasbleEntry3));

		IncreasableEntry<String, Integer> batchedIncreasableEntry =
			batchablePipe.take();

		Assert.assertEquals(5, (int)batchedIncreasableEntry.getValue());
		Assert.assertNull(batchablePipe.take());
	}

	@Test
	public void testCreation() {
		BatchablePipe<String, Integer> batchablePipe = new BatchablePipe<>();

		Assert.assertNull(batchablePipe.take());
		Assert.assertNull(batchablePipe.take());
		Assert.assertNull(batchablePipe.take());
	}

	@Test
	public void testIncreasableEntryWrapper() {
		IncreasableEntry<String, Integer> increasbleEntry1 =
			new IntegerIncreasableEntry("test", 1);
		IncreasableEntry<String, Integer> increasbleEntry2 =
			new IntegerIncreasableEntry("test", 1);

		Assert.assertEquals(
			new IncreasableEntryWrapper<String, Integer>(increasbleEntry1),
			new IncreasableEntryWrapper<String, Integer>(increasbleEntry1));
		Assert.assertNotEquals(
			new IncreasableEntryWrapper<String, Integer>(increasbleEntry1),
			new IncreasableEntryWrapper<String, Integer>(increasbleEntry2));

		IncreasableEntryWrapper<String, Integer> increasableEntryWrapper =
			new IncreasableEntryWrapper<>(increasbleEntry1);

		Assert.assertEquals(
			increasbleEntry1.hashCode(), increasableEntryWrapper.hashCode());
		Assert.assertEquals(
			increasbleEntry1.toString(), increasableEntryWrapper.toString());
	}

	@Test
	public void testSimplePutAndTake() {
		BatchablePipe<String, Integer> batchablePipe = new BatchablePipe<>();

		// Put 1st

		IncreasableEntry<String, Integer> increasableEntry1 =
			new IntegerIncreasableEntry("1st", 1);

		Assert.assertTrue(batchablePipe.put(increasableEntry1));

		// Get 1st

		Assert.assertSame(increasableEntry1, batchablePipe.take());

		// Sequence put

		IncreasableEntry<String, Integer> increasableEntry2 =
			new IntegerIncreasableEntry("2nd", 2);

		Assert.assertTrue(batchablePipe.put(increasableEntry2));

		IncreasableEntry<String, Integer> increasableEntry3 =
			new IntegerIncreasableEntry("3nd", 3);

		Assert.assertTrue(batchablePipe.put(increasableEntry3));

		IncreasableEntry<String, Integer> increasableEntry4 =
			new IntegerIncreasableEntry("4th", 4);

		Assert.assertTrue(batchablePipe.put(increasableEntry4));

		// Sequence take

		// Get 2nd

		Assert.assertSame(increasableEntry2, batchablePipe.take());

		// Get 3rd

		Assert.assertSame(increasableEntry3, batchablePipe.take());

		// Get 4th

		Assert.assertSame(increasableEntry4, batchablePipe.take());
		Assert.assertNull(batchablePipe.take());
	}

	private static class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		@Override
		public IntegerIncreasableEntry increase(Integer deltaValue) {
			return new IntegerIncreasableEntry(key, value + deltaValue);
		}

	}

}