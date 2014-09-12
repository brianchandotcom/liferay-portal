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

package com.liferay.portal.kernel.process;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncFilterInputStream;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class BaseOutputProcessorTestCase {

	public void testFailToRead(OutputProcessor<?, ?> outputProcessor) {
		final IOException ioException = new IOException("Unable to read");

		InputStream inputStream = new UnsyncFilterInputStream(
			new UnsyncByteArrayInputStream(new byte[0])) {

				@Override
				public int read() throws IOException {
					throw ioException;
				}

				@Override
				public int read(byte[] bytes) throws IOException {
					throw ioException;
				}

				@Override
				public int read(byte[] bytes, int offset, int length)
					throws IOException {

					throw ioException;
				}

			};

		try {
			outputProcessor.processStdErr(inputStream);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertSame(ioException, pe.getCause());
		}

		try {
			outputProcessor.processStdOut(inputStream);

			Assert.fail();
		}
		catch (ProcessException pe) {
			Assert.assertSame(ioException, pe.getCause());
		}

		inputStream = new UnsyncFilterInputStream(
			new UnsyncByteArrayInputStream(new byte[0])) {

				@Override
				public void close() throws IOException {
					throw ioException;
				}

			};

		try {
			invokeProcessStdErr(outputProcessor, inputStream);

			Assert.fail();
		}
		catch (Exception e) {
			Throwable throwable = e.getCause();

			Assert.assertSame(ioException, throwable.getCause());
		}

		try {
			invokeProcessStdOut(outputProcessor, inputStream);

			Assert.fail();
		}
		catch (Exception e) {
			Throwable throwable = e.getCause();

			Assert.assertSame(ioException, throwable.getCause());
		}
	}

	protected static <T> T invokeProcessStdErr(
		OutputProcessor<T, ?> outputProcessor, InputStream inputStream) {

		return ReflectionTestUtil.invokeBridge(
			outputProcessor, "processStdErr",
			new Class<?>[] {InputStream.class}, inputStream);
	}

	protected static <T> T invokeProcessStdOut(
		OutputProcessor<?, T> outputProcessor, InputStream inputStream) {

		return ReflectionTestUtil.invokeBridge(
			outputProcessor, "processStdOut",
			new Class<?>[] {InputStream.class}, inputStream);
	}

}