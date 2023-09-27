/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class UnsyncFilterInputStreamTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testAvailable() throws IOException {
		AtomicBoolean availableCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public int available() {
						availableCalled.set(true);

						return -1;
					}

				});

		Assert.assertEquals(-1, unsyncFilterInputStream.available());

		Assert.assertTrue(availableCalled.get());
	}

	@Test
	public void testClose() throws IOException {
		AtomicBoolean closeCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public void close() {
						closeCalled.set(true);
					}

				});

		unsyncFilterInputStream.close();

		Assert.assertTrue(closeCalled.get());
	}

	@Test
	public void testMark() {
		int expectedReadLimit = 1;

		AtomicBoolean markCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public void mark(int readLimit) {
						Assert.assertEquals(expectedReadLimit, readLimit);

						markCalled.set(true);
					}

				});

		unsyncFilterInputStream.mark(expectedReadLimit);

		Assert.assertTrue(markCalled.get());
	}

	@Test
	public void testMarkSupported() {
		AtomicBoolean markCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public boolean markSupported() {
						markCalled.set(true);

						return true;
					}

				});

		Assert.assertTrue(unsyncFilterInputStream.markSupported());

		Assert.assertTrue(markCalled.get());
	}

	@Test
	public void testRead() throws IOException {
		AtomicBoolean readCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public int read() {
						readCalled.set(true);

						return -1;
					}

				});

		Assert.assertEquals(-1, unsyncFilterInputStream.read());

		Assert.assertTrue(readCalled.get());
	}

	@Test
	public void testReadBlock() throws IOException {
		byte[] expectedBytes = new byte[1];

		AtomicBoolean readCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public int read(byte[] bytes, int offset, int length) {
						Assert.assertSame(expectedBytes, bytes);
						Assert.assertEquals(0, offset);
						Assert.assertSame(1, length);

						readCalled.set(true);

						return -1;
					}

				});

		Assert.assertEquals(-1, unsyncFilterInputStream.read(expectedBytes));

		Assert.assertTrue(readCalled.get());
	}

	@Test
	public void testReset() throws IOException {
		AtomicBoolean resetCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public void reset() {
						resetCalled.set(true);
					}

				});

		unsyncFilterInputStream.reset();

		Assert.assertTrue(resetCalled.get());
	}

	@Test
	public void testSkip() throws IOException {
		long expectedSkip = 1;

		AtomicBoolean skipCalled = new AtomicBoolean();

		UnsyncFilterInputStream unsyncFilterInputStream =
			new UnsyncFilterInputStream(
				new TestInputStream() {

					@Override
					public long skip(long skip) {
						Assert.assertEquals(expectedSkip, skip);

						skipCalled.set(true);

						return 0;
					}

				});

		Assert.assertEquals(0, unsyncFilterInputStream.skip(expectedSkip));

		Assert.assertTrue(skipCalled.get());
	}

	private abstract static class TestInputStream extends InputStream {

		@Override
		public int available() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void close() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void mark(int readLimit) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean markSupported() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int read() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int read(byte[] bytes) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int read(byte[] bytes, int offset, int length) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void reset() {
			throw new UnsupportedOperationException();
		}

		@Override
		public long skip(long skip) {
			throw new UnsupportedOperationException();
		}

	}

}