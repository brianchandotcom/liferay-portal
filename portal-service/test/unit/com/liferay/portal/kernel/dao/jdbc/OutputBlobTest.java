/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.jdbc;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Blob;
import java.sql.SQLException;

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
		catch (IllegalArgumentException iae) {
		}

		try {
			new OutputBlob(new ByteArrayInputStream(new byte[10]), -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
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
		catch (IOException ioe) {
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
		catch (SQLException sqle) {
		}

		// pos - 1 > _length

		try {
			outputBlob.getBinaryStream(11, 5);

			Assert.fail();
		}
		catch (SQLException sqle) {
		}

		// pos - 1 + length > _length

		try {
			outputBlob.getBinaryStream(6, 6);

			Assert.fail();
		}
		catch (SQLException sqle) {
		}

		// Fail to skip enough data

		outputBlob = new OutputBlob(new ByteArrayInputStream(new byte[10]), 5);

		try {
			outputBlob.getBinaryStream(6, 1);
			Assert.fail();
		}
		catch (SQLException sqle) {
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
		catch (SQLException sqle) {
		}

		// length < 0

		try {
			outputBlob.getBytes(1, -1);

			Assert.fail();
		}
		catch (SQLException sqle) {
		}

		// Normal read

		byte[] bytes = outputBlob.getBytes(1, 6);

		Assert.assertEquals(6, bytes.length);

		// Short read

		bytes = outputBlob.getBytes(1, 6);

		Assert.assertEquals(4, bytes.length);
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
		catch (UnsupportedOperationException uoe) {
		}

		try {
			outputBlob.position((Blob)null, 5);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			outputBlob.setBinaryStream(5);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			outputBlob.setBytes(5, new byte[10]);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			outputBlob.setBytes(5, new byte[10], 2, 2);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		try {
			outputBlob.truncate(5);

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}
	}

}