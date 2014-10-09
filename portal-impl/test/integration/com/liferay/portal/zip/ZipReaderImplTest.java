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

package com.liferay.portal.zip;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;

import com.liferay.portal.util.test.DependenciesTestUtil;
import de.schlichtherle.io.FileInputStream;

import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ZipReaderImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_expectedContent_0 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_0));

		_expectedContent_1 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_1));

		_expectedContent_2 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_2));

		_expectedContent_3 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_3));
	}

	@Test
	public void testConstructors() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNotNull(zipReader);

		zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsFile(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNotNull(zipReader);
	}

	@Test
	public void testGetEntries() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(5, entries.size());
		Assert.assertEquals(_FILE_PATH_0, entries.get(0));
		Assert.assertEquals(_FILE_PATH_1, entries.get(1));
		Assert.assertEquals(_FILE_PATH_2, entries.get(2));
		Assert.assertEquals(_FILE_PATH_3, entries.get(3));
		Assert.assertEquals(_FILE_PATH_4, entries.get(4));
	}

	@Test
	public void testGetEntryAsByteArray() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		byte[] content = zipReader.getEntryAsByteArray(_FILE_PATH_0);

		Assert.assertArrayEquals(_expectedContent_0.getBytes(_UTF_8), content);

		content = zipReader.getEntryAsByteArray(_FILE_PATH_1);

		Assert.assertArrayEquals(_expectedContent_1.getBytes(_UTF_8), content);

		content = zipReader.getEntryAsByteArray(_FILE_PATH_2);

		Assert.assertArrayEquals(_expectedContent_2.getBytes(_UTF_8), content);

		content = zipReader.getEntryAsByteArray(_FILE_PATH_3);

		Assert.assertArrayEquals(_expectedContent_3.getBytes(_UTF_8), content);
	}

	@Test
	public void testGetEntryAsByteArrayThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray("foo.txt"));
	}

	@Test
	public void testGetEntryAsByteArrayThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray("1"));
		Assert.assertNull(zipReader.getEntryAsByteArray("/1"));
	}

	@Test
	public void testGetEntryAsByteArrayWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray(""));
		Assert.assertNull(zipReader.getEntryAsByteArray(null));
	}

	@Test
	public void testGetEntryAsInputStream() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		InputStream is = zipReader.getEntryAsInputStream(_FILE_PATH_0);

		Assert.assertTrue(is instanceof FileInputStream);

		is = zipReader.getEntryAsInputStream(_FILE_PATH_1);

		Assert.assertTrue(is instanceof FileInputStream);

		is = zipReader.getEntryAsInputStream(_FILE_PATH_2);

		Assert.assertTrue(is instanceof FileInputStream);

		is = zipReader.getEntryAsInputStream(_FILE_PATH_3);

		Assert.assertTrue(is instanceof FileInputStream);
	}

	@Test
	public void testGetEntryAsInputStreamThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream("foo.txt"));
	}

	@Test
	public void testGetEntryAsInputStreamThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream("1"));
		Assert.assertNull(zipReader.getEntryAsInputStream("/1"));
	}

	@Test
	public void testGetEntryAsInputStreamWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream(""));
		Assert.assertNull(zipReader.getEntryAsInputStream(null));
	}

	@Test
	public void testGetEntryAsString() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsFile(
				getClass(), _ZIP_FILE_PATH));

		String content = zipReader.getEntryAsString(_FILE_PATH_0);

		Assert.assertEquals(_expectedContent_0, content);

		content = zipReader.getEntryAsString("/" + _FILE_PATH_0);

		Assert.assertEquals(_expectedContent_0, content);

		content = zipReader.getEntryAsString(_FILE_PATH_1);

		Assert.assertEquals(_expectedContent_1, content);

		content = zipReader.getEntryAsString(_FILE_PATH_2);

		Assert.assertEquals(_expectedContent_2, content);

		content = zipReader.getEntryAsString(_FILE_PATH_3);

		Assert.assertEquals(_expectedContent_3, content);
	}

	@Test
	public void testGetEntryAsStringThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString("foo.txt"));
	}

	@Test
	public void testGetEntryAsStringThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString("1"));
		Assert.assertNull(zipReader.getEntryAsString("/1"));
	}

	@Test
	public void testGetEntryAsStringWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString(""));
		Assert.assertNull(zipReader.getEntryAsString(null));
	}

	@Test
	public void testGetFolderEntries() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries = zipReader.getFolderEntries("");

		Assert.assertNotNull(entries);
		Assert.assertTrue(entries.isEmpty());

		entries = zipReader.getFolderEntries("/");

		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(_FILE_PATH_0, entries.get(0));

		entries = zipReader.getFolderEntries("1");

		Assert.assertEquals(2, entries.size());
		Assert.assertEquals(_FILE_PATH_1, entries.get(0));
		Assert.assertEquals(_FILE_PATH_4, entries.get(1));

		entries = zipReader.getFolderEntries("1/2");

		Assert.assertEquals(2, entries.size());
		Assert.assertEquals(_FILE_PATH_2, entries.get(0));
		Assert.assertEquals(_FILE_PATH_3, entries.get(1));
	}

	@Test
	public void testGetFolderEntriesThatDoesNotExists() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries = zipReader.getFolderEntries("foo");

		Assert.assertNotNull(entries);
		Assert.assertTrue(entries.isEmpty());
	}

	private static final String _FILE_PATH_0 = "0.txt";

	private static final String _FILE_PATH_1 = "1/1.txt";

	private static final String _FILE_PATH_2 = "1/2/2.txt";

	private static final String _FILE_PATH_3 = "1/2/3.txt";

	private static final String _FILE_PATH_4 = "1/4.txt";

	private static final Charset _UTF_8 = Charset.forName("UTF-8");

	/**
	 * file.zip contains a zip file which contains the structure under it
	 * dependencies
	 */
	private static final String _ZIP_FILE_PATH = "file.zip";

	private static String _expectedContent_0;
	private static String _expectedContent_1;
	private static String _expectedContent_2;
	private static String _expectedContent_3;

}