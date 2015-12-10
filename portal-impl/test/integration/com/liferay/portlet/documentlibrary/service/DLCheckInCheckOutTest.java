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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.ContextUserReplacer;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.FileEntryLockException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.io.InputStream;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class DLCheckInCheckOutTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, DLFolderConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.ADD_DOCUMENT);

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);

		_authorUser = UserTestUtil.addUser("author", _group.getGroupId());
		_overriderUser = UserTestUtil.addUser("overrider", _group.getGroupId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), 0);

		_folder = createFolder("CheckInCheckOutTest");

		_fileEntry = createFileEntry(_FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testCancelCheckoutVersion() throws Exception {
		FileEntry fileEntry = createFileEntry(StringUtil.randomString());

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		Assert.assertEquals(
			fileEntry.getVersion(), DLFileEntryConstants.VERSION_DEFAULT);
	}

	@Test
	public void testCancelCheckout() throws Exception {
		FileEntry fileEntry = createFileEntry(StringUtil.randomString());

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), _serviceContext);

		DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

		fileEntry = DLAppServiceUtil.getFileEntry(fileEntry.getFileEntryId());

		Assert.assertFalse(fileEntry.isCheckedOut());
	}

	@Test
	public void testAdminCancelCheckout() throws Exception {
		FileEntry fileEntry = null;

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_authorUser)) {

			fileEntry = createFileEntry(StringUtil.randomString());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), _serviceContext);
		}

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				TestPropsValues.getUser())) {

			DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

			fileEntry = DLAppServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			Assert.assertFalse(fileEntry.isCheckedOut());
		}
	}

	@Test(expected = FileEntryLockException.MustOwnLock.class)
	public void testAdminOverrideCheckout() throws Exception {
		FileEntry fileEntry = null;

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_authorUser)) {

			fileEntry = createFileEntry(StringUtil.randomString());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), _serviceContext);
		}

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				TestPropsValues.getUser())) {

			DLAppServiceUtil.checkInFileEntry(
				fileEntry.getFileEntryId(), false, StringPool.NULL,
				_serviceContext);
		}
	}

	@Test(expected = FileEntryLockException.MustOwnLock.class)
	public void testAdminUpdateCheckedOutFile() throws Exception {
		FileEntry fileEntry = null;

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_authorUser)) {

			fileEntry = createFileEntry(StringUtil.randomString());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), _serviceContext);
		}

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				TestPropsValues.getUser())) {

			updateFileEntry(fileEntry.getFileEntryId());
		}
	}

	@Test
	public void testCancelCheckIn() throws Exception {
		DLAppServiceUtil.checkOutFileEntry(
			_fileEntry.getFileEntryId(), _serviceContext);

		Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Date lastPostDate = folder.getLastPostDate();

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
			_fileEntry.getFileEntryId());

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertEquals("PWC", fileVersion.getVersion());

		getAssetEntry(fileVersion.getFileVersionId(), true);

		DLAppServiceUtil.cancelCheckOut(_fileEntry.getFileEntryId());

		folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Assert.assertTrue(
			DateUtil.equals(lastPostDate, folder.getLastPostDate()));

		fileEntry = DLAppServiceUtil.getFileEntry(_fileEntry.getFileEntryId());

		Assert.assertEquals("1.0", fileEntry.getVersion());
	}

	@Test
	public void testCheckIn() throws Exception {
		for (int i = 0; i < 2; i++) {
			DLAppServiceUtil.checkOutFileEntry(
				_fileEntry.getFileEntryId(), _serviceContext);

			Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

			Date lastPostDate = folder.getLastPostDate();

			FileVersion fileVersion = _fileEntry.getLatestFileVersion();

			Assert.assertEquals("PWC", fileVersion.getVersion());

			getAssetEntry(fileVersion.getFileVersionId(), true);

			if (i == 1) {
				updateFileEntry(_fileEntry.getFileEntryId());
			}

			DLAppServiceUtil.checkInFileEntry(
				_fileEntry.getFileEntryId(), false, StringPool.BLANK,
				_serviceContext);

			folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

			if (i == 1) {
				Assert.assertFalse(
					lastPostDate.after(folder.getLastPostDate()));
			}
			else {
				Assert.assertTrue(
					DateUtil.equals(lastPostDate, folder.getLastPostDate()));
			}

			FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
				_fileEntry.getFileEntryId());

			Assert.assertEquals("1." + i, fileEntry.getVersion());

			fileVersion = fileEntry.getFileVersion();

			getAssetEntry(fileVersion.getFileVersionId(), false);
		}
	}

	@Test
	public void testCheckOut() throws Exception {
		Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Date lastPostDate = folder.getLastPostDate();

		DLAppServiceUtil.checkOutFileEntry(
			_fileEntry.getFileEntryId(), _serviceContext);

		folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Assert.assertTrue(
			DateUtil.equals(lastPostDate, folder.getLastPostDate()));

		FileVersion fileVersion = _fileEntry.getLatestFileVersion();

		Assert.assertEquals("PWC", fileVersion.getVersion());

		getAssetEntry(fileVersion.getFileVersionId(), true);
	}

	@Test
	public void testUpdateFileEntry() throws Exception {
		Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Date lastPostDate = folder.getLastPostDate();

		FileEntry fileEntry = updateFileEntry(_fileEntry.getFileEntryId());

		folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Assert.assertFalse(lastPostDate.after(folder.getLastPostDate()));

		Assert.assertEquals("1.1", fileEntry.getVersion());

		getAssetEntry(fileEntry.getFileEntryId(), true);
	}

	@Test
	public void testUpdateFileEntry2() throws Exception {
		DLAppServiceUtil.checkOutFileEntry(
			_fileEntry.getFileEntryId(), _serviceContext);

		Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Date lastPostDate = folder.getLastPostDate();

		FileEntry fileEntry = updateFileEntry(_fileEntry.getFileEntryId());

		Assert.assertEquals("1.0", fileEntry.getVersion());

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertEquals("PWC", fileVersion.getVersion());

		DLAppServiceUtil.checkInFileEntry(
			_fileEntry.getFileEntryId(), false, StringPool.BLANK,
			_serviceContext);

		folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

		Assert.assertFalse(lastPostDate.after(folder.getLastPostDate()));

		fileEntry = DLAppServiceUtil.getFileEntry(_fileEntry.getFileEntryId());

		Assert.assertEquals("1.1", fileEntry.getVersion());

		getAssetEntry(fileVersion.getFileVersionId(), false);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testWithoutPermissionOverrideCheckout() throws Exception {
		FileEntry fileEntry = null;

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_authorUser)) {

			fileEntry = createFileEntry(StringUtil.randomString());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), _serviceContext);
		}

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_overriderUser)) {

			DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());
		}
	}

	@Test(expected = FileEntryLockException.MustOwnLock.class)
	public void testCheckInWithoutPermissionOverrideCheckout() throws Exception {
		FileEntry fileEntry = null;

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_authorUser)) {

			fileEntry = createFileEntry(StringUtil.randomString());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), _serviceContext);
		}

		try (ContextUserReplacer contextUserReplacer = new ContextUserReplacer(
				_overriderUser)) {

			DLAppServiceUtil.checkInFileEntry(
				fileEntry.getFileEntryId(), false, StringUtil.randomString(),
				_serviceContext);
		}
	}

	@Test
	public void testCancelCheckoutWithPermissionOverrideCheckout()
		throws Exception {

		Role role = RoleTestUtil.addRole(
			"Overrider", RoleConstants.TYPE_REGULAR,
			DLFileEntryConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.OVERRIDE_CHECKOUT);

		try {
			UserLocalServiceUtil.setRoleUsers(
				role.getRoleId(), new long[] {_overriderUser.getUserId()});

			FileEntry fileEntry = null;

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_authorUser)) {

				fileEntry = createFileEntry(StringUtil.randomString());

				DLAppServiceUtil.checkOutFileEntry(
					fileEntry.getFileEntryId(), _serviceContext);
			}

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_overriderUser)) {

				DLAppServiceUtil.cancelCheckOut(fileEntry.getFileEntryId());

				fileEntry = DLAppServiceUtil.getFileEntry(
					fileEntry.getFileEntryId());

				Assert.assertFalse(fileEntry.isCheckedOut());
			}
		}
		finally {
			RoleLocalServiceUtil.deleteRole(role.getRoleId());
		}
	}

	@Test(expected = FileEntryLockException.MustOwnLock.class)
	public void testCheckInWithPermissionOverrideCheckout() throws Exception {
		Role role = RoleTestUtil.addRole(
			"Overrider", RoleConstants.TYPE_REGULAR,
			DLFileEntryConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.OVERRIDE_CHECKOUT);

		try {
			UserLocalServiceUtil.setRoleUsers(
				role.getRoleId(), new long[] {_overriderUser.getUserId()});

			FileEntry fileEntry = null;

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_authorUser)) {

				fileEntry = createFileEntry(StringUtil.randomString());

				DLAppServiceUtil.checkOutFileEntry(
					fileEntry.getFileEntryId(), _serviceContext);
			}

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_overriderUser)) {

				DLAppServiceUtil.checkInFileEntry(
					fileEntry.getFileEntryId(), false, StringPool.NULL,
					_serviceContext);
			}
		}
		finally {
			RoleLocalServiceUtil.deleteRole(role.getRoleId());
		}
	}

	@Test(expected = FileEntryLockException.MustOwnLock.class)
	public void testUpdateWithPermissionOverrideCheckout() throws Exception {
		Role role = RoleTestUtil.addRole(
			"Overrider", RoleConstants.TYPE_REGULAR,
			DLFileEntryConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.OVERRIDE_CHECKOUT);

		RoleTestUtil.addResourcePermission(
			role, DLFileEntryConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.UPDATE);

		try {
			UserLocalServiceUtil.setRoleUsers(
				role.getRoleId(), new long[] {_overriderUser.getUserId()});

			FileEntry fileEntry = null;

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_authorUser)) {

				fileEntry = createFileEntry(StringUtil.randomString());

				DLAppServiceUtil.checkOutFileEntry(
					fileEntry.getFileEntryId(), _serviceContext);
			}

			try (ContextUserReplacer contextUserReplacer =
					new ContextUserReplacer(_overriderUser)) {

				updateFileEntry(fileEntry.getFileEntryId());
			}
		}
		finally {
			RoleLocalServiceUtil.deleteRole(role.getRoleId());
		}
	}

	protected FileEntry createFileEntry(String fileName) throws Exception {
		long repositoryId = _group.getGroupId();
		long folderId = _folder.getFolderId();
		InputStream inputStream = new UnsyncByteArrayInputStream(
			_TEST_CONTENT.getBytes());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			repositoryId, folderId, fileName, ContentTypes.TEXT_PLAIN, fileName,
			null, null, inputStream, _TEST_CONTENT.length(), _serviceContext);

		Assert.assertNotNull(fileEntry);

		Assert.assertEquals("1.0", fileEntry.getVersion());

		AssetEntry assetEntry = getAssetEntry(fileEntry.getFileEntryId(), true);

		Assert.assertNotNull(assetEntry);

		return fileEntry;
	}

	protected Folder createFolder(String folderName) throws Exception {
		long repositoryId = _group.getGroupId();

		Folder folder = DLAppServiceUtil.addFolder(
			repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			folderName, StringPool.BLANK, _serviceContext);

		Assert.assertNotNull(folder);

		folder = DLAppServiceUtil.getFolder(folder.getFolderId());

		Assert.assertNotNull(folder);

		return folder;
	}

	protected AssetEntry getAssetEntry(long assetClassPk, boolean expectExists)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), assetClassPk);

		if (expectExists) {
			Assert.assertNotNull(assetEntry);
		}
		else {
			Assert.assertNull(assetEntry);
		}

		return assetEntry;
	}

	protected FileEntry updateFileEntry(long fileEntryId) throws Exception {
		return updateFileEntry(fileEntryId, _FILE_NAME);
	}

	protected FileEntry updateFileEntry(long fileEntryId, String fileName)
		throws Exception {

		String content = _TEST_CONTENT + "\n" + System.currentTimeMillis();

		InputStream inputStream = new UnsyncByteArrayInputStream(
			content.getBytes());

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, fileName, ContentTypes.TEXT_PLAIN, fileName, null,
			null, false, inputStream, content.length(), _serviceContext);
	}

	private static final String _FILE_NAME = "test1.txt";

	private static final String _TEST_CONTENT =
		"LIFERAY\nEnterprise. Open Source. For Life.";

	@DeleteAfterTestRun
	private User _authorUser;

	private FileEntry _fileEntry;
	private Folder _folder;
	private Group _group;

	@DeleteAfterTestRun
	private User _overriderUser;

	private ServiceContext _serviceContext;

}