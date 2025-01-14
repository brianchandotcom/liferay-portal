/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.test.util;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;

import java.io.Serializable;

/**
 * @author Alexander Chow
 */
public abstract class DLAppTestUtil {

	public static FileEntry addFileEntry(Folder folder) throws Exception {
		return addFileEntry(
			TestPropsValues.getUserId(), folder.getGroupId(),
			folder.getFolderId(), folder.getRepositoryId());
	}

	public static FileEntry addFileEntry(long groupId) throws Exception {
		return addFileEntry(
			TestPropsValues.getUserId(), groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, groupId);
	}

	public static FileEntry addFileEntry(
			long userId, long groupId, long folderId, long repositoryId)
		throws Exception {

		return DLAppLocalServiceUtil.addFileEntry(
			null, userId, repositoryId, folderId, RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			RandomTestUtil.randomBytes(), null, null, null,
			ServiceContextTestUtil.getServiceContext(groupId, userId));
	}

	public static FileEntry addFileEntry(Repository repository)
		throws Exception {

		return addFileEntry(
			TestPropsValues.getUserId(), repository.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			repository.getRepositoryId());
	}

	public static FileEntry addFileEntryWithWorkflow(
			long userId, long groupId, long folderId, String sourceFileName,
			String title, boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				null, userId, groupId, folderId, sourceFileName,
				ContentTypes.TEXT_PLAIN, title, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK,
				TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
				serviceContext);

			if (approved) {
				return updateStatus(fileEntry, serviceContext);
			}

			return fileEntry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static Folder addFolder(long groupId) throws PortalException {
		return DLAppLocalServiceUtil.addFolder(
			null, TestPropsValues.getUserId(), groupId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	public static Folder addFolder(Repository repository)
		throws PortalException {

		return DLAppLocalServiceUtil.addFolder(
			null, TestPropsValues.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				repository.getGroupId(), TestPropsValues.getUserId()));
	}

	public static Repository addRepository(long groupId)
		throws PortalException {

		return RepositoryLocalServiceUtil.addRepository(
			null, TestPropsValues.getUserId(), groupId,
			PortalUtil.getClassNameId(LiferayRepository.class),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	public static void populateNotificationsServiceContext(
		ServiceContext serviceContext, String command) {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	public static void populateServiceContext(
		ServiceContext serviceContext, long fileEntryTypeId) {

		if (fileEntryTypeId !=
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {

			serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	protected static FileEntry updateStatus(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(), fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			HashMapBuilder.<String, Serializable>put(
				WorkflowConstants.CONTEXT_URL, "http://localhost"
			).put(
				"event", "add"
			).build());

		return DLAppLocalServiceUtil.getFileEntry(fileEntry.getFileEntryId());
	}

}