/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Future listener is null", nullPointerException.getMessage());
		}

		try {
			_defaultNoticeableFuture.removeFutureListener(null);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
			Assert.assertEquals(
				"Future listener is null", nullPointerException.getMessage());
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
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
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

		_defaultNoticeableFuture.set(new Object());

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