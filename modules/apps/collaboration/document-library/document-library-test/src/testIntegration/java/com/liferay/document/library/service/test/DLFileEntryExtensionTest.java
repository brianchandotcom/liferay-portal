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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.service.test.BaseDLAppTestCase;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
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
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryExtensionTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test(expected = DuplicateFileEntryException.class)
	public void testAddDuplicateFileEntryFileNameEqualToTitleWithoutExtension()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);
		addFileEntry(_STRIPPED_FILE_NAME, _FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testAddDuplicateFileEntryTitleEqualToFileName()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);
		addFileEntry(_FILE_NAME, _FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testAddDuplicateFileEntryTitleEqualToFileNameWithoutExtension()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);
		addFileEntry(_FILE_NAME, _STRIPPED_FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testAddDuplicateFileEntryWithNoExtension() throws Exception {
		addFileEntry(_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);
		addFileEntry(_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryEmptyFileName() throws Exception {
		testAddFileEntryBasic("", _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryEmptyFileNameAndExtension() throws Exception {
		testAddFileEntryBasic("", _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test(expected = FileNameException.class)
	public void testAddFileEntryEmptyTitleAndFileName() throws Exception {
		addFileEntry("", "");
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
	public void testAddFileEntryTitleEqualToFileName() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryTitleEqualToFileNameBothWithNoExtension()
		throws Exception {

		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME, "", _STRIPPED_FILE_NAME);
	}

	@Test
	public void testAddFileEntryTitleEqualToFileNamePlusExtension()
		throws Exception {

		testAddFileEntryBasic(
			_STRIPPED_FILE_NAME, _FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryTitleEqualToFileNameWithoutExtension()
		throws Exception {

		testAddFileEntryBasic(
			_FILE_NAME, _STRIPPED_FILE_NAME, "txt", _FILE_NAME);
	}

	@Test
	public void testAddFileEntryTitleWithExtension() throws Exception {
		testAddFileEntryBasic(_FILE_NAME, "Test.pdf", "txt", "Test.pdf.txt");
	}

	@Test(expected = FileNameException.class)
	public void testAddFileEntryWithEmptyTitle() throws Exception {
		addFileEntry(_FILE_NAME, "");
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testRenameDuplicateFileEntryFileNameEqualToTitleWithoutExtension()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);

		FileEntry fileEntry = addFileEntry("Temp.txt", "Temp");

		renameFileEntry(fileEntry, _STRIPPED_FILE_NAME, _FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testRenameDuplicateFileEntryTitleEqualToFileName()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);

		FileEntry fileEntry = addFileEntry("Temp.txt", "Temp");

		renameFileEntry(fileEntry, _FILE_NAME, _FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testRenameDuplicateFileEntryTitleEqualToFileNameWithoutExtension()
		throws Exception {

		addFileEntry(_FILE_NAME, _FILE_NAME);

		FileEntry fileEntry = addFileEntry("Temp.txt", "Temp");

		renameFileEntry(fileEntry, _FILE_NAME, _STRIPPED_FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testRenameDuplicateFileEntryWithNoExtensionNorFileName()
		throws Exception {

		addFileEntry(_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		FileEntry tempFileEntry = addFileEntry("", "Temp");

		renameFileEntry(tempFileEntry, StringPool.BLANK, _STRIPPED_FILE_NAME);
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testRenameDuplicateFileEntryWithNoExtensionNorTitle()
		throws Exception {

		addFileEntry(_STRIPPED_FILE_NAME, _STRIPPED_FILE_NAME);

		FileEntry tempFileEntry = addFileEntry("Temp", "Temp");

		renameFileEntry(tempFileEntry, _STRIPPED_FILE_NAME, StringPool.BLANK);
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

	protected void renameFileEntry(
			FileEntry fileEntry, String sourceFileName, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), sourceFileName, ContentTypes.TEXT_PLAIN,
			title, StringPool.BLANK, StringPool.BLANK, false,
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

	private static final String _FILE_NAME = "Test.txt";

	private static final String _STRIPPED_FILE_NAME = "Test";

}