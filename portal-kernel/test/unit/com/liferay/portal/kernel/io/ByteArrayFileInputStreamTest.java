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

package com.liferay.portal.kernel.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ByteArrayFileInputStreamTest {

	@Before
	public void setUp() throws Exception {
		_testDir = new File("ByteArrayFileInputStreamTest.testDir");

		_testDir.mkdir();

		_testFile = new File("ByteArrayFileInputStreamTest.testFile");

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				_testFile)) {

			for (int i = 0; i < 1024; i++) {
				fileOutputStream.write(i);
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		_testDir.delete();
		_testFile.delete();
	}

	@Test
	public void testaAailable() throws IOException {

		// Uninitialized

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 512);

		Assert.assertEquals(0, byteArrayFileInputStream.available());

		// byte[]

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 2048);

		byteArrayFileInputStream.read();

		Assert.assertNotNull(byteArrayFileInputStream.data);
		Assert.assertNull(byteArrayFileInputStream.fileInputStream);
		Assert.assertEquals(1, byteArrayFileInputStream.index);
		Assert.assertEquals(1023, byteArrayFileInputStream.available());

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		byteArrayFileInputStream.read();

		Assert.assertNull(byteArrayFileInputStream.data);
		Assert.assertNotNull(byteArrayFileInputStream.fileInputStream);
		Assert.assertEquals(0, byteArrayFileInputStream.index);
		Assert.assertEquals(
			byteArrayFileInputStream.fileInputStream.available(),
			byteArrayFileInputStream.available());

		byteArrayFileInputStream.close();
	}

	@Test
	public void testBlockRead() throws IOException {

		// byte[]

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 2048);

		byte[] buffer = new byte[17];

		int index = 0;
		int length = 0;

		while ((length = byteArrayFileInputStream.read(buffer)) != -1) {
			for (int i = 0; i < length; i++) {
				Assert.assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		byteArrayFileInputStream.close();

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 2048);

		// 0 length

		Assert.assertEquals(0, byteArrayFileInputStream.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = byteArrayFileInputStream.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				Assert.assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
			}
		}

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		buffer = new byte[17];

		index = 0;
		length = 0;

		while ((length = byteArrayFileInputStream.read(buffer)) != -1) {
			for (int i = 0; i < length; i++) {
				Assert.assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		byteArrayFileInputStream.close();

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		// 0 length

		Assert.assertEquals(0, byteArrayFileInputStream.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = byteArrayFileInputStream.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				Assert.assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
			}
		}

		byteArrayFileInputStream.close();
	}

	@Test
	public void testClose() throws IOException {

		// Do not delete on close

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 512);

		byteArrayFileInputStream.read();

		byteArrayFileInputStream.close();

		Assert.assertNull(byteArrayFileInputStream.data);
		Assert.assertNull(byteArrayFileInputStream.file);
		Assert.assertNull(byteArrayFileInputStream.fileInputStream);

		Assert.assertTrue(_testFile.exists());

		// Delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, true);

		byteArrayFileInputStream.close();

		Assert.assertNull(byteArrayFileInputStream.data);
		Assert.assertNull(byteArrayFileInputStream.file);
		Assert.assertNull(byteArrayFileInputStream.fileInputStream);

		Assert.assertFalse(_testFile.exists());
	}

	@Test
	public void testConstructor() {

		// File is a dir

		try {
			new ByteArrayFileInputStream(_testDir, 1024);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// File does not exist

		try {
			new ByteArrayFileInputStream(new File("No Such File"), 1024);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Constructor 1

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 512);

		Assert.assertEquals(_testFile, byteArrayFileInputStream.file);
		Assert.assertEquals(1024, byteArrayFileInputStream.fileSize);
		Assert.assertEquals(512, byteArrayFileInputStream.threshold);
		Assert.assertFalse(byteArrayFileInputStream.deleteOnClose);

		// Constructor 2, do not delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, false);

		Assert.assertEquals(_testFile, byteArrayFileInputStream.file);
		Assert.assertEquals(1024, byteArrayFileInputStream.fileSize);
		Assert.assertEquals(512, byteArrayFileInputStream.threshold);
		Assert.assertFalse(byteArrayFileInputStream.deleteOnClose);

		// Constructor 2, delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, true);

		Assert.assertEquals(_testFile, byteArrayFileInputStream.file);
		Assert.assertEquals(1024, byteArrayFileInputStream.fileSize);
		Assert.assertEquals(512, byteArrayFileInputStream.threshold);
		Assert.assertTrue(byteArrayFileInputStream.deleteOnClose);
	}

	@Test
	public void testMark() throws IOException {

		// byte[]

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 2048);

		Assert.assertTrue(byteArrayFileInputStream.markSupported());

		for (int i = 0; i < 512; i++) {
			Assert.assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.mark(0);

		for (int i = 512; i < 1024; i++) {
			Assert.assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		Assert.assertEquals(-1, byteArrayFileInputStream.read());

		// In memory reset to index 512

		byteArrayFileInputStream.reset();

		for (int i = 512; i < 1024; i++) {
			Assert.assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		Assert.assertFalse(byteArrayFileInputStream.markSupported());

		for (int i = 0; i < 1024; i++) {
			Assert.assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		Assert.assertEquals(-1, byteArrayFileInputStream.read());

		// FileInputStream reset to index 0

		byteArrayFileInputStream.reset();

		for (int i = 0; i < 1024; i++) {
			Assert.assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.close();
	}

	@Test
	public void testSkip() throws IOException {

		// byte[]

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 2048);

		// Negative length

		Assert.assertEquals(0, byteArrayFileInputStream.skip(-1));

		int count = 1024 / 17;

		for (int i = 0; i < count; i++) {
			Assert.assertEquals(17, byteArrayFileInputStream.skip(17));
		}

		Assert.assertEquals(1024 % 17, byteArrayFileInputStream.skip(17));

		Assert.assertEquals(0, byteArrayFileInputStream.skip(17));

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		// 0 length

		Assert.assertEquals(0, byteArrayFileInputStream.skip(0));

		for (int i = 0; i < 1024; i++) {
			Assert.assertEquals(17, byteArrayFileInputStream.skip(17));
		}

		// Skip EOF

		byteArrayFileInputStream.skip(17);

		Assert.assertEquals(-1, byteArrayFileInputStream.read());

		byteArrayFileInputStream.close();
	}

	private File _testDir;
	private File _testFile;

}