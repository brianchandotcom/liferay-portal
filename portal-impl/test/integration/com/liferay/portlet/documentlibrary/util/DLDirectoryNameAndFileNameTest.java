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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Everest Liu
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLDirectoryNameAndFileNameTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = FileNameException.class)
	public void testAddFileEntry() throws Exception {
		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name);
	}

	@Test(expected = FolderNameException.class)
	public void testAddFolder() throws Exception {
		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name);
	}

	@Test
	public void testIsValidNameBackSlash() {
		String name =
			StringUtil.randomString(10) + StringPool.BACK_SLASH +
				StringUtil.randomString(10);

		Assert.assertFalse(name, DLStoreUtil.isValidName(name));
	}

	@Test
	public void testIsValidNameBlacklist() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			String name = blacklistName;

			Assert.assertFalse(name, DLStoreUtil.isValidName(name));

			name = blacklistName + ".txt";

			Assert.assertFalse(name, DLStoreUtil.isValidName(name));

			name = blacklistName + StringUtil.randomString(10);

			Assert.assertTrue(name, DLStoreUtil.isValidName(name));

			name = blacklistName + StringUtil.randomString(10) + " .txt";

			Assert.assertTrue(name, DLStoreUtil.isValidName(name));
		}
	}

	@Test
	public void testIsValidNameBlacklistLastCharacter() throws Exception {
		for (String blacklistLastChar : _DL_CHAR_LAST_BLACKLIST) {
			String name = StringUtil.randomString(20) + blacklistLastChar;

			Assert.assertFalse(name, DLStoreUtil.isValidName(name));
		}
	}

	@Test
	public void testIsValidNameEmptyString() {
		Assert.assertFalse(DLStoreUtil.isValidName(StringPool.BLANK));
	}

	@Test
	public void testIsValidNameHiddenOSX() throws Exception {
		String name = "._" + StringUtil.randomString(20) + ".tmp";

		Assert.assertTrue(name, DLStoreUtil.isValidName(name));
	}

	@Test
	public void testIsValidNameNull() {
		Assert.assertFalse(DLStoreUtil.isValidName(null));
	}

	@Test
	public void testIsValidNameRandom() throws Exception {
		for (int i = 0; i < 100; i++) {
			String name = StringUtil.randomString(20);

			Assert.assertTrue(name, DLStoreUtil.isValidName(name));
		}

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
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

	@Test(expected = FileNameException.class)
	public void testUpdateFileEntry() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(20));

		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		DLAppTestUtil.updateFileEntry(
			_group.getGroupId(), fileEntry.getFileEntryId(), name, name);
	}

	@Test(expected = FolderNameException.class)
	public void testUpdateFolder() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		DLAppServiceUtil.updateFolder(
			folder.getFolderId(), name, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private String[] _DL_CHAR_LAST_BLACKLIST =
		{StringPool.SPACE, StringPool.PERIOD};

	@DeleteAfterTestRun
	private Group _group;

}