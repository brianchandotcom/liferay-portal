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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.util.BaseTestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class DBStoreTest extends BaseTestCase {

	public void testAddFileWithByteArray() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	public void testAddFileWithFile() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(companyId, repositoryId, fileName, file);

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	public void testAddFileWithInputStream() throws Exception {

		// FileInputStream

		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(
			companyId, repositoryId, fileName, new FileInputStream(file));

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// UnsyncByteArrayInputStream

		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// ByteArrayInputStream

		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new ByteArrayInputStream(_DATA_VERSION_1));

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// BufferedInputStream

		companyId = nextLong();
		repositoryId = nextLong();
		fileName = randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new BufferedInputStream(
				new ByteArrayInputStream(_DATA_VERSION_1)));

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	public void testDeleteDirectory() throws Exception {

		// One level deep

		long companyId = nextLong();
		long repositoryId = nextLong();

		String directory = randomString();

		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(companyId, repositoryId, directory);

		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		// Two levels deep

		directory = randomString();
		fileName1 = directory + "/" + randomString();
		fileName2 = directory + "/" + randomString() + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(companyId, repositoryId, directory);

		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	public void testDeleteFile() throws Exception {
		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		assertTrue(_store.hasFile(companyId, repositoryId, fileName, "1.1"));

		_store.deleteFile(companyId, repositoryId, fileName);

		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		assertFalse(_store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	public void testDeleteFileWithVersion() throws Exception {

		// 1.0

		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		_store.deleteFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// 1.1

		assertTrue(_store.hasFile(companyId, repositoryId, fileName, "1.1"));

		_store.deleteFile(companyId, repositoryId, fileName, "1.1");

		assertFalse(_store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	public void testGetFileAsStream() throws Exception {
		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName);

		for (int i = 0; i < _DATA_SIZE; i++) {
			assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	public void testGetFileAsStreamWithVersion() throws Exception {
		Object[] data = addFile(5);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName, "1.5");

		for (int i = 0; i < _DATA_SIZE; i++) {
			assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	public void testGetFileNames() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();

		// One level deep

		String directory = randomString();

		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(companyId, repositoryId);

		assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		assertTrue(fileNamesSet.contains(fileName1));
		assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = randomString();

		String fileName3 = directory + "/" + randomString();
		String fileName4 =
			directory + "/" + randomString() + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(companyId, repositoryId);

		assertEquals(4, fileNames.length);

		fileNamesSet = SetUtil.fromArray(fileNames);

		assertTrue(fileNamesSet.contains(fileName1));
		assertTrue(fileNamesSet.contains(fileName2));
		assertTrue(fileNamesSet.contains(fileName3));
		assertTrue(fileNamesSet.contains(fileName4));
	}

	public void testGetFileNamesWithDirectory() throws Exception {

		// One level deep

		long companyId = nextLong();
		long repositoryId = nextLong();

		String directory = randomString();

		String fileName1 = directory + "/" + randomString();
		String fileName2 = directory + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, directory);

		assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		assertTrue(fileNamesSet.contains(fileName1));
		assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = randomString();

		String subdirectory = directory + "/" + randomString();

		String fileName3 = directory + "/" + randomString();
		String fileName4 = subdirectory + "/" + randomString();

		_store.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(companyId, repositoryId, directory);

		assertEquals(2, fileNames.length);

		fileNamesSet = SetUtil.fromArray(fileNames);

		assertTrue(fileNamesSet.contains(fileName3));
		assertTrue(fileNamesSet.contains(fileName4));

		fileNames = _store.getFileNames(companyId, repositoryId, subdirectory);

		assertEquals(1, fileNames.length);
		assertEquals(fileName4, fileNames[0]);
	}

	public void testGetFileSize() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		long size = _store.getFileSize(companyId, repositoryId, fileName);

		assertEquals(_DATA_SIZE, size);
	}

	public void testHasFile() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		assertTrue(_store.hasFile(companyId, repositoryId, fileName));
	}

	public void testHasFileWithVersion() throws Exception {
		Object[] data = addFile(5);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			assertTrue(
				_store.hasFile(
					companyId, repositoryId, fileName, versionLabel + i));
		}
	}

	public void testUpdateFileWithByteArray() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		_store.updateFile(
			companyId, repositoryId, fileName, "1.1", _DATA_VERSION_2);

		byte[] bytes1 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	public void testUpdateFileWithFile() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		File file = createFile(_DATA_VERSION_2);

		_store.updateFile(companyId, repositoryId, fileName, "1.1", file);

		byte[] bytes1 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	public void testUpdateFileWithInputStream() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		_store.updateFile(
			companyId, repositoryId, fileName, "1.1",
			new ByteArrayInputStream(_DATA_VERSION_2));

		byte[] bytes1 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	public void testUpdateFileWithNewFileName() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		String newFileName = randomString();

		_store.updateFile(companyId, repositoryId, fileName, newFileName);

		assertFalse(_store.hasFile(companyId, repositoryId, fileName));
		assertTrue(_store.hasFile(companyId, repositoryId, newFileName));
	}

	public void testUpdateFileWithNewRepositoryId() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		long newRepositoryId = nextLong();

		_store.updateFile(companyId, repositoryId, newRepositoryId, fileName);

		assertFalse(_store.hasFile(companyId, repositoryId, fileName));
		assertTrue(_store.hasFile(companyId, newRepositoryId, fileName));
	}

	protected Object[] addFile(int newVersionCount) throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String fileName = randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_store.updateFile(
				companyId, repositoryId, fileName, versionLabel + i,
				_DATA_VERSION_1);
		}

		assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		for (int i = 1; i <= newVersionCount; i++) {
			assertTrue(
				_store.hasFile(
					companyId, repositoryId, fileName, versionLabel + i));
		}

		return new Object[] {companyId, repositoryId, fileName};
	}

	protected File createFile(byte[] fileData) throws IOException {
		File file = File.createTempFile("DBStoreTest-testFile", null);

		OutputStream outputStream = new FileOutputStream(file);

		outputStream.write(fileData);

		outputStream.close();

		return file;
	}

	private static final int _DATA_SIZE = 1024 * 65;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];

	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE];

	private static Store _store = new DBStore();

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}
	}

}