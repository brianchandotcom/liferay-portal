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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.util.BaseTestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class DBStoreTest extends BaseTestCase {

	public void testAddFileWithByteArray() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_STORE.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);
	}

	public void testAddFileWithFile() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		File testFile = createTempFile(_DATA_VERSION_1);

		_STORE.addFile(companyId, repositoryId, fileName, testFile);

		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);
	}

	public void testAddFileWithInputStream() throws Exception {
		// FileInputStream
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		File testFile = createTempFile(_DATA_VERSION_1);

		_STORE.addFile(
			companyId, repositoryId, fileName, new FileInputStream(testFile));

		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);

		// UnsyncByteArrayInputStream
		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_STORE.addFile(
			companyId, repositoryId, fileName,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);

		// ByteArrayInputStream
		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_STORE.addFile(
			companyId, repositoryId, fileName,
			new ByteArrayInputStream(_DATA_VERSION_1));

		exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);

		// BufferedInputStream
		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_STORE.addFile(
			companyId, repositoryId, fileName,
			new BufferedInputStream(
				new ByteArrayInputStream(_DATA_VERSION_1)));

		exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);
	}

	public void testDeleteDirectory() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();

		// One level directory
		String directory = randomString();
		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		boolean fileExist1 = _STORE.hasFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT);
		boolean fileExist2 = _STORE.hasFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT);

		assertTrue(fileExist1);
		assertTrue(fileExist2);

		_STORE.deleteDirectory(companyId, repositoryId, directory);

		fileExist1 = _STORE.hasFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT);
		fileExist2 = _STORE.hasFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT);

		assertFalse(fileExist1);
		assertFalse(fileExist2);

		// Two levels directories
		directory = randomString();
		String subDirectory = directory + "/" + randomString();
		fileName1 = directory + "/" + randomString();
		fileName2 = subDirectory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		fileExist1 = _STORE.hasFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT);
		fileExist2 = _STORE.hasFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT);

		assertTrue(fileExist1);
		assertTrue(fileExist2);

		_STORE.deleteDirectory(companyId, repositoryId, directory);

		fileExist1 = _STORE.hasFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT);
		fileExist2 = _STORE.hasFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT);

		assertFalse(fileExist1);
		assertFalse(fileExist2);
	}

	public void testDeleteFile() throws Exception {
		Object[] data = createFile(1);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
		assertTrue(exist);

		exist = _STORE.hasFile(companyId, repositoryId, fileName, "1.1");
		assertTrue(exist);

		_STORE.deleteFile(companyId, repositoryId, fileName);

		exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
		assertFalse(exist);

		exist = _STORE.hasFile(companyId, repositoryId, fileName, "1.1");

		assertFalse(exist);
	}

	public void testDeleteFileWithVersion() throws Exception {
		Object[] data = createFile(1);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		// 1.0
		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
		assertTrue(exist);

		_STORE.deleteFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
		assertFalse(exist);

		// 1.1
		exist = _STORE.hasFile(companyId, repositoryId, fileName, "1.1");
		assertTrue(exist);

		_STORE.deleteFile(companyId, repositoryId, fileName, "1.1");

		exist = _STORE.hasFile(companyId, repositoryId, fileName, "1.1");
		assertFalse(exist);
	}

	public void testGetFileAsStream() throws Exception {
		Object[] data = createFile(1);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		InputStream is = _STORE.getFileAsStream(
			companyId, repositoryId, fileName);

		for (int i = 0; i < _DATA_SIZE; i++) {
			assertEquals(_DATA_VERSION_1[i], (byte)is.read());
		}

		assertEquals(-1, is.read());

		is.close();
	}

	public void testGetFileAsStreamWithVersion() throws Exception {
		Object[] data = createFile(5);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		InputStream is = _STORE.getFileAsStream(
			companyId, repositoryId, fileName, "1.5");

		for (int i = 0; i < _DATA_SIZE; i++) {
			assertEquals(_DATA_VERSION_1[i], (byte)is.read());
		}

		assertEquals(-1, is.read());

		is.close();
	}

	public void testGetFileNames() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();

		// One level directory
		String directory = randomString();
		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _STORE.getFileNames(companyId, repositoryId);

		assertEquals(2, fileNames.length);

		List<String> fileNameList = Arrays.asList(fileNames);

		assertTrue(fileNameList.contains(fileName1));
		assertTrue(fileNameList.contains(fileName2));

		// Two levels directories
		directory = randomString();
		String subDirectory = directory + "/" + randomString();
		String fileName3 = directory + "/" + randomString();
		String fileName4 = subDirectory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _STORE.getFileNames(companyId, repositoryId);

		assertEquals(4, fileNames.length);

		fileNameList = Arrays.asList(fileNames);

		assertTrue(fileNameList.contains(fileName1));
		assertTrue(fileNameList.contains(fileName2));
		assertTrue(fileNameList.contains(fileName3));
		assertTrue(fileNameList.contains(fileName4));
	}

	public void testGetFileNamesWithDirectory() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();

		// One level directory
		String directory = randomString();
		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _STORE.getFileNames(
			companyId, repositoryId, directory);

		assertEquals(2, fileNames.length);

		List<String> fileNameList = Arrays.asList(fileNames);

		assertTrue(fileNameList.contains(fileName1));
		assertTrue(fileNameList.contains(fileName2));

		// Two levels directories
		directory = randomString();
		String subDirectory = directory + "/" + randomString();
		String fileName3 = directory + "/" + randomString();
		String fileName4 = subDirectory + "/" + randomString();

		_STORE.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_STORE.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _STORE.getFileNames(companyId, repositoryId, directory);

		assertEquals(2, fileNames.length);

		fileNameList = Arrays.asList(fileNames);

		assertTrue(fileNameList.contains(fileName3));
		assertTrue(fileNameList.contains(fileName4));

		fileNames = _STORE.getFileNames(companyId, repositoryId, subDirectory);

		assertEquals(1, fileNames.length);

		assertEquals(fileName4, fileNames[0]);
	}

	public void testGetFileSize() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		long size = _STORE.getFileSize(companyId, repositoryId, fileName);

		assertEquals(_DATA_SIZE, size);
	}

	public void testHasFile() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_STORE.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		boolean exist = _STORE.hasFile(companyId, repositoryId, fileName);

		assertTrue(exist);
	}

	public void testHasFileWithVersion() throws Exception {
		Object[] data = createFile(5);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {

			boolean exist = _STORE.hasFile(
				companyId, repositoryId, fileName, versionLabel + i);

			assertTrue(exist);
		}
	}

	public void testUpdateFileWithByteArray() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		_STORE.updateFile(
			companyId, repositoryId, fileName, "1.1", _DATA_VERSION_2);

		byte[] result1 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, result1));

		byte[] result2 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, result2));

		byte[] result3 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, result3));
	}

	public void testUpdateFileWithFile() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		File testFile = createTempFile(_DATA_VERSION_2);

		_STORE.updateFile(companyId, repositoryId, fileName, "1.1", testFile);

		byte[] result1 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, result1));

		byte[] result2 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, result2));

		byte[] result3 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, result3));
	}

	public void testUpdateFileWithInputStream() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		_STORE.updateFile(
			companyId, repositoryId, fileName, "1.1",
			new ByteArrayInputStream(_DATA_VERSION_2));

		byte[] result1 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, result1));

		byte[] result2 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, result2));

		byte[] result3 = _STORE.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, result3));
	}

	public void testUpdateFileWithNewFileName() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		String newFileName = randomString();

		_STORE.updateFile(companyId, repositoryId, fileName, newFileName);

		boolean exist1 = _STORE.hasFile(companyId, repositoryId, fileName);
		boolean exist2 = _STORE.hasFile(companyId, repositoryId, newFileName);

		assertFalse(exist1);
		assertTrue(exist2);
	}

	public void testUpdateFileWithNewRepositoryId() throws Exception {
		Object[] data = createFile(0);

		Long companyId =  (Long)data[0];
		Long repositoryId =  (Long)data[1];
		String fileName = (String)data[2];

		long newRepositoryId = nextLong();

		_STORE.updateFile(companyId, repositoryId, newRepositoryId, fileName);

		boolean exist1 = _STORE.hasFile(companyId, repositoryId, fileName);
		boolean exist2 = _STORE.hasFile(companyId, newRepositoryId, fileName);

		assertFalse(exist1);
		assertTrue(exist2);
	}

	private Object[] createFile(int newVersionCount) throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_STORE.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_STORE.updateFile(
				companyId, repositoryId, fileName, versionLabel + i,
				_DATA_VERSION_1);
		}

		boolean exist = _STORE.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertTrue(exist);

		for (int i = 1; i <= newVersionCount; i++) {
			exist = _STORE.hasFile(
				companyId, repositoryId, fileName, versionLabel + i);

			assertTrue(exist);
		}

		return new Object[] {companyId, repositoryId, fileName};
	}

	private File createTempFile(byte[] fileData) throws IOException {
		File tempFile = File.createTempFile("DBStoreTest-testFile", null);

		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		fileOutputStream.write(fileData);
		fileOutputStream.close();

		return tempFile;
	}

	private static final Store _STORE = new DBStore();

	private static final int _DATA_SIZE = 1024 * 65;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];
	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE];

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}
	}

}