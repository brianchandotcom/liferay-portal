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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DBStoreTest {

	@Before
	public void setUp() throws Exception {
		_companyId = RandomTestUtil.nextLong();
		_repositoryId = RandomTestUtil.nextLong();
	}

	@After
	public void tearDown() throws Exception {
		_store.deleteDirectory(_companyId, _repositoryId, StringPool.SLASH);
	}

	@Test
	public void testAddFileWithByteArray() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithFile() throws Exception {
		String fileName = RandomTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(_companyId, _repositoryId, fileName, file);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithInputStream() throws Exception {

		// FileInputStream

		String fileName = RandomTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(
			_companyId, _repositoryId, fileName, new FileInputStream(file));

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		// UnsyncByteArrayInputStream

		_companyId = RandomTestUtil.nextLong();
		_repositoryId = RandomTestUtil.nextLong();
		fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		// ByteArrayInputStream

		_companyId = RandomTestUtil.nextLong();
		_repositoryId = RandomTestUtil.nextLong();
		fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName,
			new ByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		// BufferedInputStream

		_companyId = RandomTestUtil.nextLong();
		_repositoryId = RandomTestUtil.nextLong();
		fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName,
			new BufferedInputStream(new ByteArrayInputStream(_DATA_VERSION_1)));

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectory() throws Exception {

		// One level deep

		String directory = RandomTestUtil.randomString();

		String fileName1 = directory + "/" + RandomTestUtil.randomString();
		String fileName2 = directory + "/" + RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName2, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(_companyId, _repositoryId, directory);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));

		// Two levels deep

		directory = RandomTestUtil.randomString();
		fileName1 = directory + "/" + RandomTestUtil.randomString();
		fileName2 =
			directory + "/" + RandomTestUtil.randomString() + "/" +
				RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName2, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(_companyId, _repositoryId, directory);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName = addFile(1);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(_companyId, _repositoryId, fileName, "1.1"));

		_store.deleteFile(_companyId, _repositoryId, fileName);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(_companyId, _repositoryId, fileName, "1.1"));
	}

	@Test
	public void testDeleteFileWithVersion() throws Exception {

		// 1.0

		String fileName = addFile(1);

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		_store.deleteFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		// 1.1

		Assert.assertTrue(
			_store.hasFile(_companyId, _repositoryId, fileName, "1.1"));

		_store.deleteFile(_companyId, _repositoryId, fileName, "1.1");

		Assert.assertFalse(
			_store.hasFile(_companyId, _repositoryId, fileName, "1.1"));
	}

	@Test
	public void testGetFileAsStream() throws Exception {
		String fileName = addFile(1);

		InputStream inputStream = _store.getFileAsStream(
			_companyId, _repositoryId, fileName);

		for (int i = 0; i < _DATA_SIZE; i++) {
			Assert.assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		Assert.assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	@Test
	public void testGetFileAsStreamWithVersion() throws Exception {
		String fileName = addFile(5);

		InputStream inputStream = _store.getFileAsStream(
			_companyId, _repositoryId, fileName, "1.5");

		for (int i = 0; i < _DATA_SIZE; i++) {
			Assert.assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		Assert.assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	@Test
	public void testGetFileNames() throws Exception {

		// One level deep

		String directory = RandomTestUtil.randomString();

		String fileName1 = directory + "/" + RandomTestUtil.randomString();
		String fileName2 = directory + "/" + RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(_companyId, _repositoryId);

		Assert.assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName1));
		Assert.assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = RandomTestUtil.randomString();

		String fileName3 = directory + "/" + RandomTestUtil.randomString();
		String fileName4 =
			directory + "/" + RandomTestUtil.randomString() + "/" +
				RandomTestUtil. randomString();

		_store.addFile(_companyId, _repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(_companyId, _repositoryId);

		Assert.assertEquals(4, fileNames.length);

		fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName1));
		Assert.assertTrue(fileNamesSet.contains(fileName2));
		Assert.assertTrue(fileNamesSet.contains(fileName3));
		Assert.assertTrue(fileNamesSet.contains(fileName4));
	}

	@Test
	public void testGetFileNamesWithDirectory() throws Exception {

		// One level deep

		String directory = RandomTestUtil.randomString();

		String fileName1 = directory + "/" + RandomTestUtil.randomString();
		String fileName2 = directory + "/" + RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, directory);

		Assert.assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName1));
		Assert.assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = RandomTestUtil.randomString();

		String subdirectory = directory + "/" + RandomTestUtil.randomString();

		String fileName3 = directory + "/" + RandomTestUtil.randomString();
		String fileName4 = subdirectory + "/" + RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(_companyId, _repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(_companyId, _repositoryId, directory);

		Assert.assertEquals(2, fileNames.length);

		fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName3));
		Assert.assertTrue(fileNamesSet.contains(fileName4));

		fileNames = _store.getFileNames(
			_companyId, _repositoryId, subdirectory);

		Assert.assertEquals(1, fileNames.length);
		Assert.assertEquals(fileName4, fileNames[0]);
	}

	@Test
	public void testGetFileSize() throws Exception {
		String fileName = addFile(0);

		long size = _store.getFileSize(_companyId, _repositoryId, fileName);

		Assert.assertEquals(_DATA_SIZE, size);
	}

	@Test
	public void testHasFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(_store.hasFile(_companyId, _repositoryId, fileName));
	}

	@Test
	public void testHasFileWithVersion() throws Exception {
		String fileName = addFile(5);

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(
				_store.hasFile(
					_companyId, _repositoryId, fileName, versionLabel + i));
		}
	}

	@Test
	public void testUpdateFileWithByteArray() throws Exception {
		String fileName = addFile(0);

		_store.updateFile(
			_companyId, _repositoryId, fileName, "1.1", _DATA_VERSION_2);

		byte[] bytes1 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.0");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
	public void testUpdateFileWithFile() throws Exception {
		String fileName = addFile(0);

		File file = createFile(_DATA_VERSION_2);

		_store.updateFile(_companyId, _repositoryId, fileName, "1.1", file);

		byte[] bytes1 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.0");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
	public void testUpdateFileWithInputStream() throws Exception {
		String fileName = addFile(0);

		_store.updateFile(
			_companyId, _repositoryId, fileName, "1.1",
			new ByteArrayInputStream(_DATA_VERSION_2));

		byte[] bytes1 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.0");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			_companyId, _repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
	public void testUpdateFileWithNewFileName() throws Exception {
		String fileName = addFile(0);

		String newFileName = RandomTestUtil.randomString();

		_store.updateFile(_companyId, _repositoryId, fileName, newFileName);

		Assert.assertFalse(_store.hasFile(_companyId, _repositoryId, fileName));
		Assert.assertTrue(
			_store.hasFile(_companyId, _repositoryId, newFileName));
	}

	@Test
	public void testUpdateFileWithNewRepositoryId() throws Exception {
		String fileName = addFile(0);

		long newRepositoryId = RandomTestUtil.nextLong();

		_store.updateFile(_companyId, _repositoryId, newRepositoryId, fileName);

		Assert.assertFalse(_store.hasFile(_companyId, _repositoryId, fileName));
		Assert.assertTrue(
			_store.hasFile(_companyId, newRepositoryId, fileName));
	}

	protected String addFile(int newVersionCount) throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(_companyId, _repositoryId, fileName, _DATA_VERSION_1);

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_store.updateFile(
				_companyId, _repositoryId, fileName, versionLabel + i,
				_DATA_VERSION_1);
		}

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));

		for (int i = 1; i <= newVersionCount; i++) {
			Assert.assertTrue(
				_store.hasFile(
					_companyId, _repositoryId, fileName, versionLabel + i));
		}

		return fileName;
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

	private long _companyId;
	private long _repositoryId;

}