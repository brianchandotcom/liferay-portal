/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;

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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DBStoreTest {

	@Test
	public void testAddFileWithByteArray() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String fileName = ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithFile() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String fileName = ServiceTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(companyId, repositoryId, fileName, file);

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithInputStream() throws Exception {

		// FileInputStream

		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String fileName = ServiceTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		_store.addFile(
			companyId, repositoryId, fileName, new FileInputStream(file));

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// UnsyncByteArrayInputStream

		companyId = ServiceTestUtil.nextLong();
		repositoryId = ServiceTestUtil.nextLong();
		fileName = ServiceTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// ByteArrayInputStream

		companyId = ServiceTestUtil.nextLong();
		repositoryId = ServiceTestUtil.nextLong();
		fileName = ServiceTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new ByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// BufferedInputStream

		companyId = ServiceTestUtil.nextLong();
		repositoryId = ServiceTestUtil.nextLong();
		fileName = ServiceTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName,
			new BufferedInputStream(new ByteArrayInputStream(_DATA_VERSION_1)));

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectory() throws Exception {

		// One level deep

		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();

		String directory = ServiceTestUtil.randomString();

		String fileName1 = directory + "/" + ServiceTestUtil.randomString();
		String fileName2 = directory + "/" + ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(companyId, repositoryId, directory);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		// Two levels deep

		directory = ServiceTestUtil.randomString();
		fileName1 = directory + "/" + ServiceTestUtil.randomString();
		fileName2 =
			directory + "/" + ServiceTestUtil.randomString() + "/" +
				ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));

		_store.deleteDirectory(companyId, repositoryId, directory);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(companyId, repositoryId, fileName, "1.1"));

		_store.deleteFile(companyId, repositoryId, fileName);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	@Test
	public void testDeleteFileWithVersion() throws Exception {

		// 1.0

		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		_store.deleteFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		// 1.1

		Assert.assertTrue(
			_store.hasFile(companyId, repositoryId, fileName, "1.1"));

		_store.deleteFile(companyId, repositoryId, fileName, "1.1");

		Assert.assertFalse(
			_store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	@Test
	public void testGetFileAsStream() throws Exception {
		Object[] data = addFile(1);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName);

		for (int i = 0; i < _DATA_SIZE; i++) {
			Assert.assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		Assert.assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	@Test
	public void testGetFileAsStreamWithVersion() throws Exception {
		Object[] data = addFile(5);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName, "1.5");

		for (int i = 0; i < _DATA_SIZE; i++) {
			Assert.assertEquals(_DATA_VERSION_1[i], (byte)inputStream.read());
		}

		Assert.assertEquals(-1, inputStream.read());

		inputStream.close();
	}

	@Test
	public void testGetFileNames() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();

		// One level deep

		String directory = ServiceTestUtil.randomString();

		String fileName1 = directory + "/" + ServiceTestUtil.randomString();
		String fileName2 = directory + "/" + ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(companyId, repositoryId);

		Assert.assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName1));
		Assert.assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = ServiceTestUtil.randomString();

		String fileName3 = directory + "/" + ServiceTestUtil.randomString();
		String fileName4 =
			directory + "/" + ServiceTestUtil.randomString() + "/" +
				ServiceTestUtil. randomString();

		_store.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(companyId, repositoryId);

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

		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();

		String directory = ServiceTestUtil.randomString();

		String fileName1 = directory + "/" + ServiceTestUtil.randomString();
		String fileName2 = directory + "/" + ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, directory);

		Assert.assertEquals(2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName1));
		Assert.assertTrue(fileNamesSet.contains(fileName2));

		// Two levels deep

		directory = ServiceTestUtil.randomString();

		String subdirectory = directory + "/" + ServiceTestUtil.randomString();

		String fileName3 = directory + "/" + ServiceTestUtil.randomString();
		String fileName4 = subdirectory + "/" + ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);
		_store.addFile(companyId, repositoryId, fileName4, _DATA_VERSION_1);

		fileNames = _store.getFileNames(companyId, repositoryId, directory);

		Assert.assertEquals(2, fileNames.length);

		fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(fileNamesSet.contains(fileName3));
		Assert.assertTrue(fileNamesSet.contains(fileName4));

		fileNames = _store.getFileNames(companyId, repositoryId, subdirectory);

		Assert.assertEquals(1, fileNames.length);
		Assert.assertEquals(fileName4, fileNames[0]);
	}

	@Test
	public void testGetFileSize() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		long size = _store.getFileSize(companyId, repositoryId, fileName);

		Assert.assertEquals(_DATA_SIZE, size);
	}

	@Test
	public void testHasFile() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String fileName = ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(_store.hasFile(companyId, repositoryId, fileName));
	}

	@Test
	public void testHasFileWithVersion() throws Exception {
		Object[] data = addFile(5);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(
				_store.hasFile(
					companyId, repositoryId, fileName, versionLabel + i));
		}
	}

	@Test
	public void testUpdateFileWithByteArray() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		_store.updateFile(
			companyId, repositoryId, fileName, "1.1", _DATA_VERSION_2);

		byte[] bytes1 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
	public void testUpdateFileWithFile() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		File file = createFile(_DATA_VERSION_2);

		_store.updateFile(companyId, repositoryId, fileName, "1.1", file);

		byte[] bytes1 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
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

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_1, bytes1));

		byte[] bytes2 = _store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes2));

		byte[] bytes3 = _store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertTrue(Arrays.equals(_DATA_VERSION_2, bytes3));
	}

	@Test
	public void testUpdateFileWithNewFileName() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		String newFileName = ServiceTestUtil.randomString();

		_store.updateFile(companyId, repositoryId, fileName, newFileName);

		Assert.assertFalse(_store.hasFile(companyId, repositoryId, fileName));
		Assert.assertTrue(_store.hasFile(companyId, repositoryId, newFileName));
	}

	@Test
	public void testUpdateFileWithNewRepositoryId() throws Exception {
		Object[] data = addFile(0);

		long companyId = (Long)data[0];
		long repositoryId = (Long)data[1];
		String fileName = (String)data[2];

		long newRepositoryId = ServiceTestUtil.nextLong();

		_store.updateFile(companyId, repositoryId, newRepositoryId, fileName);

		Assert.assertFalse(_store.hasFile(companyId, repositoryId, fileName));
		Assert.assertTrue(_store.hasFile(companyId, newRepositoryId, fileName));
	}

	protected Object[] addFile(int newVersionCount) throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String fileName = ServiceTestUtil.randomString();

		_store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_store.updateFile(
				companyId, repositoryId, fileName, versionLabel + i,
				_DATA_VERSION_1);
		}

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));

		for (int i = 1; i <= newVersionCount; i++) {
			Assert.assertTrue(
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