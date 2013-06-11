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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ByteArrayFileInputStreamTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		_testDir = new File("ByteArrayFileInputStreamTest.testDir");

		_testDir.mkdir();

		_testFile = new File("ByteArrayFileInputStreamTest.testFile");

		FileOutputStream fileOutputStream = new FileOutputStream(_testFile);

		for (int i = 0; i < 1024; i++) {
			fileOutputStream.write(i);
		}

		fileOutputStream.close();
	}

	@Override
	public void tearDown() throws Exception {
		_testDir.delete();
		_testFile.delete();
	}

	@Test
	public void testaAailable() throws IOException {

		// Uninitialized

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 512);

		assertEquals(0, byteArrayFileInputStream.available());

		// byte[]

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 2048);

		byteArrayFileInputStream.read();

		assertNotNull(byteArrayFileInputStream.data);
		assertNull(byteArrayFileInputStream.fileInputStream);
		assertEquals(1, byteArrayFileInputStream.index);
		assertEquals(1023, byteArrayFileInputStream.available());

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		byteArrayFileInputStream.read();

		assertNull(byteArrayFileInputStream.data);
		assertNotNull(byteArrayFileInputStream.fileInputStream);
		assertEquals(0, byteArrayFileInputStream.index);
		assertEquals(
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
				assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		byteArrayFileInputStream.close();

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 2048);

		// 0 length

		assertEquals(0, byteArrayFileInputStream.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = byteArrayFileInputStream.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
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
				assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		byteArrayFileInputStream.close();

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		// 0 length

		assertEquals(0, byteArrayFileInputStream.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = byteArrayFileInputStream.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
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
		assertNull(byteArrayFileInputStream.data);
		assertNull(byteArrayFileInputStream.file);
		assertNull(byteArrayFileInputStream.fileInputStream);
		assertTrue(_testFile.exists());

		// Delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, true);

		byteArrayFileInputStream.close();

		assertNull(byteArrayFileInputStream.data);
		assertNull(byteArrayFileInputStream.file);
		assertNull(byteArrayFileInputStream.fileInputStream);
		assertFalse(_testFile.exists());
	}

	@Test
	public void testConstructor() {

		// File is a dir

		try {
			new ByteArrayFileInputStream(_testDir, 1024);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// File does not exist

		try {
			new ByteArrayFileInputStream(new File("No Such File"), 1024);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		// Constructor 1

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 512);

		assertEquals(_testFile, byteArrayFileInputStream.file);
		assertEquals(1024, byteArrayFileInputStream.fileSize);
		assertEquals(512, byteArrayFileInputStream.threshold);
		assertFalse(byteArrayFileInputStream.deleteOnClose);

		// Constructor 2, do not delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, false);

		assertEquals(_testFile, byteArrayFileInputStream.file);
		assertEquals(1024, byteArrayFileInputStream.fileSize);
		assertEquals(512, byteArrayFileInputStream.threshold);
		assertFalse(byteArrayFileInputStream.deleteOnClose);

		// Constructor 2, delete on close

		byteArrayFileInputStream = new ByteArrayFileInputStream(
			_testFile, 512, true);

		assertEquals(_testFile, byteArrayFileInputStream.file);
		assertEquals(1024, byteArrayFileInputStream.fileSize);
		assertEquals(512, byteArrayFileInputStream.threshold);
		assertTrue(byteArrayFileInputStream.deleteOnClose);
	}

	@Test
	public void testMark() throws IOException {

		// byte[]

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 2048);

		assertTrue(byteArrayFileInputStream.markSupported());

		for (int i = 0; i < 512; i++) {
			assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.mark(0);

		for (int i = 512; i < 1024; i++) {
			assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		assertEquals(-1, byteArrayFileInputStream.read());

		// In memory reset to index 512

		byteArrayFileInputStream.reset();

		for (int i = 512; i < 1024; i++) {
			assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		assertFalse(byteArrayFileInputStream.markSupported());

		for (int i = 0; i < 1024; i++) {
			assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		assertEquals(-1, byteArrayFileInputStream.read());

		// FileInputStream reset to index 0

		byteArrayFileInputStream.reset();

		for (int i = 0; i < 1024; i++) {
			assertEquals(i & 0xff, byteArrayFileInputStream.read());
		}

		byteArrayFileInputStream.close();
	}

	@Test
	public void testSkip() throws IOException {

		// byte[]

		ByteArrayFileInputStream byteArrayFileInputStream =
			new ByteArrayFileInputStream(_testFile, 2048);

		// Negative length

		assertEquals(0, byteArrayFileInputStream.skip(-1));

		int count = 1024 / 17;

		for (int i = 0; i < count; i++) {
			assertEquals(17, byteArrayFileInputStream.skip(17));
		}

		assertEquals(1024 % 17, byteArrayFileInputStream.skip(17));

		assertEquals(0, byteArrayFileInputStream.skip(17));

		byteArrayFileInputStream.close();

		// FileInputStream

		byteArrayFileInputStream = new ByteArrayFileInputStream(_testFile, 512);

		// 0 length

		assertEquals(0, byteArrayFileInputStream.skip(0));

		for (int i = 0; i < 1024; i++) {
			assertEquals(17, byteArrayFileInputStream.skip(17));
		}

		// Skip EOF

		byteArrayFileInputStream.skip(17);

		assertEquals(-1, byteArrayFileInputStream.read());

		byteArrayFileInputStream.close();
	}

	private File _testDir;
	private File _testFile;

}