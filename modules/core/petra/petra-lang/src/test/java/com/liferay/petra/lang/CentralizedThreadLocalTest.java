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

package com.liferay.petra.lang;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CentralizedThreadLocalTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCopy() {

		// No copy

		Object obj = new Object();

		CentralizedThreadLocal<Object> centralizedThreadLocal =
			new CentralizedThreadLocal<>(false);

		centralizedThreadLocal.set(obj);

		Map<CentralizedThreadLocal<?>, Object> longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Map<CentralizedThreadLocal<?>, Object> shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		centralizedThreadLocal.remove();

		CentralizedThreadLocal.setThreadLocals(
			longLivedThreadLocals, shortLivedThreadLocals);

		Assert.assertNull(centralizedThreadLocal.get());

		centralizedThreadLocal.remove();

		// Explicit copy

		centralizedThreadLocal = new CentralizedThreadLocal<>(
			null, null, Function.identity(), false);

		centralizedThreadLocal.set(obj);

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		centralizedThreadLocal.remove();

		CentralizedThreadLocal.setThreadLocals(
			longLivedThreadLocals, shortLivedThreadLocals);

		Assert.assertSame(obj, centralizedThreadLocal.get());

		centralizedThreadLocal.remove();

		// Default copy

		String testString = "test";

		centralizedThreadLocal = new CentralizedThreadLocal<>(false);

		centralizedThreadLocal.set(testString);

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		centralizedThreadLocal.remove();

		CentralizedThreadLocal.setThreadLocals(
			longLivedThreadLocals, shortLivedThreadLocals);

		Assert.assertSame(testString, centralizedThreadLocal.get());

		centralizedThreadLocal.remove();

		// Null copy

		centralizedThreadLocal = new CentralizedThreadLocal<>(false);

		centralizedThreadLocal.set(null);

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		centralizedThreadLocal.remove();

		CentralizedThreadLocal.setThreadLocals(
			longLivedThreadLocals, shortLivedThreadLocals);

		Assert.assertNull(centralizedThreadLocal.get());

		centralizedThreadLocal.remove();
	}

	@Test
	public void testEquals() {
		CentralizedThreadLocal<?> centralizedThreadLocal1 =
			new CentralizedThreadLocal<>(false);

		Assert.assertFalse(centralizedThreadLocal1.equals(new Object()));
		Assert.assertTrue(
			centralizedThreadLocal1.equals(centralizedThreadLocal1));

		CentralizedThreadLocal<?> centralizedThreadLocal2 =
			new CentralizedThreadLocal<>(false);

		Assert.assertFalse(
			centralizedThreadLocal1.equals(centralizedThreadLocal2));
	}

	@Test
	public void testHashCode() {
		AtomicInteger longLivedNextHasCode = ReflectionTestUtil.getFieldValue(
			CentralizedThreadLocal.class, "_longLivedNextHasCode");
		AtomicInteger shortLivedNextHasCode = ReflectionTestUtil.getFieldValue(
			CentralizedThreadLocal.class, "_shortLivedNextHasCode");

		Random random = new Random();

		int longLivedHashCode = random.nextInt();
		int shortLivedHashCode = random.nextInt();

		longLivedNextHasCode.set(longLivedHashCode);
		shortLivedNextHasCode.set(shortLivedHashCode);

		CentralizedThreadLocal<?> longLivedCentralizedThreadLocal =
			new CentralizedThreadLocal<>(false);
		CentralizedThreadLocal<?> shortLivedCentralizedThreadLocal =
			new CentralizedThreadLocal<>(true);

		Assert.assertEquals(
			longLivedHashCode, longLivedCentralizedThreadLocal.hashCode());
		Assert.assertEquals(
			shortLivedHashCode, shortLivedCentralizedThreadLocal.hashCode());

		longLivedCentralizedThreadLocal = new CentralizedThreadLocal<>(false);
		shortLivedCentralizedThreadLocal = new CentralizedThreadLocal<>(true);

		int hashIncrement = ReflectionTestUtil.getFieldValue(
			CentralizedThreadLocal.class, "_HASH_INCREMENT");

		Assert.assertEquals(
			longLivedHashCode + hashIncrement,
			longLivedCentralizedThreadLocal.hashCode());
		Assert.assertEquals(
			shortLivedHashCode + hashIncrement,
			shortLivedCentralizedThreadLocal.hashCode());
		Assert.assertEquals(
			longLivedHashCode + hashIncrement * 2, longLivedNextHasCode.get());
		Assert.assertEquals(
			shortLivedHashCode + hashIncrement * 2,
			shortLivedNextHasCode.get());
	}

	@Test
	public void testInitialValue() {

		// By override

		Object obj = new Object();

		CentralizedThreadLocal<?> centralizedThreadLocal =
			new CentralizedThreadLocal<Object>(false) {

				@Override
				protected Object initialValue() {
					return obj;
				}

			};

		Assert.assertSame(obj, centralizedThreadLocal.get());

		centralizedThreadLocal.remove();

		// By Supplier

		centralizedThreadLocal = new CentralizedThreadLocal<>(null, () -> obj);

		Assert.assertSame(obj, centralizedThreadLocal.get());

		centralizedThreadLocal.remove();

		// By null Supplier

		centralizedThreadLocal = new CentralizedThreadLocal<>(null, null);

		Assert.assertNull(centralizedThreadLocal.get());

		centralizedThreadLocal.remove();
	}

	@Test
	public void testTheadLocalManagement() {

		// Initial clean up

		CentralizedThreadLocal.clearLongLivedThreadLocals();
		CentralizedThreadLocal.clearShortLivedThreadLocals();

		// Lazy registration

		CentralizedThreadLocal<String> longLiveCentralizedThreadLocal =
			new CentralizedThreadLocal<>(false);

		CentralizedThreadLocal<String> shortLivedCentralizedThreadLocal =
			new CentralizedThreadLocal<>(true);

		Map<CentralizedThreadLocal<?>, Object> longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Assert.assertTrue(
			longLivedThreadLocals.toString(), longLivedThreadLocals.isEmpty());

		Map<CentralizedThreadLocal<?>, Object> shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		Assert.assertTrue(
			shortLivedThreadLocals.toString(),
			shortLivedThreadLocals.isEmpty());

		// Trigger registration

		longLiveCentralizedThreadLocal.set("longLive");

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Assert.assertEquals(
			"longLive",
			longLivedThreadLocals.get(longLiveCentralizedThreadLocal));

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		Assert.assertTrue(
			shortLivedThreadLocals.toString(),
			shortLivedThreadLocals.isEmpty());

		shortLivedCentralizedThreadLocal.set("shortLive");

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Assert.assertEquals(
			"longLive",
			longLivedThreadLocals.get(longLiveCentralizedThreadLocal));

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		Assert.assertEquals(
			"shortLive",
			shortLivedThreadLocals.get(shortLivedCentralizedThreadLocal));

		// Clean up

		CentralizedThreadLocal.clearLongLivedThreadLocals();
		CentralizedThreadLocal.clearShortLivedThreadLocals();

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Assert.assertTrue(
			longLivedThreadLocals.toString(), longLivedThreadLocals.isEmpty());

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		Assert.assertTrue(
			shortLivedThreadLocals.toString(),
			shortLivedThreadLocals.isEmpty());

		// Set threadlocals

		CentralizedThreadLocal.setThreadLocals(
			Collections.singletonMap(
				longLiveCentralizedThreadLocal, "longLive"),
			Collections.singletonMap(
				shortLivedCentralizedThreadLocal, "shortLive"));

		longLivedThreadLocals =
			CentralizedThreadLocal.getLongLivedThreadLocals();

		Assert.assertEquals(
			"longLive",
			longLivedThreadLocals.get(longLiveCentralizedThreadLocal));

		shortLivedThreadLocals =
			CentralizedThreadLocal.getShortLivedThreadLocals();

		Assert.assertEquals(
			"shortLive",
			shortLivedThreadLocals.get(shortLivedCentralizedThreadLocal));

		// Clean up

		CentralizedThreadLocal.clearLongLivedThreadLocals();
		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	@Test
	public void testThreadLocalMap() throws InterruptedException {

		// Auto expanding with hashcode confliction

		AtomicInteger shortLivedNextHasCode = ReflectionTestUtil.getFieldValue(
			CentralizedThreadLocal.class, "_shortLivedNextHasCode");

		Random random = new Random();

		int shortLivedHashCode = random.nextInt();

		for (int i = 0; i < 17; i++) {
			shortLivedNextHasCode.set(shortLivedHashCode);

			CentralizedThreadLocal<String> centralizedThreadLocal =
				new CentralizedThreadLocal<>(true);

			centralizedThreadLocal.set(String.valueOf(i));
		}

		ThreadLocal<Object> shortLivedThreadLocals =
			ReflectionTestUtil.getFieldValue(
				CentralizedThreadLocal.class, "_shortLivedThreadLocals");

		Object threadLocalMap = shortLivedThreadLocals.get();

		Object[] table = ReflectionTestUtil.getFieldValue(
			threadLocalMap, "_table");

		Assert.assertEquals(Arrays.toString(table), 32, table.length);

		Assert.assertEquals(
			Integer.valueOf(32 * 2 / 3),
			ReflectionTestUtil.getFieldValue(threadLocalMap, "_threshold"));

		CentralizedThreadLocal.clearShortLivedThreadLocals();

		// Auto expanding upper threshold

		int newCapacity = 1 << 31;

		ReflectionTestUtil.invoke(
			threadLocalMap, "expand", new Class<?>[] {int.class}, newCapacity);

		Assert.assertEquals(
			Integer.valueOf(Integer.MAX_VALUE),
			ReflectionTestUtil.getFieldValue(threadLocalMap, "_threshold"));

		// Hash code confliction set/get/remove

		shortLivedNextHasCode.set(shortLivedHashCode);

		CentralizedThreadLocal<String> centralizedThreadLocal1 =
			new CentralizedThreadLocal<>(true);

		shortLivedNextHasCode.set(shortLivedHashCode);

		CentralizedThreadLocal<String> centralizedThreadLocal2 =
			new CentralizedThreadLocal<>(true);

		Assert.assertEquals(
			centralizedThreadLocal1.hashCode(),
			centralizedThreadLocal2.hashCode());

		shortLivedNextHasCode.set(shortLivedHashCode);

		CentralizedThreadLocal<String> centralizedThreadLocal3 =
			new CentralizedThreadLocal<>(true);

		Assert.assertEquals(
			centralizedThreadLocal2.hashCode(),
			centralizedThreadLocal3.hashCode());

		centralizedThreadLocal1.set("test1");
		centralizedThreadLocal2.set("test2");

		Assert.assertNull(centralizedThreadLocal3.get());
		Assert.assertEquals("test2", centralizedThreadLocal2.get());
		Assert.assertEquals("test1", centralizedThreadLocal1.get());

		centralizedThreadLocal2.remove();

		Assert.assertEquals("test1", centralizedThreadLocal1.get());
		Assert.assertNull(centralizedThreadLocal2.get());

		centralizedThreadLocal1.remove();

		Assert.assertNull(centralizedThreadLocal1.get());
		Assert.assertNull(centralizedThreadLocal2.get());

		// Empty remove

		CentralizedThreadLocal<String> centralizedThreadLocal =
			new CentralizedThreadLocal<>(true);

		centralizedThreadLocal.remove();
	}

	@Test
	public void testThreadSeparation() throws Exception {
		CentralizedThreadLocal<String> centralizedThreadLocal =
			new CentralizedThreadLocal<>(false);

		FutureTask<?> poisonFutureTask = new FutureTask<>(
			() -> {
				return null;
			});

		BlockingQueue<FutureTask<?>> blockingQueue = new SynchronousQueue<>();

		FutureTask<Void> threadFutureTask = new FutureTask<>(
			() -> {
				FutureTask<?> futureTask = null;

				while ((futureTask =
							blockingQueue.take()) != poisonFutureTask) {

					futureTask.run();
				}

				return null;
			});

		Thread thread = new Thread(threadFutureTask, "Test Thread");

		thread.start();

		// Clean get

		Assert.assertNull(centralizedThreadLocal.get());

		FutureTask<String> getFutureTask = new FutureTask<>(
			centralizedThreadLocal::get);

		blockingQueue.put(getFutureTask);

		Assert.assertNull(getFutureTask.get());

		// Set on current thread

		centralizedThreadLocal.set("test1");

		Assert.assertEquals("test1", centralizedThreadLocal.get());

		getFutureTask = new FutureTask<>(centralizedThreadLocal::get);

		blockingQueue.put(getFutureTask);

		Assert.assertNull(getFutureTask.get());

		// Set on test thread

		FutureTask<?> setFutureTask = new FutureTask<>(
			() -> {
				centralizedThreadLocal.set("test2");

				return null;
			});

		blockingQueue.put(setFutureTask);

		setFutureTask.get();

		Assert.assertEquals("test1", centralizedThreadLocal.get());

		getFutureTask = new FutureTask<>(centralizedThreadLocal::get);

		blockingQueue.put(getFutureTask);

		Assert.assertEquals("test2", getFutureTask.get());

		// Remove on current thread

		centralizedThreadLocal.remove();

		Assert.assertNull(centralizedThreadLocal.get());

		getFutureTask = new FutureTask<>(centralizedThreadLocal::get);

		blockingQueue.put(getFutureTask);

		Assert.assertEquals("test2", getFutureTask.get());

		// Remove on test thread

		FutureTask<?> removeFutureTask = new FutureTask<>(
			() -> {
				centralizedThreadLocal.remove();

				return null;
			});

		blockingQueue.put(removeFutureTask);

		removeFutureTask.get();

		Assert.assertNull(centralizedThreadLocal.get());

		getFutureTask = new FutureTask<>(centralizedThreadLocal::get);

		blockingQueue.put(getFutureTask);

		Assert.assertNull(getFutureTask.get());

		// Shutdown test thread

		blockingQueue.put(poisonFutureTask);

		threadFutureTask.get();

		// Clean up

		centralizedThreadLocal.remove();
	}

	@Test
	public void testToString() {
		CentralizedThreadLocal<?> centralizedThreadLocal =
			new CentralizedThreadLocal<>("test");

		Assert.assertEquals("test", centralizedThreadLocal.toString());

		centralizedThreadLocal = new CentralizedThreadLocal<>(null);

		Assert.assertEquals(
			CentralizedThreadLocal.class.getName() + "@" +
				Integer.toHexString(centralizedThreadLocal.hashCode()),
			centralizedThreadLocal.toString());
	}

}