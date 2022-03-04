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

package com.liferay.document.library.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FolderNameException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Everest Liu
 */
@RunWith(Arquillian.class)
public class DLDirectoryNameAndFileNameTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = FileNameException.class)
	public void testAddFileEntry() throws Exception {
		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		addFileEntry(name);
	}

	@Test(expected = FolderNameException.class)
	public void testAddFolder() throws Exception {
		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name, RandomTestUtil.randomString(), serviceContext);
	}

	@Test
	public void testFixNameBackSlash() {
		String random1 = StringUtil.randomString(10);
		String random2 = StringUtil.randomString(10);

		String name = random1 + StringPool.BACK_SLASH + random2;

		Assert.assertEquals(
			random1 + StringPool.UNDERLINE + random2,
			DLValidatorUtil.fixName(name));
	}

	@Test
	public void testFixNameBlacklist() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			String name = blacklistName;

			Assert.assertEquals(
				blacklistName + StringPool.UNDERLINE,
				DLValidatorUtil.fixName(name));

			name = blacklistName + ".txt";

			Assert.assertEquals(
				blacklistName + "_.txt", DLValidatorUtil.fixName(name));

			name = blacklistName + StringUtil.randomString(10);

			Assert.assertEquals(name, DLValidatorUtil.fixName(name));

			name = blacklistName + StringUtil.randomString(10) + " .txt";

			Assert.assertEquals(name, DLValidatorUtil.fixName(name));
		}
	}

	@Test
	public void testFixNameBlacklistLastCharacter() throws Exception {
		for (String blacklistLastChar : _DL_CHAR_LAST_BLACKLIST) {
			String name = StringUtil.randomString(20);

			Assert.assertEquals(
				name, DLValidatorUtil.fixName(name + blacklistLastChar));
		}
	}

	@Test
	public void testFixNameEmptyString() {
		Assert.assertEquals(
			StringPool.UNDERLINE, DLValidatorUtil.fixName(StringPool.BLANK));
	}

	@Test
	public void testFixNameHiddenOSX() throws Exception {
		String name = "._" + StringUtil.randomString(20) + ".tmp";

		Assert.assertEquals(name, DLValidatorUtil.fixName(name));
	}

	@Test
	public void testFixNameNull() {
		Assert.assertEquals(
			StringPool.UNDERLINE, DLValidatorUtil.fixName(null));
	}

	@Test
	public void testFixNameRandom() throws Exception {
		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			StringBundler sb = new StringBundler(4);

			sb.append(StringUtil.randomString(10));
			sb.append(blacklistChar);
			sb.append(StringUtil.randomString(10));

			String name = sb.toString();

			Assert.assertEquals(
				StringUtil.replace(name, blacklistChar, StringPool.UNDERLINE),
				DLValidatorUtil.fixName(name));

			sb.append(".txt");

			name = sb.toString();

			Assert.assertEquals(
				StringUtil.replace(name, blacklistChar, StringPool.UNDERLINE),
				DLValidatorUtil.fixName(name));
		}
	}

	@Test
	public void testIsValidNameBackSlash() {
		String name =
			StringUtil.randomString(10) + StringPool.BACK_SLASH +
				StringUtil.randomString(10);

		Assert.assertFalse(name, DLValidatorUtil.isValidName(name));
	}

	@Test
	public void testIsValidNameBlacklist() throws Exception {
		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			String name = blacklistName;

			Assert.assertFalse(name, DLValidatorUtil.isValidName(name));

			name = blacklistName + ".txt";

			Assert.assertFalse(name, DLValidatorUtil.isValidName(name));

			name = blacklistName + StringUtil.randomString(10);

			Assert.assertTrue(name, DLValidatorUtil.isValidName(name));

			name = blacklistName + StringUtil.randomString(10) + " .txt";

			Assert.assertTrue(name, DLValidatorUtil.isValidName(name));
		}
	}

	@Test
	public void testIsValidNameBlacklistLastCharacter() throws Exception {
		for (String blacklistLastChar : _DL_CHAR_LAST_BLACKLIST) {
			String name = StringUtil.randomString(20) + blacklistLastChar;

			Assert.assertFalse(name, DLValidatorUtil.isValidName(name));
		}
	}

	@Test
	public void testIsValidNameEmptyString() {
		Assert.assertFalse(DLValidatorUtil.isValidName(StringPool.BLANK));
	}

	@Test
	public void testIsValidNameHiddenOSX() throws Exception {
		String name = "._" + StringUtil.randomString(20) + ".tmp";

		Assert.assertTrue(name, DLValidatorUtil.isValidName(name));
	}

	@Test
	public void testIsValidNameNull() {
		Assert.assertFalse(DLValidatorUtil.isValidName(null));
	}

	@Test
	public void testIsValidNameRandom() throws Exception {
		for (int i = 0; i < 100; i++) {
			String name = StringUtil.randomString(20);

			Assert.assertTrue(name, DLValidatorUtil.isValidName(name));
		}

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			StringBundler sb = new StringBundler(4);

			sb.append(StringUtil.randomString(10));
			sb.append(blacklistChar);
			sb.append(StringUtil.randomString(10));

			Assert.assertFalse(
				sb.toString(), DLValidatorUtil.isValidName(sb.toString()));

			sb.append(".txt");

			Assert.assertFalse(
				sb.toString(), DLValidatorUtil.isValidName(sb.toString()));
		}
	}

	@Test(expected = FileNameException.class)
	public void testUpdateFileEntry() throws Exception {
		FileEntry fileEntry = addFileEntry(StringUtil.randomString(20));

		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, TestDataConstants.TEST_BYTE_ARRAY,
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			serviceContext);
	}

	@Test(expected = FolderNameException.class)
	public void testUpdateFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		String name =
			StringUtil.randomString(20) + PropsValues.DL_CHAR_BLACKLIST[0];

		DLAppServiceUtil.updateFolder(
			folder.getFolderId(), name, StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	protected FileEntry addFileEntry(String sourceFileName) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, sourceFileName,
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY, null,
			null, serviceContext);
	}

	private static final String[] _DL_CHAR_LAST_BLACKLIST = {
		StringPool.SPACE, StringPool.PERIOD
	};

	@DeleteAfterTestRun
	private Group _group;

}