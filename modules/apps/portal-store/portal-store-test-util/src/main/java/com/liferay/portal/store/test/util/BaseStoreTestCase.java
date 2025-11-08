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
		companyId = RandomTestUtil.nextLong();
		repositoryId = RandomTestUtil.nextLong();
		_store = getStore();
	}

	@After
	public void tearDown() {
		_store.deleteDirectory(companyId, repositoryId, StringPool.SLASH);
	}

	@Test
	public void testAddFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		Assert.assertTrue(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		_store.deleteDirectory(companyId, repositoryId, dirName);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteDirectoryWithTwoLevelDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		_store.deleteDirectory(companyId, repositoryId, dirName);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		addVersions(fileName, 1);

		_store.deleteFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_store.hasFile(companyId, repositoryId, fileName, "1.1"));
	}

	@Test
	public void testGetFileAsStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		addVersions(fileName, 5);

		try (InputStream inputStream = _store.getFileAsStream(
				companyId, repositoryId, fileName, "1.5")) {

			for (int i = 0; i < _DATA_SIZE; i++) {
				Assert.assertEquals(
					DATA_VERSION_1[i], (byte)inputStream.read());
			}

			Assert.assertEquals(-1, inputStream.read());
		}
	}

	@Test
	public void testGetFileNames() throws Exception {
		String fileName1 = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, StringPool.BLANK);

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
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
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

		_store.addFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName3 =
			RandomTestUtil.randomString() + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName3, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 2, fileNames.length);

		Set<String> fileNamesSet = SetUtil.fromArray(fileNames);

		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName1));
		Assert.assertTrue(
			fileNamesSet.toString(), fileNamesSet.contains(fileName2));

		fileNames = _store.getFileNames(companyId, repositoryId, subdirName);

		Assert.assertEquals(Arrays.toString(fileNames), 1, fileNames.length);
		Assert.assertEquals(fileName2, fileNames[0]);
	}

	@Test
	public void testGetFileNamesWithInvalidDirectory() {
		String dirName = RandomTestUtil.randomString();

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, dirName);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithInvalidRepository() {
		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, StringPool.BLANK);

		Assert.assertEquals(Arrays.toString(fileNames), 0, fileNames.length);
	}

	@Test
	public void testGetFileNamesWithTwoLevelsDeep() throws Exception {
		String dirName = RandomTestUtil.randomString();

		String subdirName = dirName + "/" + RandomTestUtil.randomString();

		String fileName1 = dirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName1, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String fileName2 = subdirName + "/" + RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName2, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, StringPool.BLANK);

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
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		long size = _store.getFileSize(
			companyId, repositoryId, fileName, StringPool.BLANK);

		Assert.assertEquals(_DATA_SIZE, size);
	}

	@Test(expected = NoSuchFileException.class)
	public void testGetFileSizeNoSuchFileException() throws Exception {
		_store.getFileSize(
			companyId, repositoryId, RandomTestUtil.randomString(),
			StringPool.BLANK);
	}

	@Test
	public void testGetFileVersions() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		addVersions(fileName, 5);

		String[] fileVersions = _store.getFileVersions(
			companyId, repositoryId, fileName);

		for (int i = 0; i < 5; i++) {
			Assert.assertEquals("1." + i, fileVersions[i]);
		}
	}

	@Test
	public void testHasFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		_store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(DATA_VERSION_1));

		addVersions(fileName, 5);

		String versionLabel = "1.";

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(
				_store.hasFile(
					companyId, repositoryId, fileName, versionLabel + i));
		}
	}

	protected void addVersions(String fileName, int newVersionCount)
		throws Exception {

		String versionLabel = "1.";

		for (int i = 1; i <= newVersionCount; i++) {
			_store.addFile(
				companyId, repositoryId, fileName, versionLabel + i,
				new UnsyncByteArrayInputStream(DATA_VERSION_1));
		}
	}

	protected abstract Store getStore();

	protected static final byte[] DATA_VERSION_1 =
		new byte[BaseStoreTestCase._DATA_SIZE];

	protected long companyId;
	protected long repositoryId;

	private static final int _DATA_SIZE = 1024 * 65;

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			DATA_VERSION_1[i] = (byte)i;
		}
	}

	private Store _store;

}