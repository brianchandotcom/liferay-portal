/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.internal.http;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.net.SocketTimeoutException;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class DSHttpTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAttempts() {
		Assert.assertEquals(1, _getAttempts(-1));
		Assert.assertEquals(1, _getAttempts(0));
		Assert.assertEquals(1, _getAttempts(19999));
		Assert.assertEquals(1, _getAttempts(39999));
		Assert.assertEquals(2, _getAttempts(40000));
		Assert.assertEquals(2, _getAttempts(59999));
		Assert.assertEquals(3, _getAttempts(60000));
		Assert.assertEquals(3, _getAttempts(Integer.MAX_VALUE));
	}

	@Test
	public void testInvokeAsBytesDoesNotRetryOtherIOExceptions() {
		AtomicInteger invocationCount = new AtomicInteger();

		IOException ioException = new IOException("Connection refused");

		DSHttp dsHttp = _createDSHttp(
			null, ioException, Integer.MAX_VALUE, invocationCount);

		try {
			_invokeAsBytes(dsHttp, _getMaxAttempts());

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(ioException, exception);
		}

		Assert.assertEquals(1, invocationCount.get());
	}

	@Test
	public void testInvokeAsBytesDoesNotRetryWithSingleAttempt() {
		AtomicInteger invocationCount = new AtomicInteger();

		IOException ioException = new IOException(
			new SocketTimeoutException("Read timed out"));

		DSHttp dsHttp = _createDSHttp(
			null, ioException, Integer.MAX_VALUE, invocationCount);

		try {
			_invokeAsBytes(dsHttp, 1);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(ioException, exception);
		}

		Assert.assertEquals(1, invocationCount.get());
	}

	@Test
	public void testInvokeAsBytesRetriesSocketTimeouts() throws Exception {
		AtomicInteger invocationCount = new AtomicInteger();

		int maxAttempts = _getMaxAttempts();

		byte[] expectedBytes = "response".getBytes();

		DSHttp dsHttp = _createDSHttp(
			expectedBytes,
			new IOException(new SocketTimeoutException("Read timed out")),
			maxAttempts - 1, invocationCount);

		Assert.assertArrayEquals(
			expectedBytes, _invokeAsBytes(dsHttp, maxAttempts));

		Assert.assertEquals(maxAttempts, invocationCount.get());
	}

	@Test
	public void testInvokeAsBytesThrowsAfterAllAttemptsTimeOut() {
		AtomicInteger invocationCount = new AtomicInteger();

		int maxAttempts = _getMaxAttempts();

		IOException ioException = new IOException(
			new SocketTimeoutException("Read timed out"));

		DSHttp dsHttp = _createDSHttp(
			null, ioException, Integer.MAX_VALUE, invocationCount);

		try {
			_invokeAsBytes(dsHttp, maxAttempts);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(ioException, exception);
		}

		Assert.assertEquals(maxAttempts, invocationCount.get());
	}

	private DSHttp _createDSHttp(
		byte[] bytes, IOException ioException, int ioExceptionCount,
		AtomicInteger invocationCount) {

		DSHttp dsHttp = new DSHttp();

		ReflectionTestUtil.setFieldValue(
			dsHttp, "_http",
			ProxyUtil.newProxyInstance(
				Http.class.getClassLoader(), new Class<?>[] {Http.class},
				(proxy, method, arguments) -> {
					if (!Objects.equals(method.getName(), "URLtoByteArray")) {
						throw new UnsupportedOperationException(
							method.getName());
					}

					if (invocationCount.incrementAndGet() <= ioExceptionCount) {
						throw ioException;
					}

					return bytes;
				}));

		return dsHttp;
	}

	private int _getAttempts(int httpTimeout) {
		return ReflectionTestUtil.invoke(
			new DSHttp(), "_getAttempts", new Class<?>[] {int.class},
			httpTimeout);
	}

	private int _getMaxAttempts() {
		return ReflectionTestUtil.getFieldValue(DSHttp.class, "_MAX_ATTEMPTS");
	}

	private byte[] _invokeAsBytes(DSHttp dsHttp, int attempts)
		throws Exception {

		return ReflectionTestUtil.invoke(
			dsHttp, "_invokeAsBytes",
			new Class<?>[] {Http.Options.class, int.class}, new Http.Options(),
			attempts);
	}

}