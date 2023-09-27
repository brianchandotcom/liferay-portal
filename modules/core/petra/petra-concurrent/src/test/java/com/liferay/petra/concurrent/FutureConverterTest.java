/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.concurrent;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FutureConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testCancelInner() throws Exception {
		Future<Object> future = new NopFutureConverter(_futureTask);

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		_futureTask.cancel(true);

		Assert.assertTrue(_futureTask.isCancelled());
		Assert.assertTrue(_futureTask.isDone());
		Assert.assertTrue(future.isCancelled());
		Assert.assertTrue(future.isDone());

		try {
			future.get();

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}
	}

	@Test
	public void testCancelOutter() throws Exception {
		Future<Object> future = new NopFutureConverter(_futureTask);

		Assert.assertFalse(future.isCancelled());
		Assert.assertFalse(future.isDone());

		future.cancel(true);

		Assert.assertTrue(_futureTask.isCancelled());
		Assert.assertTrue(_futureTask.isDone());
		Assert.assertTrue(future.isCancelled());
		Assert.assertTrue(future.isDone());

		try {
			future.get();

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}
	}

	@Test
	public void testCovertCausedExecutionException() throws Exception {
		_futureTask.run();

		final Exception exception = new Exception();

		Future<Object> future = new FutureConverter<Object, Object>(
			_futureTask) {

			@Override
			protected Object convert(Object v) throws Exception {
				throw exception;
			}

		};

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@Test
	public void testCovertResult() throws Exception {
		Object result = new Object();

		FutureTask<Object> futureTask = new FutureTask<Object>(
			new Runnable() {

				@Override
				public void run() {
				}

			},
			result);

		futureTask.run();

		Future<Object> future = new NopFutureConverter(futureTask);

		Assert.assertSame(result, future.get());
		Assert.assertSame(result, future.get(1, TimeUnit.SECONDS));
	}

	@Test
	public void testExecutionException() throws Exception {
		final Exception exception = new Exception();

		FutureTask<Object> futureTask = new FutureTask<Object>(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					throw exception;
				}

			});

		futureTask.run();

		Future<Object> future = new NopFutureConverter(futureTask);

		try {
			future.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@Test(timeout = 10000)
	public void testInterruptionException() throws Exception {
		Future<Object> future = new NopFutureConverter(_futureTask);

		Thread currentThread = Thread.currentThread();

		currentThread.interrupt();

		try {
			future.get();

			Assert.fail();
		}
		catch (InterruptedException interruptedException) {
		}

		Assert.assertFalse(currentThread.isInterrupted());

		currentThread.interrupt();

		try {
			future.get(1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (InterruptedException interruptedException) {
		}

		Assert.assertFalse(currentThread.isInterrupted());
	}

	@Test
	public void testTimeoutException() throws Exception {
		Future<Object> future = new NopFutureConverter(_futureTask);

		try {
			future.get(1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException timeoutException) {
		}
	}

	private final FutureTask<Object> _futureTask = new FutureTask<Object>(
		new Callable<Object>() {

			@Override
			public Object call() {
				return null;
			}

		});

	private static class NopFutureConverter
		extends FutureConverter<Object, Object> {

		public NopFutureConverter(Future<Object> future) {
			super(future);
		}

		@Override
		protected Object convert(Object object) {
			return object;
		}

	}

}