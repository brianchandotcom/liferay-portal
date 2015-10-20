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

import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class ZipWriterImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_expectedEntryContent = StringUtil.read(
			DependenciesTestUtil.getDependencyAsInputStream(
				ZipWriterImplTest.class, _ENTRY_FILE_PATH));
	}

	@Before
	public void setUp() throws IOException {
		Path tempZipFilePath = Files.createTempFile(
			Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)), "-file",
			".zip");

		_tempZipFile = tempZipFilePath.toFile();
	}

	@Test
	public void testAddEntryFromBytes() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		File dependencyFile = DependenciesTestUtil.getDependencyAsFile(
			getClass(), _ENTRY_FILE_PATH);

		zipWriter.addEntry(_ENTRY_FILE_PATH, FileUtil.getBytes(dependencyFile));

		File file = zipWriter.getFile();

		Assert.assertTrue(file.exists());

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromBytesThatAreEmpty() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		zipWriter.addEntry("empty.txt", new byte[0]);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromInputStream() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		zipWriter.addEntry(
			_ENTRY_FILE_PATH,
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ENTRY_FILE_PATH));

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromInputStreamThatIsNull() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		InputStream nullableInputStream = null;

		zipWriter.addEntry("null.txt", nullableInputStream);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromInputStreamThatStartsWithSlash()
		throws Exception {

		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		zipWriter.addEntry(
			"/" + _ENTRY_FILE_PATH,
			DependenciesTestUtil.getDependencyAsInputStream(
				getClass(), _ENTRY_FILE_PATH));

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromString() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		zipWriter.addEntry("string.txt", "This is a string.");

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			"This is a string.", zipReader.getEntryAsString("string.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromStringBuilder() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		StringBuilder sb = new StringBuilder();

		sb.append("This is a string.");

		zipWriter.addEntry("string.txt", sb);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals(
			"This is a string.", zipReader.getEntryAsString("string.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsEmpty() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		StringBuilder sb = new StringBuilder();

		zipWriter.addEntry("empty.txt", sb);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsNull() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		StringBuilder sb = null;

		zipWriter.addEntry("null.txt", sb);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromStringThatIsEmpty() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		String string = "";

		zipWriter.addEntry("empty.txt", string);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testAddEntryFromStringThatIsNull() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		String string = null;

		zipWriter.addEntry("null.txt", string);

		File file = zipWriter.getFile();

		ZipReader zipReader = new ZipReaderImpl(file);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));

		zipReader.close();

		file.delete();
	}

	@Test
	public void testConstructor() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		Assert.assertNotNull(zipWriter);

		File file = zipWriter.getFile();

		Assert.assertNotNull(file);

		file.delete();

		zipWriter = new ZipWriterImpl(_tempZipFile);

		Assert.assertNotNull(zipWriter);

		file = zipWriter.getFile();

		Assert.assertNotNull(file);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(_tempZipFile.getPath(), file.getPath());

		file.delete();
	}

	@Test
	public void testFinish() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		StringBuilder sb = new StringBuilder();

		sb.append("This is a string.");

		zipWriter.addEntry("string.txt", sb);

		byte[] bytes = zipWriter.finish();

		Assert.assertArrayEquals(FileUtil.getBytes(_tempZipFile), bytes);

		File file = zipWriter.getFile();

		file.delete();
	}

	/**
	 * Tests that {@link ZipWriter#finish()} can execute without error on a ZIP
	 * writer that's been created by the default constructor and that has no
	 * entries.
	 *
	 * @throws Exception
	 */
	@Test
	public void testFinishIfZipFileIsNotSet() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		zipWriter.finish();

		File file = zipWriter.getFile();

		file.delete();
	}

	/**
	 * Tests that {@link ZipWriter#finish()} can execute without error on a ZIP
	 * writer that's been created for an existing ZIP file and that has no
	 * entries.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Test
	public void testFinishIfZipFileIsSet() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl(_tempZipFile);

		zipWriter.finish();

		File file = zipWriter.getFile();

		file.delete();
	}

	private static final String _ENTRY_FILE_PATH = "entry.txt";

	private static String _expectedEntryContent;

	private File _tempZipFile;

}