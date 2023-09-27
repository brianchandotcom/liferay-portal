/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.test.util;

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public abstract class BaseStoreTestCase {

	@Before
	public void setUp() {
		_companyId = RandomTestUtil.nextLong();
		_repositoryId = RandomTestUtil.nextLong();
		_store = getStore();
	}

	@After
	public void tearDown() {
		_store.deleteDirectory(_companyId, _repositoryId, StringPool.SLASH);
	}

	@Test
	public void testAddFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		_store.deleteDirectory(_companyId, _repositoryId, dirName);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectoryWithTwoLevelDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		_store.deleteDirectory(_companyId, _repositoryId, dirName);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		addVersions(fileName, 1);

		_store.deleteFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			_store.hasFile(
				_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(_companyId, _repositoryId, fileName, "1.1"));
	}

	@Test
	public void testGetFileAsStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		addVersions(fileName, 5);

		try (InputStream inputStream = _store.getFileAsStream(
				_companyId, _repositoryId, fileName, "1.5")) {

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

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, StringPool.BLANK);

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

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, dirName);

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

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName3 =
			RandomTestUtil.randomString() + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName3, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));

		fileNames = _store.getFileNames(_companyId, _repositoryId, subdirName);

		Assert.assertEquals(Arrays.toString(fileNames), 1, fileNames.length);
		Assert.assertEquals(fileName2, fileNames[0]);
	}

	@Test
	public void testGetFileNamesWithInvalidDirectory() {
		String dirName = RandomTestUtil.randomString();

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithInvalidRepository() {
		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, StringPool.BLANK);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithTwoLevelsDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			_companyId, _repositoryId, StringPool.BLANK);

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

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		long size = _store.getFileSize(
			_companyId, _repositoryId, fileName, StringPool.BLANK);

		Assert.assertEquals(_DATA_SIZE, size);
	}

	@Test(expected = NoSuchFileException.class)
	public void testGetFileSizeNoSuchFileException() throws Exception {
		_store.getFileSize(
			_companyId, _repositoryId, RandomTestUtil.randomString(),
			StringPool.BLANK);
	}

	@Test
	public void testGetFileVersions() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		addVersions(fileName, 5);

		String[] fileVersions = _store.getFileVersions(
			_companyId, _repositoryId, fileName);

		for (int i = 0; i < 5; i++) {
			Assert.assertEquals("1." + i, fileVersions[i]);
		}
	}

	@Test
	public void testHasFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			_companyId, _repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(_DATA_VERSION_1));

		addVersions(fileName, 5);

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(
				_store.hasFile(
					_companyId, _repositoryId, fileName, versionLabel + i));
		}
	}

	protected void addVersions(String fileName, int newVersionCount)
		throws Exception {

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_store.addFile(
				_companyId, _repositoryId, fileName, versionLabel + i,
				new UnsyncByteArrayInputStream(_DATA_VERSION_1));
		}
	}

	protected abstract Store getStore();

	private static final int _DATA_SIZE = 1024 * 65;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];

	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE];

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}
	}

	private long _companyId;
	private long _repositoryId;
	private Store _store;

}