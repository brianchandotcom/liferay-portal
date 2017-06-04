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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.SimplePermissionChecker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		setUpPermissionThreadLocal();
		setUpPrincipalThreadLocal();
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testCopyFileEntryWithExtensionInFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), true);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithExtensionInFolderToRootFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), true);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Test
	public void testCopyFileEntryWithExtensionInRootFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInFolderToFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), false);

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInFolderToRootFolder()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder folder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntry dlFileEntry = addDLFileEntry(folder.getFolderId(), false);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	@Test
	public void testCopyFileEntryWithoutExtensionInRootFolderToFolder()
		throws Exception {

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder destFolder = DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		DLFileEntryServiceUtil.copyFileEntry(
			_group.getGroupId(), _group.getGroupId(),
			dlFileEntry.getFileEntryId(), destFolder.getFolderId(),
			serviceContext);
	}

	@Test
	public void testRestoreFileNameWhenDeletingLatestFileVersion()
		throws Exception {

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		dlFileEntry = updateStatus(
			dlFileEntry.getLatestFileVersion(true), serviceContext);

		String initialFileName = dlFileEntry.getFileName();

		dlFileEntry.setTitle(RandomTestUtil.randomString());

		dlFileEntry = updateDLFileEntry(dlFileEntry, serviceContext);

		dlFileEntry = updateStatus(
			dlFileEntry.getLatestFileVersion(true), serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		dlFileEntry = DLFileEntryLocalServiceUtil.deleteFileVersion(
			dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileVersion.getVersion());

		Assert.assertEquals(initialFileName, dlFileEntry.getFileName());
	}

	@Test
	public void testUpdateFileName()throws Exception {
		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		dlFileEntry = updateStatus(
			dlFileEntry.getLatestFileVersion(true), serviceContext);

		String title = RandomTestUtil.randomString();

		dlFileEntry.setTitle(title);

		dlFileEntry = updateDLFileEntry(dlFileEntry, serviceContext);

		dlFileEntry = updateStatus(
			dlFileEntry.getLatestFileVersion(true), serviceContext);

		Assert.assertEquals(
			DLUtil.getSanitizedFileName(title, dlFileEntry.getExtension()),
			dlFileEntry.getFileName());
	}

	@Test
	public void testUpdateFileNameWhenUpdatingFileVersionStatus()
		throws Exception {

		DLFileEntry dlFileEntry = addDLFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		dlFileEntry = updateStatus(
			dlFileEntry.getLatestFileVersion(true), serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(true);

		String fileName = RandomTestUtil.randomString();

		dlFileVersion.setFileName(fileName);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

		dlFileEntry = updateStatus(dlFileVersion, serviceContext);

		Assert.assertEquals(
			DLUtil.getSanitizedFileName(fileName, dlFileEntry.getExtension()),
			dlFileEntry.getFileName());
	}

	protected DLFileEntry addDLFileEntry(long folderId, boolean appendExtension)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String sourceFileName = RandomTestUtil.randomString();

		if (appendExtension) {
			sourceFileName = sourceFileName.concat(".pdf");
		}

		String fileEntryTitle = RandomTestUtil.randomString();

		return DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), folderId, sourceFileName, null, fileEntryTitle,
			RandomTestUtil.randomString(), StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(_CONTENT.getBytes()), 0,
			serviceContext);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new SimplePermissionChecker() {

				{
					init(TestPropsValues.getUser());
				}

				@Override
				public boolean hasOwnerPermission(
					long companyId, String name, String primKey, long ownerId,
					String actionId) {

					return true;
				}

			});
	}

	protected void setUpPrincipalThreadLocal() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	protected DLFileEntry updateDLFileEntry(
			DLFileEntry dlFileEntry, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryLocalServiceUtil.updateFileEntry(
			dlFileEntry.getUserId(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getTitle(), dlFileEntry.getMimeType(),
			dlFileEntry.getTitle(), dlFileEntry.getDescription(),
			StringPool.BLANK, false, dlFileEntry.getFileEntryTypeId(), null,
			null, dlFileEntry.getContentStream(), dlFileEntry.getSize(),
			serviceContext);
	}

	protected DLFileEntry updateStatus(
			DLFileVersion dlFileVersion, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryLocalServiceUtil.updateStatus(
			dlFileVersion.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			new HashMap<String, Serializable>());
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@DeleteAfterTestRun
	private Group _group;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

}