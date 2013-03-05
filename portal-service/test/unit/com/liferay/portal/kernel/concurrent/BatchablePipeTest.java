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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
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
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testBatchPutAndGet() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();

		// Batch same entry should fail

		IncreasableEntry<String, Integer> increasbleEntry1 =
			new IntegerIncreasableEntry("1st", 1);

		Assert.assertTrue(batchablePipe.put(increasbleEntry1));
		Assert.assertTrue(batchablePipe.put(increasbleEntry1));
		Assert.assertEquals(increasbleEntry1, batchablePipe.take());
		Assert.assertEquals(increasbleEntry1, batchablePipe.take());

		// Batch 2 entries

		IncreasableEntry<String, Integer> increasbleEntry2 =
			new IntegerIncreasableEntry("2nd", 1);
		IncreasableEntry<String, Integer> increasbleEntry3 =
			new IntegerIncreasableEntry("2nd", 2);

		Assert.assertTrue(batchablePipe.put(increasbleEntry2));
		Assert.assertFalse(batchablePipe.put(increasbleEntry3));

		IncreasableEntry<String, Integer> resultIncreasbleEntry =
			batchablePipe.take();

		Assert.assertNotNull(resultIncreasbleEntry);
		Assert.assertEquals("2nd", resultIncreasbleEntry.getKey());
		Assert.assertEquals(3, (int)resultIncreasbleEntry.getValue());
		Assert.assertNull(batchablePipe.take());

		// Mix batch

		IncreasableEntry<String, Integer> increasbleEntry4 =
			new IntegerIncreasableEntry("3rd", 1);
		IncreasableEntry<String, Integer> increasbleEntry5 =
			new IntegerIncreasableEntry("4th", 1);
		IncreasableEntry<String, Integer> increasbleEntry6 =
			new IntegerIncreasableEntry("4th", 2);
		IncreasableEntry<String, Integer> increasbleEntry7 =
			new IntegerIncreasableEntry("3rd", 2);
		IncreasableEntry<String, Integer> increasbleEntry8 =
			new IntegerIncreasableEntry("5th", 1);

		Assert.assertTrue(batchablePipe.put(increasbleEntry4));
		Assert.assertTrue(batchablePipe.put(increasbleEntry5));
		Assert.assertFalse(batchablePipe.put(increasbleEntry6));
		Assert.assertFalse(batchablePipe.put(increasbleEntry7));
		Assert.assertTrue(batchablePipe.put(increasbleEntry8));

		resultIncreasbleEntry = batchablePipe.take();

		Assert.assertNotNull(resultIncreasbleEntry);
		Assert.assertEquals("3rd", resultIncreasbleEntry.getKey());
		Assert.assertEquals(3, (int)resultIncreasbleEntry.getValue());

		resultIncreasbleEntry = batchablePipe.take();

		Assert.assertNotNull(resultIncreasbleEntry);
		Assert.assertEquals("4th", resultIncreasbleEntry.getKey());
		Assert.assertEquals(3, (int)resultIncreasbleEntry.getValue());

		resultIncreasbleEntry = batchablePipe.take();

		Assert.assertNotNull(resultIncreasbleEntry);
		Assert.assertEquals("5th", resultIncreasbleEntry.getKey());
		Assert.assertEquals(1, (int)resultIncreasbleEntry.getValue());
		Assert.assertNull(batchablePipe.take());
	}

	@Test
	public void testConcurrent() throws Exception {
		final BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();

		final BlockingQueue<IncreasableEntry<String, Integer>>
			resultBlockingQueue = new LinkedBlockingQueue
				<IncreasableEntry<String, Integer>>();

		ExecutorService putThreadPool = Executors.newFixedThreadPool(5);
		ExecutorService takeThreadPool = Executors.newFixedThreadPool(5);

		Runnable putRunnable = new Runnable() {

			public void run() {
				for (int i = 0; i < 100; i++) {
					batchablePipe.put(
						new IntegerIncreasableEntry(String.valueOf(i % 10), 1));
				}
			}

		};

		Runnable takeRunnable = new Runnable() {

			public void run() {
				while (true) {
					try {
						IncreasableEntry<String, Integer> increasableEntry =
							batchablePipe.take();

						if (increasableEntry != null) {
							if (increasableEntry.getKey().equals("exit")) {
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
			new IntegerIncreasableEntry("exit", -1);

		for (int i = 0; i < 10; i++) {
			batchablePipe.put(poisonIncreasableEntry);
		}

		takeThreadPool.shutdown();
		takeThreadPool.awaitTermination(240, TimeUnit.SECONDS);

		// Do statistics

		Map<String, Integer> verifyMap = new HashMap<String, Integer>();

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

		// Verify batch rate

		Assert.assertTrue(1000 > resultBlockingQueue.size());
	}

	@Test
	public void testCreation() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();

		Assert.assertNull(batchablePipe.take());
		Assert.assertNull(batchablePipe.take());
		Assert.assertNull(batchablePipe.take());
	}

	@Test
	public void testSimplePutAndTake() {
		BatchablePipe<String, Integer> batchablePipe =
			new BatchablePipe<String, Integer>();

		// Put 1st

		IncreasableEntry<String, Integer> increasableEntry1 =
			new IntegerIncreasableEntry("1st", 1);

		Assert.assertTrue(batchablePipe.put(increasableEntry1));

		// Get 1st

		Assert.assertEquals(increasableEntry1, batchablePipe.take());

		// Assure still increasable

		Assert.assertTrue(increasableEntry1.increase(1));

		// Assure get the correct increaed value

		Assert.assertEquals(2, (int)increasableEntry1.getValue());

		// Assure can't increase anymore

		Assert.assertFalse(increasableEntry1.increase(1));

		// Assure value has not changed

		Assert.assertEquals(2, (int)increasableEntry1.getValue());

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

		Assert.assertEquals(increasableEntry2, batchablePipe.take());

		// Get 3rd

		Assert.assertEquals(increasableEntry3, batchablePipe.take());

		// Get 4th

		Assert.assertEquals(increasableEntry4, batchablePipe.take());

		Assert.assertNull(batchablePipe.take());
	}

	private class IntegerIncreasableEntry
		extends IncreasableEntry<String, Integer> {

		public IntegerIncreasableEntry(String key, Integer value) {
			super(key, value);
		}

		@Override
		public Integer doIncrease(Integer originalValue, Integer deltaValue) {
			return originalValue + deltaValue;
		}

	}

}