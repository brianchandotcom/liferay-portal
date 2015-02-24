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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class DefaultNoticeableFutureTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testAddRemoveFutureListener() {
		try {
			defaultNoticeableFuture.addFutureListener(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Future listener is null", npe.getMessage());
		}

		try {
			defaultNoticeableFuture.removeFutureListener(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Future listener is null", npe.getMessage());
		}

		Object futureListeners = ReflectionTestUtil.getFieldValue(
			defaultNoticeableFuture, "_futureListeners");

		Assert.assertEquals(0, futureListeners.hashCode());

		RecordedFutureListener<Object> recordedFutureListener1 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener1));
		Assert.assertEquals(
			recordedFutureListener1.hashCode(), futureListeners.hashCode());

		RecordedFutureListener<Object> recordedFutureListener2 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener2));
		Assert.assertEquals(
			recordedFutureListener1.hashCode() +
				recordedFutureListener2.hashCode(),
			futureListeners.hashCode());
		Assert.assertFalse(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener1));
		Assert.assertFalse(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener2));
		Assert.assertTrue(
			defaultNoticeableFuture.removeFutureListener(
				recordedFutureListener1));
		Assert.assertFalse(
			defaultNoticeableFuture.removeFutureListener(
				recordedFutureListener1));
		Assert.assertTrue(
			defaultNoticeableFuture.removeFutureListener(
				recordedFutureListener2));
		Assert.assertFalse(
			defaultNoticeableFuture.removeFutureListener(
				recordedFutureListener2));
	}

	@Test
	public void testCompleteWithException() throws InterruptedException {
		RecordedFutureListener<Object> recordedFutureListener1 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener1));

		Exception exception = new Exception();

		defaultNoticeableFuture.setException(exception);

		Assert.assertSame(
			defaultNoticeableFuture, recordedFutureListener1.getFuture());

		try {
			defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}

		RecordedFutureListener<Object> recordedFutureListener2 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener2));
		Assert.assertSame(
			defaultNoticeableFuture, recordedFutureListener2.getFuture());
	}

	@Test
	public void testCompleteWithRaceCondition() {
		RecordedFutureListener<Object> recordedFutureListener =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener));

		defaultNoticeableFuture.done();

		Assert.assertSame(
			defaultNoticeableFuture, recordedFutureListener.getFuture());
		Assert.assertEquals(1, recordedFutureListener.getCount());

		Object result = new Object();

		defaultNoticeableFuture.set(result);

		Assert.assertEquals(1, recordedFutureListener.getCount());
	}

	@Test
	public void testCompleteWithResult() throws Exception {
		RecordedFutureListener<Object> recordedFutureListener1 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener1));

		Object result = new Object();

		defaultNoticeableFuture.set(result);

		Assert.assertSame(
			defaultNoticeableFuture, recordedFutureListener1.getFuture());
		Assert.assertSame(result, defaultNoticeableFuture.get());

		RecordedFutureListener<Object> recordedFutureListener2 =
			new RecordedFutureListener<>();

		Assert.assertTrue(
			defaultNoticeableFuture.addFutureListener(recordedFutureListener2));
		Assert.assertSame(
			defaultNoticeableFuture, recordedFutureListener2.getFuture());
	}

	@Test
	public void testConstructor() throws Exception {
		final AtomicBoolean flag = new AtomicBoolean();

		DefaultNoticeableFuture<?> defaultNoticeableFuture =
			new DefaultNoticeableFuture<Object>(
				new Callable<Object>() {

					@Override
					public Object call() {
						flag.set(true);

						return flag;
					}

				});

		defaultNoticeableFuture.run();

		Assert.assertSame(flag, defaultNoticeableFuture.get());
		Assert.assertTrue(flag.get());

		defaultNoticeableFuture = new DefaultNoticeableFuture<Object>(
			new Runnable() {

				@Override
				public void run() {
					flag.set(false);
				}

			},
			flag);

		defaultNoticeableFuture.run();

		Assert.assertSame(flag, defaultNoticeableFuture.get());
		Assert.assertFalse(flag.get());
	}

	private final DefaultNoticeableFuture<Object> defaultNoticeableFuture =
		new DefaultNoticeableFuture<>();

}