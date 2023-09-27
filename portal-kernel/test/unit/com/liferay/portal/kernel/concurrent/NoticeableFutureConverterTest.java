/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class NoticeableFutureConverterTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCancelInner() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		Assert.assertFalse(noticeableFutureConverter.isCancelled());
		Assert.assertFalse(noticeableFutureConverter.isDone());

		_defaultNoticeableFuture.cancel(true);

		Assert.assertTrue(_defaultNoticeableFuture.isCancelled());
		Assert.assertTrue(_defaultNoticeableFuture.isDone());
		Assert.assertTrue(noticeableFutureConverter.isCancelled());
		Assert.assertTrue(noticeableFutureConverter.isDone());

		try {
			noticeableFutureConverter.get();

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}

		try {
			noticeableFutureConverter.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}
	}

	@Test
	public void testCancelOutter() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		Assert.assertFalse(noticeableFutureConverter.isCancelled());
		Assert.assertFalse(noticeableFutureConverter.isDone());

		noticeableFutureConverter.cancel(true);

		Assert.assertTrue(_defaultNoticeableFuture.isCancelled());
		Assert.assertTrue(_defaultNoticeableFuture.isDone());
		Assert.assertTrue(noticeableFutureConverter.isCancelled());
		Assert.assertTrue(noticeableFutureConverter.isDone());

		try {
			noticeableFutureConverter.get();

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}

		try {
			noticeableFutureConverter.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (CancellationException cancellationException) {
		}
	}

	@Test
	public void testCovertCausedExecutionException() throws Exception {
		final Exception exception = new Exception();

		NoticeableFuture<Object> noticeableFutureConverter =
			new NoticeableFutureConverter<Object, Object>(
				_defaultNoticeableFuture) {

				@Override
				protected Object convert(Object v) throws Exception {
					throw exception;
				}

			};

		TestFutureListener<Object> testFutureListener =
			new TestFutureListener<>();

		Assert.assertTrue(
			noticeableFutureConverter.addFutureListener(testFutureListener));

		_defaultNoticeableFuture.set(new Object());

		Assert.assertEquals(1, testFutureListener.getCount());
		Assert.assertSame(
			ReflectionTestUtil.getFieldValue(
				noticeableFutureConverter, "_defaultNoticeableFuture"),
			testFutureListener.getFuture());

		try {
			noticeableFutureConverter.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}

		try {
			noticeableFutureConverter.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@Test
	public void testCovertResult() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		Object result = new Object();

		_defaultNoticeableFuture.set(result);

		Assert.assertSame(result, noticeableFutureConverter.get());
		Assert.assertSame(
			result, noticeableFutureConverter.get(1, TimeUnit.SECONDS));
	}

	@Test
	public void testExecutionException() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		TestFutureListener<Object> testFutureListener =
			new TestFutureListener<>();

		Assert.assertTrue(
			noticeableFutureConverter.addFutureListener(testFutureListener));

		Exception exception = new Exception();

		_defaultNoticeableFuture.setException(exception);

		Assert.assertEquals(1, testFutureListener.getCount());
		Assert.assertSame(
			ReflectionTestUtil.getFieldValue(
				noticeableFutureConverter, "_defaultNoticeableFuture"),
			testFutureListener.getFuture());

		try {
			noticeableFutureConverter.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}

		try {
			noticeableFutureConverter.get(1, TimeUnit.SECONDS);

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}
	}

	@Test
	public void testFutureListenerRegistration() {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		TestFutureListener<Object> testFutureListener =
			new TestFutureListener<>();

		Assert.assertTrue(
			noticeableFutureConverter.addFutureListener(testFutureListener));
		Assert.assertFalse(
			noticeableFutureConverter.addFutureListener(testFutureListener));
		Assert.assertTrue(
			noticeableFutureConverter.removeFutureListener(testFutureListener));
		Assert.assertFalse(
			noticeableFutureConverter.removeFutureListener(testFutureListener));
	}

	@Test(timeout = 10000)
	public void testInterruptionException() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		Thread currentThread = Thread.currentThread();

		currentThread.interrupt();

		try {
			noticeableFutureConverter.get();

			Assert.fail();
		}
		catch (InterruptedException interruptedException) {
		}

		Assert.assertFalse(currentThread.isInterrupted());

		currentThread.interrupt();

		try {
			noticeableFutureConverter.get(1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (InterruptedException interruptedException) {
		}

		Assert.assertFalse(currentThread.isInterrupted());
	}

	@Test
	public void testTimeoutException() throws Exception {
		NoticeableFuture<Object> noticeableFutureConverter =
			new NopNoticeableFutureConverter(_defaultNoticeableFuture);

		try {
			noticeableFutureConverter.get(1, TimeUnit.MILLISECONDS);

			Assert.fail();
		}
		catch (TimeoutException timeoutException) {
		}
	}

	private final DefaultNoticeableFuture<Object> _defaultNoticeableFuture =
		new DefaultNoticeableFuture<>();

	private static class NopNoticeableFutureConverter
		extends NoticeableFutureConverter<Object, Object> {

		public NopNoticeableFutureConverter(
			NoticeableFuture<Object> noticeableFuture) {

			super(noticeableFuture);
		}

		@Override
		protected Object convert(Object object) {
			return object;
		}

	}

}