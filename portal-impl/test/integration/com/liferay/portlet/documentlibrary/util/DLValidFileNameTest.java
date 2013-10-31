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
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

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

			Assert.fail(name + " is not a valid name.");
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

			Assert.fail(name + " is not a valid name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FolderNameException);
		}
	}

	@Test
	public void testNameStartingWithPeriodUnderscore() throws Exception {
		String name = "._".concat(StringUtil.randomString(20)).concat(".tmp");

		Assert.assertTrue(name, DLStoreUtil.isValidName(name));
	}

	@Test
	public void testNullName() {
		Assert.assertFalse("null", DLStoreUtil.isValidName(null));
	}

	@Test
	public void testRandomStrings() throws Exception {
		for (int i = 0; i < 100; i++) {
			String name = StringUtil.randomString(20);

			Assert.assertTrue(name, DLStoreUtil.isValidName(name));
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
				sb.toString(), DLStoreUtil.isValidName(sb.toString()));

			sb.append(".txt");

			Assert.assertFalse(
				sb.toString(), DLStoreUtil.isValidName(sb.toString()));
		}
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

			Assert.fail(name + " is not a valid name.");
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

			Assert.fail(name + " is not a valid name.");
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof FolderNameException);
		}
	}

	private static final String[] BLACKLIST_CHARS = new String[] {
		"\\", "\\\\", "//", ":", "*", "?", "\"", "<", ">", "|", "[", "]", "../",
		"/.."};

	private static Group _group;

}