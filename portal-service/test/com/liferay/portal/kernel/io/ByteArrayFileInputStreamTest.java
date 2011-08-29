/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Shuyang Zhou
 */
public class ByteArrayFileInputStreamTest extends TestCase {

	public void setUp() throws IOException {
		_testDir = new File("test-dir");
		_testDir.mkdir();

		_testFile = new File("test-file");

		FileOutputStream fos = new FileOutputStream(_testFile);

		for (int i = 0; i < 1024; i++) {
			fos.write(i);
		}

		fos.close();
	}

	public void tearDown() {
		_testDir.delete();
		_testFile.delete();
	}

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
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			512);

		assertEquals(_testFile, bafi.file);
		assertEquals(1024, bafi.fileSize);
		assertEquals(512, bafi.threshold);
		assertFalse(bafi.deleteOnClose);

		// Constructor 2, deleteOnClose = false
		bafi = new ByteArrayFileInputStream(_testFile, 512, false);

		assertEquals(_testFile, bafi.file);
		assertEquals(1024, bafi.fileSize);
		assertEquals(512, bafi.threshold);
		assertFalse(bafi.deleteOnClose);

		// Constructor 2, deleteOnClose = true
		bafi = new ByteArrayFileInputStream(_testFile, 512, true);

		assertEquals(_testFile, bafi.file);
		assertEquals(1024, bafi.fileSize);
		assertEquals(512, bafi.threshold);
		assertTrue(bafi.deleteOnClose);
	}

	public void testaAailable() throws IOException {
		// Un-initialized
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			512);

		assertEquals(0, bafi.available());

		// byte[]
		bafi = new ByteArrayFileInputStream(_testFile, 2048);
		bafi.read();

		assertNotNull(bafi.data);
		assertNull(bafi.fileInputStream);
		assertEquals(1, bafi.index);
		assertEquals(1023, bafi.available());

		bafi.close();

		// FileInputStream
		bafi = new ByteArrayFileInputStream(_testFile, 512);
		bafi.read();

		assertNull(bafi.data);
		assertNotNull(bafi.fileInputStream);
		assertEquals(0, bafi.index);
		assertEquals(bafi.fileInputStream.available(), bafi.available());
		bafi.close();
	}

	public void testClose() throws IOException {
		// deleteOnClose = false
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			512);
		bafi.read();

		bafi.close();
		assertNull(bafi.data);
		assertNull(bafi.file);
		assertNull(bafi.fileInputStream);
		assertTrue(_testFile.exists());

		// deleteOnClose = true
		bafi = new ByteArrayFileInputStream(_testFile, 512, true);

		bafi.close();
		assertNull(bafi.data);
		assertNull(bafi.file);
		assertNull(bafi.fileInputStream);
		assertFalse(_testFile.exists());

	}

	public void testMark() throws IOException {
		// byte[]
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			2048);
		assertTrue(bafi.markSupported());

		for (int i = 0; i < 512; i++) {
			assertEquals(i & 0xff, bafi.read());
		}

		bafi.mark(0);

		for (int i = 512; i < 1024; i++) {
			assertEquals(i & 0xff, bafi.read());
		}

		assertEquals(-1, bafi.read());

		// In memory reset to index 512
		bafi.reset();

		for (int i = 512; i < 1024; i++) {
			assertEquals(i & 0xff, bafi.read());
		}

		bafi.close();

		// FileInputStream
		bafi = new ByteArrayFileInputStream(_testFile, 512);

		assertFalse(bafi.markSupported());

		for (int i = 0; i < 1024; i++) {
			assertEquals(i & 0xff, bafi.read());
		}

		assertEquals(-1, bafi.read());

		// FileInputStream reset to index 0
		bafi.reset();

		for (int i = 0; i < 1024; i++) {
			assertEquals(i & 0xff, bafi.read());
		}

		bafi.close();
	}

	public void testBlockRead() throws IOException {
		// byte[]
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			2048);

		byte[] buffer = new byte[17];

		int index = 0;
		int length = 0;

		while ((length = bafi.read(buffer)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		bafi.close();

		bafi = new ByteArrayFileInputStream(_testFile, 2048);

		// Zero length
		assertEquals(0, bafi.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = bafi.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
			}
		}

		bafi.close();

		// FileInputStream
		bafi = new ByteArrayFileInputStream(_testFile, 512);

		buffer = new byte[17];

		index = 0;
		length = 0;

		while ((length = bafi.read(buffer)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i] & 0xff);
			}
		}

		bafi.close();

		bafi = new ByteArrayFileInputStream(_testFile, 512);

		// Zero length
		assertEquals(0, bafi.read(null, -1, 0));

		buffer = new byte[48];

		index = 0;
		length = 0;

		while ((length = bafi.read(buffer, 16, 16)) != -1) {
			for (int i = 0; i < length; i++) {
				assertEquals(index++ & 0xff, buffer[i + 16] & 0xff);
			}
		}

		bafi.close();
	}

	public void testSkip() throws IOException {
		// byte[]
		ByteArrayFileInputStream bafi = new ByteArrayFileInputStream(_testFile,
			2048);

		// Negative length
		assertEquals(0, bafi.skip(-1));

		int count = 1024 / 17;

		for (int i = 0; i < count; i++) {
			assertEquals(17, bafi.skip(17));
		}

		assertEquals(1024 % 17, bafi.skip(17));

		assertEquals(0, bafi.skip(17));

		bafi.close();

		// FileInputStream
		bafi = new ByteArrayFileInputStream(_testFile, 512);

		// Zero length
		assertEquals(0, bafi.skip(0));

		for (int i = 0; i < 1024; i++) {
			assertEquals(17, bafi.skip(17));
		}

		// Skip EOF
		bafi.skip(17);

		assertEquals(-1, bafi.read());

		bafi.close();
	}

	private File _testDir;
	private File _testFile;

}