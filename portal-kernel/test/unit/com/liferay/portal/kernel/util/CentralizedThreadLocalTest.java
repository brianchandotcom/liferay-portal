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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.lang.CentralizedThreadLocalTest}
 */
@Deprecated
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

		centralizedThreadLocal = new CentralizedThreadLocal<Object>(false) {

			@Override
			protected Object copy(Object value) {
				return value;
			}

		};

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

		// Direct copy

		try {
			ReflectionTestUtil.invoke(
				centralizedThreadLocal, "copy", new Class<?>[] {Object.class},
				testString);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}
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

		ReflectionTestUtil.setFieldValue(
			centralizedThreadLocal2, "_centralizedThreadLocal",
			ReflectionTestUtil.getFieldValue(
				centralizedThreadLocal1, "_centralizedThreadLocal"));

		Assert.assertTrue(
			centralizedThreadLocal1.equals(centralizedThreadLocal2));
	}

	@Test
	public void testHashCode() {
		CentralizedThreadLocal<?> centralizedThreadLocal =
			new CentralizedThreadLocal<>(false);

		com.liferay.petra.lang.CentralizedThreadLocal<?>
			petraCentralizedThreadLocal = ReflectionTestUtil.getFieldValue(
				centralizedThreadLocal, "_centralizedThreadLocal");

		Assert.assertEquals(
			petraCentralizedThreadLocal.hashCode(),
			centralizedThreadLocal.hashCode());
	}

	@Test
	public void testInitialValue() {
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
	}

	@Test
	public void testThreadLocalManagement() {

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
	public void testThreadSeparation() throws Exception {
		CentralizedThreadLocal<String> centralizedThreadLocal =
			new CentralizedThreadLocal<>(false);

		FutureTask<?> poisonFutureTask = new FutureTask<>(() -> null);

		BlockingQueue<FutureTask<?>> blockingQueue = new SynchronousQueue<>();

		FutureTask<Void> threadFutureTask = new FutureTask<>(
			() -> {
				FutureTask<?> futureTask = null;

				while ((futureTask = blockingQueue.take()) !=
							poisonFutureTask) {

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

}