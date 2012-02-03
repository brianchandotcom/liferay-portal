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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

/**
 * This JUnit takes into consideration all possible permutations of file names
 * and extensions that can be added into the document library. It verifies that
 * the correct validations occur and the correct title with extension can be
 * generated.
 *
 * <table>
 *   <tr>
 *     <th>Source</th><th>Title</th><th>Extension</th><th>Download Title</th>
 *   </tr>
 *   <tr>
 *     <td>Text.txt</td><td>Text.pdf</td><td>txt</td><td>Text.pdf.txt</td>
 *   </tr>
 *   <tr>
 *     <td>Test.txt</td><td>Test.txt</td><td>txt</td><td>Test.txt</td>
 *   </tr>
 *   <tr>
 *     <td>Test.txt</td><td>Test</td><td>txt</td><td>Test.txt</td>
 *   </tr>
 *   <tr>
 *     <td>Test.txt</td><td></td><td>txt</td><td>Test.txt</td>
 *   </tr>
 *   <tr>
 *     <td>Test</td><td>Test.txt</td><td>txt</td><td>Test.txt</td>
 *   </tr>
 *   <tr>
 *     <td>Test</td><td>Test</td><td></td><td>Test</td>
 *   </tr>
 *   <tr>
 *     <td>Test</td><td></td><td></td><td>Test</td>
 *   </tr>
 *   <tr>
 *     <td></td><td>Test.txt</td><td>txt</td><td>Test.txt</td>
 *   </tr>
 *   <tr>
 *     <td></td><td>Test</td><td></td><td>Test</td>
 *   </tr>
 * </table>
 *
 * @author Alexander Chow
 */
public class DLFileExtensionTest extends BaseDLAppTestCase {

	public void testAddBasic() throws Exception {
		validateAddFileEntry(_FILE_NAME, "Test.pdf", "txt", "Test.pdf.txt");
		validateAddFileEntry(_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
		validateAddFileEntry(
			_FILE_NAME, _STRIPPED_FILE_NAME, "txt", _FILE_NAME);
		validateAddFileEntry(_FILE_NAME, "", "txt", _FILE_NAME);
		validateAddFileEntry(
			_STRIPPED_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
		validateAddFileEntry(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
		validateAddFileEntry(_STRIPPED_FILE_NAME, "", "", _STRIPPED_FILE_NAME);
		validateAddFileEntry("", _FILE_NAME, "txt", _FILE_NAME);
		validateAddFileEntry("", _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);

		try {
			addFileEntry(false, "", "");

			fail("Created document with blank sourceFileName and blank title");
		}
		catch (FileNameException fne) {
		}
	}

	public void testAddFalsePositives() throws Exception {

		// "Test.txt" / "Test.txt" followed by "Test" / "Test"

		FileEntry fileEntry = addFileEntry(false, _FILE_NAME, _FILE_NAME);

		try {
			FileEntry tempFileEntry = addFileEntry(
				false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			fail("Could not create" + _FAIL_DUPLICATE_MESSAGE);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		// "Test" / "Test" followed by "Test.txt" / "Test.txt"

		fileEntry = addFileEntry(
			false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		try {
			FileEntry tempFileEntry = addFileEntry(
				false, _FILE_NAME, _FILE_NAME);

			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			fail("Could not create" + _FAIL_DUPLICATE_MESSAGE);
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	public void testAddFileWithExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(false, _FILE_NAME, _FILE_NAME);

		// "Test.txt" / "Test"

		try {
			addFileEntry(false, _FILE_NAME, _STRIPPED_FILE_NAME);

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry(false, "Temp.txt", "Temp");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test.txt" / ""

		try {
			addFileEntry(false, _FILE_NAME, "");

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "Temp.txt", "");

		try {
			updateFileEntry(tempFileEntry.getFileEntryId(), _FILE_NAME, "");

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test" / "Test.txt"

		try {
			addFileEntry(false, _STRIPPED_FILE_NAME, _FILE_NAME);

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "Temp", "Temp.txt");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME,
				_FILE_NAME);

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test.txt"

		try {
			addFileEntry(false, "", _FILE_NAME);

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "", "Temp.txt");

		try {
			updateFileEntry(tempFileEntry.getFileEntryId(), "", _FILE_NAME);

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// Cleanup

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	public void testAddFileWithoutExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(
			false, _STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		// "Test" / ""

		try {
			addFileEntry(false, _STRIPPED_FILE_NAME, "");

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry(false, "Temp", "");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME, "");

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe2) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test"

		try {
			addFileEntry(false, "", _STRIPPED_FILE_NAME);

			fail("Created" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}

		tempFileEntry = addFileEntry(false, "", "Temp");

		try {
			updateFileEntry(
				tempFileEntry.getFileEntryId(), "", _STRIPPED_FILE_NAME);

			fail("Renamed" + _FAIL_DUPLICATE_MESSAGE);
		}
		catch (DuplicateFileException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// Cleanup

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected void validateAddFileEntry(
			String sourceFileName, String title, String extension,
			String titleWithExtension)
		throws Exception {

		FileEntry fileEntry = addFileEntry(false, sourceFileName, title);

		assertEquals(
			"File extension invalid", extension, fileEntry.getExtension());

		assertEquals(
			titleWithExtension, DLUtil.getTitleWithExtension(fileEntry));

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	private static final String _FAIL_DUPLICATE_MESSAGE =
		" a file on top of one with the same title and extension";

	private static final String _FILE_NAME = "Test.txt";

	private static final String _STRIPPED_FILE_NAME = "Test";

}