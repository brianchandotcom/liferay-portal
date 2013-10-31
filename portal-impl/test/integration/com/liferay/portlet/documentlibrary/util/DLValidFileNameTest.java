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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Everest Liu
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		EnvironmentExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLValidFileNameTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddNotValidFileEntry() throws Exception {
		String name = StringUtil.randomString(20).concat(BLACKLIST_CHARS[0]);

		try {
			DLAppTestUtil.addFileEntry(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				true, name);

			Assert.fail(name + " is not a valida name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FileNameException);
		}
	}

	@Test
	public void testAddNotValidFolder() throws Exception {
		String name = StringUtil.randomString(20).concat(BLACKLIST_CHARS[0]);

		try {
			DLAppTestUtil.addFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name);

			Assert.fail(name + " is not a valida name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FolderNameException);
		}
	}

	@Test
	public void testEmptyName() {
		Assert.assertFalse(
			StringPool.BLANK, DLUtil.isValidName(StringPool.BLANK));
	}

	@Test
	public void testNameBlacklist() throws Exception {
		for (String name : PropsValues.DL_NAME_BLACKLIST) {
			Assert.assertFalse(name, DLUtil.isValidName(name));
		}
	}

	@Test
	public void testNameBlacklistExtension() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			String name = blacklistName.concat(".txt");

			Assert.assertFalse(name, DLUtil.isValidName(name));
		}
	}

	@Test
	public void testNameStartingWithPeriodUnderscore() throws Exception {
		String name = "._".concat(StringUtil.randomString(20)).concat(".tmp");

		Assert.assertTrue(name, DLUtil.isValidName(name));
	}

	@Test
	public void testNullName() {
		Assert.assertFalse("null", DLUtil.isValidName(null));
	}

	@Test
	public void testPeriodAtEnd() throws Exception {
		String name = StringUtil.randomString(20).concat("1.");

		Assert.assertFalse(name, DLUtil.isValidName(name));
	}

	@Test
	public void testRandomStrings() throws Exception {
		for (int i = 0; i < 100; i++) {
			String name = StringUtil.randomString(20);

			Assert.assertTrue(name, DLUtil.isValidName(name));
		}
	}

	@Test
	public void testRandomStringsWithBlacklistedChar() throws Exception {
		for (String blacklistChar : BLACKLIST_CHARS) {
			StringBuilder sb = new StringBuilder(4);

			sb.append(StringUtil.randomString(10));
			sb.append(blacklistChar);
			sb.append(StringUtil.randomString(10));

			Assert.assertFalse(
				sb.toString(), DLUtil.isValidName(sb.toString()));

			sb.append(".txt");

			Assert.assertFalse(
				sb.toString(), DLUtil.isValidName(sb.toString()));
		}
	}

	@Test
	public void testSpaceAtEnd() throws Exception {
		String name = StringUtil.randomString(20).concat(" ");

		Assert.assertFalse(name + "[space]", DLUtil.isValidName(name));
	}

	@Test
	public void testUpdateNotValidFileEntry() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			true, StringUtil.randomString(20));

		String name = StringUtil.randomString(20).concat(BLACKLIST_CHARS[0]);

		try {
			DLAppTestUtil.updateFileEntry(
				_group.getGroupId(), fileEntry.getFileEntryId(), name, name);

			Assert.fail(name + " is not a valida name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FileNameException);
		}
	}

	@Test
	public void testUpdateNotValidFolder() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(20));

		String name = StringUtil.randomString(20).concat(BLACKLIST_CHARS[0]);

		try {
			DLAppServiceUtil.updateFolder(
				folder.getFolderId(), name, StringPool.BLANK,
				ServiceTestUtil.getServiceContext(_group.getGroupId()));

			Assert.fail(name + " is not a valida name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FolderNameException);
		}
	}

	@Test
	public void testValidNamesStartingWithBlacklist() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			String name = blacklistName.concat("1");

			Assert.assertTrue(name, DLUtil.isValidName(name));

			name = blacklistName.concat(" .txt");

			Assert.assertTrue(name, DLUtil.isValidName(name));
		}
	}

	private static final String[] BLACKLIST_CHARS = new String[] {
		"<", ">", ":", "\"", "\\", "|", "?", "*", "\\\\", "\u0000", "\u0001",
		"\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\u0008",
		"\u0009", "\u000B", "\u000C", "\u000E", "\u000F", "\u0010", "\u0011",
		"\u0012", "\u0013", "\u0014", "\u0015", "\u0016", "\u0017", "\u0018",
		"\u0019", "\u001A", "\u001B", "\u001C", "\u001D", "\u001E", "\u001F"};

	private static Group _group;

}