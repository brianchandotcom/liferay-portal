/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Blob;
import java.sql.SQLException;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class OutputBlobTest {

	@Test
	public void testConstructor() {
		try {
			new OutputBlob(null, 10);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			new OutputBlob(new ByteArrayInputStream(new byte[10]), -1);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		new OutputBlob(new ByteArrayInputStream(new byte[10]), 10);
	}

	@Test
	public void testFree() throws SQLException {
		InputStream inputStream = new BufferedInputStream(
			new ByteArrayInputStream(new byte[10]));

		OutputBlob outputBlob = new OutputBlob(inputStream, 10);

		Assert.assertSame(inputStream, outputBlob.getBinaryStream());

		outputBlob.free();

		Assert.assertNull(outputBlob.getBinaryStream());

		try {
			inputStream.skip(1);

			Assert.fail();
		}
		catch (IOException ioException) {
		}
	}

	@Test
	public void testGetBinaryStream() throws IOException, SQLException {
		OutputBlob outputBlob = new OutputBlob(
			new ByteArrayInputStream(new byte[10]), 10);

		// pos < 1

		try {
			outputBlob.getBinaryStream(0, 5);

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// pos - 1 > _length

		try {
			outputBlob.getBinaryStream(11, 5);

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// pos - 1 + length > _length

		try {
			outputBlob.getBinaryStream(6, 6);

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// Fail to skip enough data

		outputBlob = new OutputBlob(new ByteArrayInputStream(new byte[10]), 5);

		try {
			outputBlob.getBinaryStream(6, 1);
			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// Normal

		outputBlob = new OutputBlob(new ByteArrayInputStream(new byte[10]), 10);

		InputStream inputStream = outputBlob.getBinaryStream(2, 5);

		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(0, inputStream.read());
		}

		Assert.assertEquals(-1, inputStream.read());
	}

	@Test
	public void testGetBytes() throws SQLException {
		OutputBlob outputBlob = new OutputBlob(
			new ByteArrayInputStream(new byte[10]), 10);

		// pos < 1

		try {
			outputBlob.getBytes(0, 5);

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// length < 0

		try {
			outputBlob.getBytes(1, -1);

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		// Normal read

		byte[] bytes = outputBlob.getBytes(1, 6);

		Assert.assertEquals(Arrays.toString(bytes), 6, bytes.length);

		// Short read

		bytes = outputBlob.getBytes(1, 6);

		Assert.assertEquals(Arrays.toString(bytes), 4, bytes.length);
	}

	@Test
	public void testGetLength() {
		OutputBlob outputBlob = new OutputBlob(
			new ByteArrayInputStream(new byte[10]), 10);

		Assert.assertEquals(10, outputBlob.length());

		outputBlob = new OutputBlob(new ByteArrayInputStream(new byte[10]), 5);

		Assert.assertEquals(5, outputBlob.length());
	}

	@Test
	public void testUnsupportedMethods() {
		OutputBlob outputBlob = new OutputBlob(
			new ByteArrayInputStream(new byte[10]), 10);

		try {
			outputBlob.position(new byte[10], 5);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		try {
			outputBlob.position((Blob)null, 5);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		try {
			outputBlob.setBinaryStream(5);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		try {
			outputBlob.setBytes(5, new byte[10]);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		try {
			outputBlob.setBytes(5, new byte[10], 2, 2);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		try {
			outputBlob.truncate(5);

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}
	}

}