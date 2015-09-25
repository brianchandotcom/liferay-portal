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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
public class DLFileEntryExtensionTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testAddFileEntryBasic01() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, "Test.pdf", "txt", "Test.pdf.txt");
	}

	@Test
	public void testAddFileEntryBasic02() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic03() throws Exception {
		testAddFileEntryBasic(
			_FILE_NAME, _STRIPPED_FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic04() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, "", "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic05() throws Exception {
		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic06() throws Exception {
		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic07() throws Exception {
		testAddFileEntryBasic(_STRIPPED_FILE_NAME, "", "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic08() throws Exception {
		testAddFileEntryBasic("", _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic09() throws Exception {
		testAddFileEntryBasic("", _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryBasic10() throws Exception {
		try {
			addFileEntry("", "");

			Assert.fail(
				"Created document with blank source file name and blank title");
		}
		catch (FileNameException fne) {
		}
	}

	@Test
	public void testAddFileEntryFalsePositives() throws Exception {

		// "Test.txt" / "Test.txt" followed by "Test" / "Test"

		FileEntry fileEntry = addFileEntry(_FILE_NAME, _FILE_NAME);

		FileEntry tempFileEntry = addFileEntry(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		DLAppLocalServiceUtil.deleteFileEntry(tempFileEntry.getFileEntryId());

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

		// "Test" / "Test" followed by "Test.txt" / "Test.txt"

		fileEntry = addFileEntry(_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		tempFileEntry = addFileEntry(_FILE_NAME, _FILE_NAME);

		DLAppLocalServiceUtil.deleteFileEntry(tempFileEntry.getFileEntryId());

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(_FILE_NAME, _FILE_NAME);

		// "Test.txt" / "Test"

		try {
			addFileEntry(_FILE_NAME, _STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry("Temp.txt", "Temp");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), _FILE_NAME,
				ContentTypes.TEXT_PLAIN, _STRIPPED_FILE_NAME, StringPool.BLANK,
				StringPool.BLANK, false,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test.txt" / ""

		try {
			addFileEntry(_FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		tempFileEntry = addFileEntry("Temp.txt", "");

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), _FILE_NAME,
				ContentTypes.TEXT_PLAIN, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "Test" / "Test.txt"

		try {
			addFileEntry(_STRIPPED_FILE_NAME, _FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		tempFileEntry = addFileEntry( "Temp", "Temp.txt");

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME,
				ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
				StringPool.BLANK, false,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test.txt"

		try {
			addFileEntry("", _FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		tempFileEntry = addFileEntry("", "Temp.txt");

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), StringPool.BLANK,
				ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
				StringPool.BLANK, false, (byte[])null, serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Test
	public void testAddFileEntryWithoutExtension() throws Exception {
		FileEntry fileEntry = addFileEntry(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		// "Test" / ""

		try {
			addFileEntry(_STRIPPED_FILE_NAME, "");

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		FileEntry tempFileEntry = addFileEntry("Temp", "");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), _STRIPPED_FILE_NAME,
				ContentTypes.TEXT_PLAIN, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, false,
				RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
				serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		// "" / "Test"

		try {
			addFileEntry("", _STRIPPED_FILE_NAME);

			Assert.fail("Created" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}

		tempFileEntry = addFileEntry("", "Temp");

		try {
			DLAppServiceUtil.updateFileEntry(
				tempFileEntry.getFileEntryId(), StringPool.BLANK,
				ContentTypes.TEXT_PLAIN, _STRIPPED_FILE_NAME, StringPool.BLANK,
				StringPool.BLANK, false, (byte[])null, serviceContext);

			Assert.fail("Renamed" + _FAIL_DUPLICATE_MESSAGE_SUFFIX);
		}
		catch (DuplicateFileEntryException dfe) {
		}
		finally {
			DLAppLocalServiceUtil.deleteFileEntry(
				tempFileEntry.getFileEntryId());
		}

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected FileEntry addFileEntry(String sourceFileName, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			parentFolder.getFolderId(), sourceFileName, ContentTypes.TEXT_PLAIN,
			title, StringPool.BLANK, StringPool.BLANK,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

	protected void testAddFileEntryBasic(
			String sourceFileName, String title, String extension,
			String titleWithExtension)
		throws Exception {

		FileEntry fileEntry = addFileEntry(sourceFileName, title);

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