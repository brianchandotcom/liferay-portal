/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.app.service.test.util.DLAppServiceTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.document.library.workflow.WorkflowHandlerInvocationCounter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenCheckingInAFileEntryTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldCallWorkflowHandler() throws Exception {
		try (WorkflowHandlerInvocationCounter<FileEntry>
				workflowHandlerInvocationCounter =
					new WorkflowHandlerInvocationCounter<>(
						DLFileEntryConstants.getClassName())) {

			FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			dlAppService.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));

			DLAppServiceTestUtil.updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(),
				RandomTestUtil.randomString(), null, null, null, true);

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));

			dlAppService.checkInFileEntry(
				fileEntry.getFileEntryId(), DLVersionNumberIncrease.MINOR,
				RandomTestUtil.randomString(), serviceContext);

			Assert.assertEquals(
				2,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));
		}
	}

	@Test
	public void testShouldUpdateFileEntryTypeWithNoVersionIncrement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = dlAppService.addFileEntry(
			null, group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, 0, null,
			null, null, serviceContext);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(0, dlFileEntry.getFileEntryTypeId());

		dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		FileEntry checkedOutFileEntry = dlAppService.getFileEntry(
			fileEntry.getFileEntryId());

		serviceContext.setAttribute(
			"ddmFormValues", _SERIALIZED_DDM_FORM_VALUES);

		DLFileEntryType basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getBasicDocumentDLFileEntryType();

		serviceContext.setAttribute(
			"fileEntryTypeId",
			basicDocumentDLFileEntryType.getFileEntryTypeId());

		FileEntry updatedFileEntry = dlAppService.updateFileEntry(
			checkedOutFileEntry.getFileEntryId(),
			checkedOutFileEntry.getFileName(),
			checkedOutFileEntry.getMimeType(), checkedOutFileEntry.getTitle(),
			StringPool.BLANK, checkedOutFileEntry.getDescription(),
			StringUtil.randomString(), DLVersionNumberIncrease.NONE, null, 0,
			null, null, null, serviceContext);

		dlAppService.checkInFileEntry(
			updatedFileEntry.getFileEntryId(), DLVersionNumberIncrease.NONE,
			StringUtil.randomString(), serviceContext);

		FileEntry checkedInFileEntry = dlAppService.getFileEntry(
			updatedFileEntry.getFileEntryId());

		DLFileEntry checkedInDLFileEntry =
			(DLFileEntry)checkedInFileEntry.getModel();

		Assert.assertEquals(
			basicDocumentDLFileEntryType.getFileEntryTypeId(),
			checkedInDLFileEntry.getFileEntryTypeId());
	}

	@Test
	public void testShouldUpdateFileEntryWithWorkflowWithVersionIncrement()
		throws Exception {

		int workflowInstanceLinksCount =
			WorkflowInstanceLinkLocalServiceUtil.
				getWorkflowInstanceLinksCount();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = dlAppService.addFolder(
			null, group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId());

		FileVersion initialFileVersion = fileEntry.getFileVersion();

		Assert.assertEquals("1.0", initialFileVersion.getVersion());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, initialFileVersion.getStatus());

		_assignSingleApproverWorkflow(folder, serviceContext);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), null, null, null, true);

		FileVersion fileVersion = fileEntry.getLatestFileVersion(true);

		Assert.assertEquals("2.0", fileVersion.getVersion());

		dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		dlAppService.checkInFileEntry(
			fileEntry.getFileEntryId(), DLVersionNumberIncrease.MINOR,
			RandomTestUtil.randomString(), serviceContext);

		fileEntry = dlAppService.getFileEntry(fileEntry.getFileEntryId());

		fileVersion = fileEntry.getLatestFileVersion(true);

		Assert.assertEquals("2.1", fileVersion.getVersion());
		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, fileVersion.getStatus());

		Assert.assertEquals(
			2, fileEntry.getFileVersionsCount(WorkflowConstants.STATUS_ANY));
		Assert.assertEquals(
			workflowInstanceLinksCount + 1,
			WorkflowInstanceLinkLocalServiceUtil.
				getWorkflowInstanceLinksCount());
	}

	@Test
	public void testShouldUpdateTagNamesWithNoVersionIncrement()
		throws Exception {

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, new String[] {"tag1", "tag2"});

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		FileEntry checkedOutFileEntry = dlAppService.getFileEntry(
			fileEntry.getFileEntryId());

		FileVersion latestFileVersion =
			checkedOutFileEntry.getLatestFileVersion(true);

		AssetEntry latestFileVersionAssetEntry =
			AssetEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(),
				latestFileVersion.getPrimaryKey());

		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2"},
			latestFileVersionAssetEntry.getTagNames());

		serviceContext.setAssetTagNames(new String[] {"tag3", "tag4"});

		FileEntry updatedFileEntry = dlAppService.updateFileEntry(
			checkedOutFileEntry.getFileEntryId(),
			checkedOutFileEntry.getFileName(),
			checkedOutFileEntry.getMimeType(), checkedOutFileEntry.getTitle(),
			StringPool.BLANK, checkedOutFileEntry.getDescription(),
			StringUtil.randomString(), DLVersionNumberIncrease.NONE, null, 0,
			null, null, null, serviceContext);

		dlAppService.checkInFileEntry(
			updatedFileEntry.getFileEntryId(), DLVersionNumberIncrease.NONE,
			StringUtil.randomString(), serviceContext);

		FileEntry checkedInFileEntry = dlAppService.getFileEntry(
			updatedFileEntry.getFileEntryId());

		FileVersion lastFileVersion = checkedInFileEntry.getFileVersion();

		AssetEntry lastFileVersionAssetEntry =
			AssetEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(),
				lastFileVersion.getPrimaryKey());

		Assert.assertArrayEquals(
			new String[] {"tag3", "tag4"},
			lastFileVersionAssetEntry.getTagNames());
	}

	private void _assignSingleApproverWorkflow(
			Folder folder, ServiceContext serviceContext)
		throws Exception {

		serviceContext.setAttribute(
			"restrictionType", DLFolderConstants.RESTRICTION_TYPE_WORKFLOW);
		serviceContext.setAttribute(
			"workflowDefinition" +
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL,
			"Single Approver@1");

		dlAppService.updateFolder(
			folder.getFolderId(), folder.getName(), folder.getDescription(),
			serviceContext);
	}

	private static final String _SERIALIZED_DDM_FORM_VALUES =
		StringBundler.concat(
			"{\"availableLanguageIds\":[\"en_US\"],\"defaultLanguageId\":",
			"\"en_US\",\"fieldValues\":[{\"instanceId\":\"pvik\",\"name\":",
			"\"select2305\",\"value\":{\"en_US\":[\"strong\"]}},",
			"{\"instanceId\":\"wwtk\",\"name\":\"select3229\",\"value\":",
			"{\"en_US\":[\"advisor\"]}},{\"instanceId\":\"cclm\",\"name\":",
			"\"select4282\",\"value\":{\"en_US\":[\"awareness\"]}}]}");

}