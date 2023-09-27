/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.helper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLURLHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetDownloadURLAppendVersion() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String downloadURL = _dlURLHelper.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertTrue(
			downloadURL, downloadURL.contains(fileEntry.getUuid()));
	}

	@Test
	public void testGetDownloadURLAppendVersionAbsoluteURL() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String downloadURL = _dlURLHelper.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, true);

		Assert.assertTrue(
			downloadURL, downloadURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetDownloadURLAppendVersionNotAbsoluteURL()
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String downloadURL = _dlURLHelper.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertFalse(
			downloadURL, downloadURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetDownloadURLNotAppendVersion() throws Exception {
		String urlTitle = RandomTestUtil.randomString();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), urlTitle,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String downloadURL = _dlURLHelper.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, false, false);

		Assert.assertTrue(
			downloadURL,
			downloadURL.contains(StringUtil.toLowerCase(urlTitle)));
	}

	@Test
	public void testGetDownloadURLNotAppendVersionAbsoluteURL()
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String downloadURL = _dlURLHelper.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, false, true);

		Assert.assertTrue(
			downloadURL, downloadURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetPreviewURLAppendVersion() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertTrue(previewURL, previewURL.contains(fileEntry.getUuid()));
	}

	@Test
	public void testGetPreviewURLAppendVersionAbsoluteURL() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, false, true);

		Assert.assertTrue(
			previewURL, previewURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetPreviewURLAppendVersionNotAbsoluteURL()
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertFalse(
			previewURL, previewURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetPreviewURLNotAppendVersion() throws Exception {
		String urlTitle = RandomTestUtil.randomString();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), urlTitle,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, false, false);

		Assert.assertTrue(
			previewURL, previewURL.contains(StringUtil.toLowerCase(urlTitle)));
	}

	@Test
	public void testGetPreviewURLNotAppendVersionAbsoluteURL()
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, false, true);

		Assert.assertTrue(
			previewURL, previewURL.startsWith("http://localhost:8080"));
	}

	@Test
	public void testGetPreviewURLVersioned() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertTrue(previewURL, previewURL.contains("version="));
	}

	@Test
	public void testGetPreviewURLVersionUpdated() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			(byte[])null, null, null, serviceContext);

		fileEntry = _dlAppLocalService.updateFileEntry(
			_user.getUserId(), fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, null,
			DLVersionNumberIncrease.MAJOR, (byte[])null, null, null,
			serviceContext);

		String previewURL = _dlURLHelper.getPreviewURL(
			fileEntry, fileEntry.getFileVersion(), _getThemeDisplay(),
			StringPool.BLANK, true, false);

		Assert.assertTrue(previewURL, previewURL.contains("version=2"));
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(_user);

		return themeDisplay;
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}