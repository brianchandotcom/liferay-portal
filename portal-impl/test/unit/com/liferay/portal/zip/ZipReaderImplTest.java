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

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class ZipReaderImplTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		_expectedContent0 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_0));
		_expectedContent1 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_1));
		_expectedContent2 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_2));
		_expectedContent3 = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipReaderImplTest.class, _FILE_PATH_3));
	}

	@Test
	public void testConstructor() throws Exception {
		ZipReader zipReader1 = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		zipReader1.close();

		ZipReader zipReader2 = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsFile(
				getClass(), _ZIP_FILE_PATH));

		zipReader2.close();
	}

	@Test
	public void testGetEntries() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries = zipReader.getEntries();

		Assert.assertEquals(entries.toString(), 5, entries.size());
		Assert.assertEquals(_FILE_PATH_0, entries.get(0));
		Assert.assertEquals(_FILE_PATH_1, entries.get(1));
		Assert.assertEquals(_FILE_PATH_2, entries.get(2));
		Assert.assertEquals(_FILE_PATH_3, entries.get(3));
		Assert.assertEquals(_FILE_PATH_4, entries.get(4));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsByteArray() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertArrayEquals(
			_expectedContent0.getBytes(_UTF_8),
			zipReader.getEntryAsByteArray(_FILE_PATH_0));
		Assert.assertArrayEquals(
			_expectedContent1.getBytes(_UTF_8),
			zipReader.getEntryAsByteArray(_FILE_PATH_1));
		Assert.assertArrayEquals(
			_expectedContent2.getBytes(_UTF_8),
			zipReader.getEntryAsByteArray(_FILE_PATH_2));
		Assert.assertArrayEquals(
			_expectedContent3.getBytes(_UTF_8),
			zipReader.getEntryAsByteArray(_FILE_PATH_3));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsByteArrayThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray("foo.txt"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsByteArrayThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray("1"));
		Assert.assertNull(zipReader.getEntryAsByteArray("/1"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsByteArrayWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsByteArray(""));
		Assert.assertNull(zipReader.getEntryAsByteArray(null));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsInputStream() throws Exception {
		_testGetEntryAsInputStream(_expectedContent0, _FILE_PATH_0);
		_testGetEntryAsInputStream(_expectedContent1, _FILE_PATH_1);
		_testGetEntryAsInputStream(_expectedContent2, _FILE_PATH_2);
		_testGetEntryAsInputStream(_expectedContent3, _FILE_PATH_3);
	}

	@Test
	public void testGetEntryAsInputStreamThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream("foo.txt"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsInputStreamThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream("1"));
		Assert.assertNull(zipReader.getEntryAsInputStream("/1"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsInputStreamWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsInputStream(""));
		Assert.assertNull(zipReader.getEntryAsInputStream(null));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsString() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsFile(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertEquals(
			_expectedContent0, zipReader.getEntryAsString(_FILE_PATH_0));

		Assert.assertEquals(
			_expectedContent0, zipReader.getEntryAsString("/" + _FILE_PATH_0));

		Assert.assertEquals(
			_expectedContent1, zipReader.getEntryAsString(_FILE_PATH_1));

		Assert.assertEquals(
			_expectedContent2, zipReader.getEntryAsString(_FILE_PATH_2));

		Assert.assertEquals(
			_expectedContent3, zipReader.getEntryAsString(_FILE_PATH_3));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsStringThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString("foo.txt"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsStringThatIsADirectory() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString("1"));
		Assert.assertNull(zipReader.getEntryAsString("/1"));

		zipReader.close();
	}

	@Test
	public void testGetEntryAsStringWithEmptyName() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		Assert.assertNull(zipReader.getEntryAsString(""));
		Assert.assertNull(zipReader.getEntryAsString(null));

		zipReader.close();
	}

	@Test
	public void testGetFolderEntries() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries1 = zipReader.getFolderEntries("");

		Assert.assertNotNull(entries1);
		Assert.assertTrue(entries1.toString(), entries1.isEmpty());

		List<String> entries2 = zipReader.getFolderEntries("/");

		Assert.assertEquals(entries2.toString(), 1, entries2.size());
		Assert.assertEquals(_FILE_PATH_0, entries2.get(0));

		List<String> entries3 = zipReader.getFolderEntries("1");

		Assert.assertEquals(entries3.toString(), 2, entries3.size());
		Assert.assertEquals(_FILE_PATH_1, entries3.get(0));
		Assert.assertEquals(_FILE_PATH_4, entries3.get(1));

		List<String> entries4 = zipReader.getFolderEntries("1/2");

		Assert.assertEquals(entries4.toString(), 2, entries4.size());
		Assert.assertEquals(_FILE_PATH_2, entries4.get(0));
		Assert.assertEquals(_FILE_PATH_3, entries4.get(1));

		zipReader.close();
	}

	@Test
	public void testGetFolderEntriesThatDoesNotExist() throws Exception {
		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		List<String> entries = zipReader.getFolderEntries("foo");

		Assert.assertNotNull(entries);
		Assert.assertTrue(entries.toString(), entries.isEmpty());

		zipReader.close();
	}

	private void _testGetEntryAsInputStream(
			String expectedContent, String filePath)
		throws Exception {

		ZipReader zipReader = new ZipReaderImpl(
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ZIP_FILE_PATH));

		try (InputStream is = zipReader.getEntryAsInputStream(filePath)) {
			Assert.assertEquals(expectedContent, StreamUtil.toString(is));
		}

		zipReader.close();
	}

	private static final String _FILE_PATH_0 = "0.txt";

	private static final String _FILE_PATH_1 = "1/1.txt";

	private static final String _FILE_PATH_2 = "1/2/2.txt";

	private static final String _FILE_PATH_3 = "1/2/3.txt";

	private static final String _FILE_PATH_4 = "1/4.txt";

	private static final Charset _UTF_8 = Charset.forName("UTF-8");

	private static final String _ZIP_FILE_PATH = "file.zip";

	private static String _expectedContent0;
	private static String _expectedContent1;
	private static String _expectedContent2;
	private static String _expectedContent3;

}