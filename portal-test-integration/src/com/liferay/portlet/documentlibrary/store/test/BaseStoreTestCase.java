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

package com.liferay.portlet.documentlibrary.store.test;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.BaseStore;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;

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

/**
 * @author Preston Crary
 * @deprecated As of Mueller (7.2.x), replaced by {@link
 *             com.liferay.portal.store.test.util.BaseStoreTestCase}
 */
@Deprecated
public abstract class BaseStoreTestCase {

	@Before
	public void setUp() throws Exception {
		store = getStore();
		companyId = RandomTestUtil.nextLong();
		repositoryId = RandomTestUtil.nextLong();
	}

	@After
	public void tearDown() throws Exception {
		store.deleteDirectory(companyId, repositoryId, StringPool.SLASH);
	}

	@Test
	public void testAddFileWithBufferedInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(
			companyId, repositoryId, fileName,
			new BufferedInputStream(new ByteArrayInputStream(_DATA_VERSION_1)));

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithByteArray() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithByteArrayInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(
			companyId, repositoryId, fileName,
			new ByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithFile() throws Exception {
		String fileName = RandomTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		store.addFile(companyId, repositoryId, fileName, file);

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithFileInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();
		File file = createFile(_DATA_VERSION_1);

		store.addFile(
			companyId, repositoryId, fileName, new FileInputStream(file));

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testAddFileWithUnsyncByteArrayInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(
			companyId, repositoryId, fileName,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testCopyFileVersion() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 1);

		store.copyFileVersion(companyId, repositoryId, fileName, "1.0", "1.2");

		Assert.assertTrue(
			store.hasFile(companyId, repositoryId, fileName, "1.2"));
		Assert.assertArrayEquals(
			_DATA_VERSION_1,
			store.getFileAsBytes(companyId, repositoryId, fileName, "1.2"));
	}

	@Test(expected = DuplicateFileException.class)
	public void testCopyFileVersionDuplicateFileException() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 1);

		store.copyFileVersion(companyId, repositoryId, fileName, "1.0", "1.1");
	}

	@Test(expected = NoSuchFileException.class)
	public void testCopyFileVersionNoSuchFileException() throws Exception {
		store.copyFileVersion(
			companyId, repositoryId, RandomTestUtil.randomString(), "1.0",
			"1.1");
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		store.deleteDirectory(companyId, repositoryId, dirName);

		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectoryWithTwoLevelDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		store.deleteDirectory(companyId, repositoryId, dirName);

		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 1);

		store.deleteFile(companyId, repositoryId, fileName);

		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	@Test
	public void testDeleteFileWithVersion() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 1);

		store.deleteFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	@Test(expected = NoSuchFileException.class)
	public void testGetFileAsBytesNoSuchFileException() throws Exception {
		store.getFileAsBytes(
			companyId, repositoryId, RandomTestUtil.randomString());
	}

	@Test
	public void testGetFileAsStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 1);

		try (InputStream inputStream = store.getFileAsStream(
				companyId, repositoryId, fileName)) {

			for (int i = 0; i < _DATA_SIZE; i++) {
				Assert.assertEquals(
					_DATA_VERSION_1[i], (byte)inputStream.read());
			}

			Assert.assertEquals(-1, inputStream.read());
		}
	}

	@Test
	public void testGetFileAsStreamWithVersion() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 5);

		try (InputStream inputStream = store.getFileAsStream(
				companyId, repositoryId, fileName, "1.5")) {

			for (int i = 0; i < _DATA_SIZE; i++) {
				Assert.assertEquals(
					_DATA_VERSION_1[i], (byte)inputStream.read());
			}

			Assert.assertEquals(-1, inputStream.read());
		}
	}

	@Test
	public void testGetFileNames() throws Exception {
		String fileName1 = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = store.getFileNames(companyId, repositoryId);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));
	}

	@Test
	public void testGetFileNamesWithDirectoryOneLevelDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = store.getFileNames(
			companyId, repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));
	}

	@Test
	public void testGetFileNamesWithDirectoryTwoLevelDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String fileName3 =
			RandomTestUtil.randomString() + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName3, _DATA_VERSION_1);

		String[] fileNames = store.getFileNames(
			companyId, repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));

		fileNames = store.getFileNames(companyId, repositoryId, subdirName);

		Assert.assertEquals(Arrays.toString(fileNames), 1, fileNames.length);
		Assert.assertEquals(fileName2, fileNames[0]);
	}

	@Test
	public void testGetFileNamesWithInvalidDirectory() {
		String dirName = RandomTestUtil.randomString();

		String[] fileNames = store.getFileNames(
			companyId, repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithInvalidRepository() throws Exception {
		String[] fileNames = store.getFileNames(companyId, repositoryId);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithTwoLevelsDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName1, _DATA_VERSION_1);

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName2, _DATA_VERSION_1);

		String[] fileNames = store.getFileNames(companyId, repositoryId);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));
	}

	@Test
	public void testGetFileSize() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		long size = store.getFileSize(companyId, repositoryId, fileName);

		Assert.assertEquals(_DATA_SIZE, size);
	}

	@Test(expected = NoSuchFileException.class)
	public void testGetFileSizeNoSuchFileException() throws Exception {
		store.getFileSize(
			companyId, repositoryId, RandomTestUtil.randomString());
	}

	@Test(expected = NoSuchFileException.class)
	public void testGetFileVersionAsBytesNoSuchFileException()
		throws Exception {

		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.getFileAsBytes(companyId, repositoryId, fileName, "1.1");
	}

	@Test
	public void testHasFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		Assert.assertTrue(store.hasFile(companyId, repositoryId, fileName));
	}

	@Test
	public void testHasFileWithVersion() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 5);

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(
				store.hasFile(
					companyId, repositoryId, fileName, versionLabel + i));
		}
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedLog = "Unable to delete file {companyId=",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "WARN", loggerClass = BaseStore.class
	)
	@Test
	public void testLogFailedDeletion() {
		store.deleteFile(
			companyId, repositoryId, RandomTestUtil.randomString());
	}

	@ExpectedLogs(
		expectedLogs = {
			@ExpectedLog(
				expectedLog = "Unable to delete file {companyId=",
				expectedType = ExpectedType.PREFIX
			)
		},
		level = "WARN", loggerClass = BaseStore.class
	)
	@Test
	public void testLogFailedDeletionWithVersionLabel() {
		store.deleteFile(
			companyId, repositoryId, RandomTestUtil.randomString(),
			Store.VERSION_DEFAULT);
	}

	@Test
	public void testUpdateFileVersion() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFileVersion(
			companyId, repositoryId, fileName, "1.0", "1.1");

		Assert.assertArrayEquals(
			_DATA_VERSION_1,
			store.getFileAsBytes(companyId, repositoryId, fileName, "1.1"));
	}

	@Test(expected = DuplicateFileException.class)
	public void testUpdateFileVersionDuplicateFileException() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFileVersion(
			companyId, repositoryId, fileName, "1.0", "1.0");
	}

	@Test(expected = NoSuchFileException.class)
	public void testUpdateFileVersionNoSuchFileException() throws Exception {
		store.updateFileVersion(
			companyId, repositoryId, RandomTestUtil.randomString(),
			Store.VERSION_DEFAULT, Store.VERSION_DEFAULT);
	}

	@Test
	public void testUpdateFileVersionWithNewFileName() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		addVersions(fileName, 2);

		String newFileName = RandomTestUtil.randomString();

		store.updateFile(companyId, repositoryId, fileName, newFileName);

		Assert.assertFalse(store.hasFile(companyId, repositoryId, fileName));
		Assert.assertTrue(store.hasFile(companyId, repositoryId, newFileName));

		Assert.assertTrue(
			store.hasFile(companyId, repositoryId, newFileName, "1.0"));
		Assert.assertTrue(
			store.hasFile(companyId, repositoryId, newFileName, "1.1"));
		Assert.assertTrue(
			store.hasFile(companyId, repositoryId, newFileName, "1.2"));
	}

	@Test
	public void testUpdateFileWithByteArray() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFile(
			companyId, repositoryId, fileName, "1.1", _DATA_VERSION_2);

		byte[] firstVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		Assert.assertArrayEquals(_DATA_VERSION_1, firstVersionBytes);

		byte[] secondVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertArrayEquals(_DATA_VERSION_2, secondVersionBytes);

		byte[] currentVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertArrayEquals(_DATA_VERSION_2, currentVersionBytes);
	}

	@Test
	public void testUpdateFileWithFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		File file = createFile(_DATA_VERSION_2);

		store.updateFile(companyId, repositoryId, fileName, "1.1", file);

		byte[] firstVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		Assert.assertArrayEquals(_DATA_VERSION_1, firstVersionBytes);

		byte[] secondVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertArrayEquals(_DATA_VERSION_2, secondVersionBytes);

		byte[] currentVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertArrayEquals(_DATA_VERSION_2, currentVersionBytes);
	}

	@Test
	public void testUpdateFileWithInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFile(
			companyId, repositoryId, fileName, "1.1",
			new ByteArrayInputStream(_DATA_VERSION_2));

		byte[] firstVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.0");

		Assert.assertArrayEquals(_DATA_VERSION_1, firstVersionBytes);

		byte[] secondVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName, "1.1");

		Assert.assertArrayEquals(_DATA_VERSION_2, secondVersionBytes);

		byte[] currentVersionBytes = store.getFileAsBytes(
			companyId, repositoryId, fileName);

		Assert.assertArrayEquals(_DATA_VERSION_2, currentVersionBytes);
	}

	@Test
	public void testUpdateFileWithNewFileName() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		String newFileName = RandomTestUtil.randomString();

		store.updateFile(companyId, repositoryId, fileName, newFileName);

		Assert.assertFalse(store.hasFile(companyId, repositoryId, fileName));
		Assert.assertTrue(store.hasFile(companyId, repositoryId, newFileName));
	}

	@Test(expected = DuplicateFileException.class)
	public void testUpdateFileWithNewFileNameDuplicateFileException()
		throws Exception {

		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFile(companyId, repositoryId, fileName, fileName);
	}

	@Test(expected = NoSuchFileException.class)
	public void testUpdateFileWithNewFileNameNoSuchFileException()
		throws Exception {

		store.updateFile(
			companyId, repositoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testUpdateFileWithNewRepositoryId() throws Exception {
		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		long newRepositoryId = RandomTestUtil.nextLong();

		store.updateFile(companyId, repositoryId, newRepositoryId, fileName);

		Assert.assertFalse(store.hasFile(companyId, repositoryId, fileName));
		Assert.assertTrue(store.hasFile(companyId, newRepositoryId, fileName));

		store.deleteDirectory(companyId, newRepositoryId, StringPool.SLASH);
	}

	@Test(expected = DuplicateFileException.class)
	public void testUpdateFileWithNewRepositoryIdDuplicateFileException()
		throws Exception {

		String fileName = RandomTestUtil.randomString();

		store.addFile(companyId, repositoryId, fileName, _DATA_VERSION_1);

		store.updateFile(companyId, repositoryId, repositoryId, fileName);
	}

	@Test(expected = NoSuchFileException.class)
	public void testUpdateFileWithNewRepositoryIdNoSuchFileException()
		throws Exception {

		store.updateFile(
			companyId, repositoryId, RandomTestUtil.nextLong(),
			RandomTestUtil.randomString());
	}

	protected void addVersions(String fileName, int newVersionCount)
		throws Exception {

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			store.updateFile(
				companyId, repositoryId, fileName, versionLabel + i,
				_DATA_VERSION_1);
		}
	}

	protected File createFile(byte[] fileData) throws IOException {
		File file = File.createTempFile("DBStoreTest-testFile", null);

		try (OutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(fileData);
		}

		return file;
	}

	protected abstract Store getStore();

	protected long companyId;
	protected long repositoryId;
	protected Store store;

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