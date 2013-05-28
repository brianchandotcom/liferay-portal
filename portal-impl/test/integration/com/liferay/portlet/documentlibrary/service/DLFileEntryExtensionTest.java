/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This JUnit test case takes into consideration all possible permutations of
 * file names and extensions that can be added into the document library. It
 * verifies that the correct validations occur and the correct title with
 * extension can be generated.
 *
 * <p>
 * <table>
 * <tr>
 * <th>
 * Source
 * </th>
 * <th>
 * Title
 * </th>
 * <th>
 * Extension
 * </th>
 * <th>
 * Download Title
 * </th>
 * </tr>
 *
 * <tr>
 * <td>
 * Text.txt
 * </td>
 * <td>
 * Text.pdf
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Text.pdf.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * Test
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test
 * </td>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test
 * </td>
 * <td>
 * Test
 * </td>
 * <td>
 * </td>
 * <td>
 * Test
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * Test
 * </td>
 * <td>
 * </td>
 * <td>
 * </td>
 * <td>
 * Test
 * </td>
 * </tr>
 * <tr>
 * <td>
 * </td>
 * <td>
 * Test.txt
 * </td>
 * <td>
 * txt
 * </td>
 * <td>
 * Test.txt
 * </td>
 * </tr>
 *
 * <tr>
 * <td>
 * </td>
 * <td>
 * Test
 * </td>
 * <td>
 * </td>
 * <td>
 * Test
 * </td>
 * </tr>
 * </table>
 * </p>
 *
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryExtensionTest extends BaseDLAppTestCase {

	@Test
	public void testAddFileEntryBasic01() throws Exception {
		addFileEntryBasic(_FILE_NAME, "Test.pdf", "txt", "Test.pdf.txt");
	}

	@Test
	public void testAddFileEntryBasic02() throws Exception {
		addFileEntryBasic(_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic03() throws Exception {
		addFileEntryBasic(_FILE_NAME, _STRIPPED_FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic04() throws Exception {
		addFileEntryBasic(_FILE_NAME, "", "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic05() throws Exception {
		addFileEntryBasic(_STRIPPED_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic06() throws Exception {
		addFileEntryBasic(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic07() throws Exception {
		addFileEntryBasic(_STRIPPED_FILE_NAME, "", "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic08() throws Exception {
		addFileEntryBasic("", _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic09() throws Exception {
		addFileEntryBasic("", _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic10() throws Exception {
		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false, "", "");

			Assert.fail(
				"Created document with blank source file name and blank title");
		}
		catch (FileNameException fne) {
		}
	}

	@Test
	public void testAddFileEntryFalsePositives() throws Exception {

		// "Test.txt" / "Test.txt" followed by "Test" / "Test"

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, _FILE_NAME,
			_FILE_NAME);

		try {
			FileEntry tempFileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail("Unable to create" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		// "Test" / "Test" followed by "Test.txt" / "Test.txt"

		fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false,
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		try {
			FileEntry tempFileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_FILE_NAME, _FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail("Unable to create" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithExtension() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, _FILE_NAME,
			_FILE_NAME);

		// "Test.txt" / "Test"

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_FILE_NAME, _STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "Temp.txt",
			"Temp");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test.txt" / ""

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "Temp.txt",
			"");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(), _FILE_NAME,
				"");

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test" / "Test.txt"

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_STRIPPED_FILE_NAME, _FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "Temp",
			"Temp.txt");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(),
				_STRIPPED_FILE_NAME, _FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test.txt"

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false, "",
				_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "",
			"Temp.txt");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(), "",
				_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithoutExtension() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false,
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		// "Test" / ""

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false,
				_STRIPPED_FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "Temp", "");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(),
				_STRIPPED_FILE_NAME, "");

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test"

		try {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), false, "",
				_STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "", "Temp");

		try {
			DLAppTestUtil.updateFileEntry(
				group.getGroupId(), tempFileEntry.getFileEntryId(), "",
				_STRIPPED_FILE_NAME);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected void addFileEntryBasic(
			String sourceFileName, String title, String extension,
			String titleWithExtension)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false,
			sourceFileName, title);

		Assert.assertEquals(
			"Invalid file extension", extension, fileEntry.getExtension());

		Assert.assertEquals(
			titleWithExtension, DLUtil.getTitleWithExtension(fileEntry));

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	private static final String _FAIL_DUPLICATE_MESSAGE_SUFFIX =
		" a file on top of one with the same title and extension";

	private static final String _FILE_NAME = "Test.txt";

	private static final String _STRIPPED_FILE_NAME = "Test";

}