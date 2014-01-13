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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.io.InputStream;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mika Koivisto
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLCheckInCheckOutTest {

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

		_serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId(), 0);

		_folder = createFolder("CheckInCheckOutTest");

		_fileEntry = createFileEntry(_FILE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		DLAppServiceUtil.deleteFolder(_folder.getFolderId());

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);

		UserLocalServiceUtil.deleteUser(_authorUser.getUserId());
		UserLocalServiceUtil.deleteUser(_overriderUser.getUserId());
	}

	@Test
	public void testAdminOverrideCheckout() throws Exception {
		overrideCheckout(_authorUser, TestPropsValues.getUser(), true);
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

		Assert.assertEquals("1.0" , fileEntry.getVersion());

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

	@Test
	public void testWithoutPermissionOverrideCheckout() throws Exception {
		overrideCheckout(_authorUser, _overriderUser, false);
	}

	@Test
	public void testWithPermissionOverrideCheckout() throws Exception {
		Role role = RoleTestUtil.addRole(
			"Overrider", RoleConstants.TYPE_REGULAR,
			DLFileEntryConstants.getClassName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.OVERRIDE_CHECKOUT);

		try {
			UserLocalServiceUtil.setRoleUsers(
				role.getRoleId(), new long[] {_overriderUser.getUserId()});

			overrideCheckout(_authorUser, _overriderUser, true);
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

	protected void overrideCheckout(
			User authorUser, User overriderUser, boolean expectOverride)
		throws Exception {

		ServiceTestUtil.setUser(authorUser);

		try {
			Folder folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

			Date lastPostDate = folder.getLastPostDate();

			String fileName = "OverrideCheckoutTest.txt";

			FileEntry fileEntry = createFileEntry(fileName);

			long fileEntryId = fileEntry.getFileEntryId();

			DLAppServiceUtil.checkOutFileEntry(fileEntryId, _serviceContext);

			ServiceTestUtil.setUser(overriderUser);

			try {
				DLAppServiceUtil.cancelCheckOut(fileEntryId);

				if (!expectOverride) {
					Assert.fail("Should not have succeeded cancel check out");
				}
			}
			catch (Exception e) {
				if (expectOverride) {
					Assert.fail("Should not have failed cancel check out " + e);
				}
			}

			if (expectOverride) {
				ServiceTestUtil.setUser(authorUser);

				DLAppServiceUtil.checkOutFileEntry(
					fileEntryId, _serviceContext);

				updateFileEntry(fileEntryId, fileName);

				ServiceTestUtil.setUser(overriderUser);
			}

			try {
				DLAppServiceUtil.checkInFileEntry(
					fileEntryId, false, StringPool.NULL, _serviceContext);

				if (!expectOverride) {
					Assert.fail("Should not have succeeded check in");
				}

				folder = DLAppServiceUtil.getFolder(_folder.getFolderId());

				Assert.assertFalse(
					lastPostDate.after(folder.getLastPostDate()));

				fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

				Assert.assertEquals("1.1", fileEntry.getVersion());
			}
			catch (Exception e) {
				if (expectOverride) {
					Assert.fail("Should not have failed check in " + e);
				}
			}
		}
		finally {
			ServiceTestUtil.setUser(TestPropsValues.getUser());
		}
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

	private User _authorUser;
	private FileEntry _fileEntry;
	private Folder _folder;
	private Group _group;
	private User _overriderUser;
	private ServiceContext _serviceContext;

}