/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.io.unsync;

import java.io.IOException;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public abstract class BaseWriterTestCase {

	@Test
	public void testWriteNullCharArrayChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write((char[])null, 0, 1);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
		}
	}

	@Test
	public void testWriteNullString() throws Exception {
		Writer writer = getWriter();

		try {
			writer.write((String)null, 0, 1);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsLengthChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_CHARS, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsLengthString() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_STRING, 3, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeLengthChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_CHARS, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeLengthString() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_STRING, 0, -1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeOffsetChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_CHARS, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsNegativeOffsetString() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_STRING, -1, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOffsetChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_CHARS, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOffsetString() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_STRING, 4, 1);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOverflowChars() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_CHARS, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteOutOfBoundsOverflowString() throws IOException {
		Writer writer = getWriter();

		try {
			writer.write(_STRING, 1, Integer.MAX_VALUE);

			Assert.fail();
		}
		catch (IndexOutOfBoundsException indexOutOfBoundsException) {
		}
	}

	@Test
	public void testWriteZeroLengthChars() throws IOException {
		Writer writer = getWriter();

		writer.write(_CHARS, 0, 0);
	}

	@Test
	public void testWriteZeroLengthString() throws IOException {
		Writer writer = getWriter();

		writer.write(_STRING, 0, 0);
	}

	protected abstract Writer getWriter();

	private static final char[] _CHARS = {'a', 'b', 'c'};

	private static final String _STRING = "abc";

}