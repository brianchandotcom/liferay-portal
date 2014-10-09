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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ZipWriterImplTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() {
		_tempZipFilePath = new Date().getTime() + "-file.zip";
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_expectedEntryContent = StringUtil.read(
			_getDependencyAsInputStream(_ENTRY_FILE_PATH));
	}

	@Test
	public void testAddEntryFromBytes() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		File entry = _getDependencyAsFile(_ENTRY_FILE_PATH);

		zipWriter.addEntry(_ENTRY_FILE_PATH, FileUtil.getBytes(entry));

		File file = zipWriter.getFile();

		File tempFile = new File(file.getPath());

		Assert.assertTrue(tempFile.exists());

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));
	}

	@Test
	public void testAddEntryFromBytesThatAreEmpty() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		byte[] emptyBytes = new byte[0];

		zipWriter.addEntry("empty.txt", emptyBytes);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));
	}

	@Test
	public void testAddEntryFromInputStream() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.addEntry(
			_ENTRY_FILE_PATH, _getDependencyAsInputStream(_ENTRY_FILE_PATH));

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals(
			_expectedEntryContent,
			zipReader.getEntryAsString(_ENTRY_FILE_PATH));
	}

	@Test
	public void testAddEntryFromInputStreamThatIsNull() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		InputStream nullableInputStream = null;

		zipWriter.addEntry("null.txt", nullableInputStream);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));
	}

	@Test
	public void testAddEntryFromString() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.addEntry("string.txt", "This is a String");

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals(
			"This is a String", zipReader.getEntryAsString("string.txt"));
	}

	@Test
	public void testAddEntryFromStringBuilder() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder sb = new StringBuilder();

		sb.append("This is a String");

		zipWriter.addEntry("string.txt", sb);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals(
			"This is a String", zipReader.getEntryAsString("string.txt"));
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsEmpty() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder emptyStringBuilder = new StringBuilder();

		zipWriter.addEntry("empty.txt", emptyStringBuilder);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));
	}

	@Test
	public void testAddEntryFromStringBuilderThatIsNull() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		StringBuilder nullStringBuilder = null;

		zipWriter.addEntry("null.txt", nullStringBuilder);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));
	}

	@Test
	public void testAddEntryFromStringThatIsEmpty() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		String emptyString = "";

		zipWriter.addEntry("empty.txt", emptyString);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertEquals("", zipReader.getEntryAsString("empty.txt"));
	}

	@Test
	public void testAddEntryFromStringThatIsNull() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		String nullString = null;

		zipWriter.addEntry("null.txt", nullString);

		ZipReader zipReader = new ZipReaderImpl(tempZipFile);

		Assert.assertNull(zipReader.getEntryAsString("null.txt"));
	}

	@Test
	public void testConstructor() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		Assert.assertNotNull(zipWriter);
		Assert.assertNotNull(zipWriter.getFile());

		File zipFile = temporaryFolder.newFile(_tempZipFilePath);

		zipWriter = new ZipWriterImpl(zipFile);

		Assert.assertNotNull(zipWriter);

		File file = zipWriter.getFile();

		Assert.assertNotNull(file);
		Assert.assertTrue(file.exists());
		Assert.assertEquals(zipFile.getPath(), file.getPath());
	}

	@Test
	public void testFinish() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		byte[] bytes = zipWriter.finish();

		Assert.assertArrayEquals(FileUtil.getBytes(tempZipFile), bytes);
	}

	/**
	 * Throws an ArchiveFileNotFoundException if and only if this file is a
	 * true archive file, not just a false positive, including RAES
	 * encrypted ZIP files for which key prompting has been cancelled or
	 * disabled.
	 */
	@Test(expected = FileNotFoundException.class)
	public void testFinishIfZipFileIsTrueArchiveFile() throws Exception {
		File tempZipFile = temporaryFolder.newFile(_tempZipFilePath);

		FileUtil.copyFile(_getDependencyAsFile(_ZIP_FILE_PATH), tempZipFile);

		ZipWriter zipWriter = new ZipWriterImpl(tempZipFile);

		zipWriter.finish();
	}

	@Test(expected = IOException.class)
	public void testFinishIfZipFileIsNotSet() throws Exception {
		ZipWriter zipWriter = new ZipWriterImpl();

		zipWriter.finish();
	}

	private static File _getDependencyAsFile(String name) throws Exception {
		URL url = ZipReaderImplTest.class.getResource("dependencies/" + name);

		return new File(url.toURI());
	}

	private static InputStream _getDependencyAsInputStream(String name)
		throws Exception {

		return ZipReaderImplTest.class.getResourceAsStream(
			"dependencies/" + name);
	}

	private static final String _ENTRY_FILE_PATH = "entry.txt";

	private static final String _ZIP_FILE_PATH = "file.zip";

	private static String _expectedEntryContent;

	private String _tempZipFilePath;

}