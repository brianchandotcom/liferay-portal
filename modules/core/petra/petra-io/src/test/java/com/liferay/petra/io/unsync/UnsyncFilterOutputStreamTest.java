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

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class UnsyncFilterOutputStreamTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testClose() throws IOException {
		AtomicBoolean closeCalled = new AtomicBoolean();
		AtomicBoolean flushCalled = new AtomicBoolean();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void close() {
						closeCalled.set(true);
					}

					@Override
					public void flush() {
						flushCalled.set(true);
					}

				});

		unsyncFilterOutputStream.close();

		Assert.assertTrue(closeCalled.get());

		Assert.assertTrue(flushCalled.get());
	}

	@Test(expected = NullPointerException.class)
	public void testCloseNull() throws IOException {
		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(null);

		unsyncFilterOutputStream.close();
	}

	@Test
	public void testCloseWithException() throws IOException {
		IOException ioException1 = new IOException();

		AtomicBoolean closeCalled = new AtomicBoolean();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void close() {
						closeCalled.set(true);
					}

					@Override
					public void flush() throws IOException {
						throw ioException1;
					}

				});

		try {
			unsyncFilterOutputStream.close();

			Assert.fail();
		}
		catch (IOException ioException2) {
			Assert.assertSame(ioException1, ioException2);
		}

		Assert.assertTrue(closeCalled.get());
	}

	@Test
	public void testCloseWithTwoExceptions() throws IOException {
		IOException ioException1 = new IOException();
		IOException ioException2 = new IOException();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void close() throws IOException {
						throw ioException2;
					}

					@Override
					public void flush() throws IOException {
						throw ioException1;
					}

				});

		try {
			unsyncFilterOutputStream.close();

			Assert.fail();
		}
		catch (IOException ioException3) {
			Assert.assertSame(ioException1, ioException3);

			Throwable[] throwables = ioException1.getSuppressed();

			Assert.assertEquals(
				Arrays.toString(throwables), 1, throwables.length);
			Assert.assertSame(ioException2, throwables[0]);
		}
	}

	@Test
	public void testFlush() throws IOException {
		AtomicBoolean flushCalled = new AtomicBoolean();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void flush() {
						flushCalled.set(true);
					}

				});

		unsyncFilterOutputStream.flush();

		Assert.assertTrue(flushCalled.get());
	}

	@Test
	public void testWrite() throws IOException {
		int expectedB = 1;

		AtomicBoolean writeCalled = new AtomicBoolean();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void write(int b) {
						Assert.assertEquals(expectedB, b);

						writeCalled.set(true);
					}

				});

		unsyncFilterOutputStream.write(expectedB);

		Assert.assertTrue(writeCalled.get());
	}

	@Test
	public void testWriteBlock() throws IOException {
		byte[] expectedBytes = new byte[1];

		AtomicBoolean writeCalled = new AtomicBoolean();

		UnsyncFilterOutputStream unsyncFilterOutputStream =
			new UnsyncFilterOutputStream(
				new TestOutputStream() {

					@Override
					public void write(byte[] bytes, int offset, int length) {
						Assert.assertSame(expectedBytes, bytes);
						Assert.assertEquals(0, offset);
						Assert.assertEquals(1, length);

						writeCalled.set(true);
					}

				});

		unsyncFilterOutputStream.write(expectedBytes);

		Assert.assertTrue(writeCalled.get());
	}

	private abstract static class TestOutputStream extends OutputStream {

		@Override
		public void close() throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void flush() throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void write(byte[] bytes) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void write(byte[] bytes, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void write(int b) {
			throw new UnsupportedOperationException();
		}

	}

}