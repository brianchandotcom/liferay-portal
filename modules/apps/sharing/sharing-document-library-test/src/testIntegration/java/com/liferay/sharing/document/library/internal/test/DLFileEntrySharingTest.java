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

package com.liferay.sharing.document.library.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLTrashService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.sharing.security.permission.SharingPermissionChecker;
import com.liferay.sharing.test.util.BaseSharingTestCase;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLFileEntrySharingTest extends BaseSharingTestCase<DLFileEntry> {

	@Override
	protected void deleteModel(DLFileEntry dlFileEntry) throws PortalException {
		_dlAppLocalService.deleteFileEntry(dlFileEntry.getFileEntryId());
	}

	@Override
	protected String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	protected DLFileEntry getModel(User user, Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, user.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), "text/plain", StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			null, null, serviceContext);

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected int getModelCount(Group group) throws PortalException {
		return _dlAppService.getFileEntriesCount(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	protected ModelResourcePermission<DLFileEntry>
		getModelResourcePermission() {

		return _modelResourcePermission;
	}

	@Override
	protected DLFileEntry getPendingModel(User user, Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		Folder folder = _dlAppLocalService.addFolder(
			user.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		serviceContext.setAttribute(
			"restrictionType", DLFolderConstants.RESTRICTION_TYPE_WORKFLOW);
		serviceContext.setAttribute(
			"workflowDefinition" +
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
			"Single Approver@1");

		_dlAppLocalService.updateFolder(
			folder.getFolderId(), folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, user.getUserId(), group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.SPACE.getBytes(), null, null,
			serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected PermissionSQLContributor getPermissionSQLContributor() {
		return _permissionSQLContributor;
	}

	@Override
	protected SharingPermissionChecker getSharingPermissionChecker() {
		return _sharingPermissionChecker;
	}

	@Override
	protected void moveModelToTrash(DLFileEntry dlFileEntry)
		throws PortalException {

		_dlTrashService.moveFileEntryToTrash(dlFileEntry.getFileEntryId());
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLTrashService _dlTrashService;

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	)
	private ModelResourcePermission<DLFileEntry> _modelResourcePermission;

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	)
	private PermissionSQLContributor _permissionSQLContributor;

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry"
	)
	private SharingPermissionChecker _sharingPermissionChecker;

}