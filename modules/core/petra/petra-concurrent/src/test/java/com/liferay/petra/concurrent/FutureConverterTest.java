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

package com.liferay.petra.concurrent;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FutureConverterTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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
		catch (CancellationException ce) {
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException ce) {
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
		catch (CancellationException ce) {
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException ce) {
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
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
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
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}

		try {
			future.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
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
		catch (InterruptedException ie) {
		}

		Assert.assertFalse(currentThread.isInterrupted());

		currentThread.interrupt();

		try {
			future.get(1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (InterruptedException ie) {
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
		catch (TimeoutException te) {
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