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
			_defaultNoticeableFuture.addFutureListener(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Future listener is null", npe.getMessage());
		}

		try {
			_defaultNoticeableFuture.removeFutureListener(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Future listener is null", npe.getMessage());
		}

		Object futureListeners = ReflectionTestUtil.getFieldValue(
			_defaultNoticeableFuture, "_futureListeners");

		Assert.assertEquals(0, futureListeners.hashCode());

		TestFutureListener<Object> testFutureListener1 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener1));
		Assert.assertEquals(
			testFutureListener1.hashCode(), futureListeners.hashCode());

		TestFutureListener<Object> testFutureListener2 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener2));
		Assert.assertEquals(
			testFutureListener1.hashCode() + testFutureListener2.hashCode(),
			futureListeners.hashCode());

		Assert.assertFalse(
			_defaultNoticeableFuture.addFutureListener(testFutureListener1));
		Assert.assertFalse(
			_defaultNoticeableFuture.addFutureListener(testFutureListener2));
		Assert.assertTrue(
			_defaultNoticeableFuture.removeFutureListener(testFutureListener1));
		Assert.assertFalse(
			_defaultNoticeableFuture.removeFutureListener(testFutureListener1));
		Assert.assertTrue(
			_defaultNoticeableFuture.removeFutureListener(testFutureListener2));
		Assert.assertFalse(
			_defaultNoticeableFuture.removeFutureListener(testFutureListener2));
	}

	@Test
	public void testCompleteWithException() throws InterruptedException {
		TestFutureListener<Object> testFutureListener1 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener1));

		Exception exception = new Exception();

		_defaultNoticeableFuture.setException(exception);

		Assert.assertSame(
			_defaultNoticeableFuture, testFutureListener1.getFuture());

		try {
			_defaultNoticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}

		TestFutureListener<Object> testFutureListener2 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener2));
		Assert.assertSame(
			_defaultNoticeableFuture, testFutureListener2.getFuture());
	}

	@Test
	public void testCompleteWithRaceCondition() {
		TestFutureListener<Object> testFutureListener =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener));

		_defaultNoticeableFuture.done();

		Assert.assertSame(
			_defaultNoticeableFuture, testFutureListener.getFuture());
		Assert.assertEquals(1, testFutureListener.getCount());

		Object result = new Object();

		_defaultNoticeableFuture.set(result);

		Assert.assertEquals(1, testFutureListener.getCount());
	}

	@Test
	public void testCompleteWithResult() throws Exception {
		TestFutureListener<Object> testFutureListener1 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener1));

		Object result = new Object();

		_defaultNoticeableFuture.set(result);

		Assert.assertSame(
			_defaultNoticeableFuture, testFutureListener1.getFuture());
		Assert.assertSame(result, _defaultNoticeableFuture.get());

		TestFutureListener<Object> testFutureListener2 =
			new TestFutureListener<>();

		Assert.assertTrue(
			_defaultNoticeableFuture.addFutureListener(testFutureListener2));
		Assert.assertSame(
			_defaultNoticeableFuture, testFutureListener2.getFuture());
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

	private final DefaultNoticeableFuture<Object> _defaultNoticeableFuture =
		new DefaultNoticeableFuture<>();

}