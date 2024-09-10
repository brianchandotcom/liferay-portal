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

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_folder = _getSingleApproverFolder(_serviceContext);

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
	}

	@Test
	public void testWorkflowApprovesFileEntry() throws PortalException {
		FileEntry fileEntry = _addFileEntry(null, null);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatus(fileVersion, WorkflowConstants.STATUS_APPROVED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
	}

	@Test
	public void testWorkflowApprovesFileEntryExpireFutureDate()
		throws PortalException {

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		Date expirationDate = new Date(System.currentTimeMillis() + Time.DAY);

		FileEntry fileEntry = _addFileEntry(null, expirationDate);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatus(fileVersion, WorkflowConstants.STATUS_APPROVED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
		Assert.assertEquals(expirationDate, fileVersion.getExpirationDate());
	}

	@Test
	public void testWorkflowApprovesFileEntryExpirePastDate()
		throws PortalException {

		FileEntry fileEntry = _addFileEntry(null, null);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setExpirationDate(
			new Date(System.currentTimeMillis() - Time.DAY));

		_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		_updateStatus(fileVersion, WorkflowConstants.STATUS_APPROVED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
		Assert.assertNull(fileVersion.getExpirationDate());
	}

	@Test
	public void testWorkflowApprovesFileEntryScheduledFutureDate()
		throws PortalException {

		FileEntry fileEntry = _addFileEntry(
			new Date(System.currentTimeMillis() + Time.DAY), null);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatus(fileVersion, WorkflowConstants.STATUS_APPROVED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, fileVersion.getStatus());
	}

	@Test
	public void testWorkflowApprovesFileEntryScheduledPastDate()
		throws PortalException {

		FileEntry fileEntry = _addFileEntry(null, null);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setDisplayDate(
			new Date(System.currentTimeMillis() - Time.DAY));

		_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		_updateStatus(fileVersion, WorkflowConstants.STATUS_APPROVED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, fileVersion.getStatus());
	}

	@Test
	public void testWorkflowRejectsFileEntry() throws PortalException {
		FileEntry fileEntry = _addFileEntry(null, null);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		_updateStatus(fileVersion, WorkflowConstants.STATUS_DENIED);

		fileEntry = _dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_DENIED, fileVersion.getStatus());
	}

	private FileEntry _addFileEntry(Date displayDate, Date expirationDate)
		throws PortalException {

		return _dlAppService.addFileEntry(
			null, _group.getGroupId(), _folder.getFolderId(),
			RandomTestUtil.randomString(), "text",
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			StringPool.SPACE.getBytes(), displayDate, expirationDate, null,
			_serviceContext);
	}

	private Folder _getSingleApproverFolder(ServiceContext serviceContext)
		throws Exception {

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

	private void _updateStatus(FileVersion fileVersion, int status)
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
				"serviceContext", _serviceContext
			).build());
	}

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}