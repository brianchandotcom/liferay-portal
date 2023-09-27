/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncFilterInputStream;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseOutputProcessorTestCase {

	public void testFailToRead(OutputProcessor<?, ?> outputProcessor) {
		final IOException ioException = new IOException("Unable to read");

		InputStream inputStream = new UnsyncFilterInputStream(
			new UnsyncByteArrayInputStream(new byte[0])) {

			@Override
			public int read() throws IOException {
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
		catch (ProcessException processException) {
			Assert.assertSame(ioException, processException.getCause());
		}

		try {
			outputProcessor.processStdOut(inputStream);

			Assert.fail();
		}
		catch (ProcessException processException) {
			Assert.assertSame(ioException, processException.getCause());
		}

		inputStream = new UnsyncFilterInputStream(
			new UnsyncByteArrayInputStream(new byte[0])) {

			@Override
			public void close() throws IOException {
				throw ioException;
			}

		};

		try {
			outputProcessor.processStdErr(inputStream);

			Assert.fail();
		}
		catch (ProcessException processException) {
			Assert.assertSame(ioException, processException.getCause());
		}

		try {
			outputProcessor.processStdOut(inputStream);

			Assert.fail();
		}
		catch (ProcessException processException) {
			Assert.assertSame(ioException, processException.getCause());
		}
	}

}