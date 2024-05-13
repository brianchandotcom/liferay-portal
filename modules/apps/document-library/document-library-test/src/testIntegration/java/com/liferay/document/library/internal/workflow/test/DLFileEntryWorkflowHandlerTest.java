/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.workflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class DLFileEntryWorkflowHandlerTest {

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

	@Test
	public void testWorkflowApprovesFileEntry() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = _getSingleApproverFolder(serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.SPACE.getBytes(), null, null, null, serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatusUserWorkflowTasks(
			fileVersion, WorkflowConstants.STATUS_APPROVED, serviceContext);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
	}

	@FeatureFlags("LPD-10701")
	@Test
	public void testWorkflowApprovesFileEntryScheduledFutureDate()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = _getSingleApproverFolder(serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.SPACE.getBytes(),
			new Date(System.currentTimeMillis() + Time.DAY), null, null,
			serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatusUserWorkflowTasks(
			fileVersion, WorkflowConstants.STATUS_APPROVED, serviceContext);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, fileVersion.getStatus());
	}

	@FeatureFlags("LPD-10701")
	@Test
	public void testWorkflowApprovesFileEntryScheduledPastDate()
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = _getSingleApproverFolder(serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.SPACE.getBytes(), null, null, null, serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setDisplayDate(
			new Date(System.currentTimeMillis() - Time.DAY));

		_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		_updateStatusUserWorkflowTasks(
			fileVersion, WorkflowConstants.STATUS_APPROVED, serviceContext);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
	}

	@Test
	public void testWorkflowRejectsFileEntry() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = _getSingleApproverFolder(serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.SPACE.getBytes(), null, null, null, serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatusUserWorkflowTasks(
			fileVersion, WorkflowConstants.STATUS_DENIED, serviceContext);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_DENIED, fileVersion.getStatus());
	}

	private Folder _getSingleApproverFolder(ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _dlAppService.addFolder(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		serviceContext.setAttribute(
			"restrictionType", DLFolderConstants.RESTRICTION_TYPE_WORKFLOW);
		serviceContext.setAttribute(
			"workflowDefinition" +
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
			"Single Approver@1");

		return _dlAppService.updateFolder(
			folder.getFolderId(), folder.getName(), folder.getDescription(),
			serviceContext);
	}

	private void _updateStatusUserWorkflowTasks(
			FileVersion fileVersion, int status, ServiceContext serviceContext)
		throws PortalException {

		WorkflowHandler<DLFileEntry> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DLFileEntry.class.getName());

		workflowHandler.updateStatus(
			status,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK,
				String.valueOf(fileVersion.getFileVersionId())
			).put(
				WorkflowConstants.CONTEXT_USER_ID,
				String.valueOf(TestPropsValues.getUserId())
			).put(
				"serviceContext", serviceContext
			).build());
	}

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}