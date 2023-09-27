/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.uad.test;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author William Newbury
 */
public class DLFolderUADTestUtil {

	public static DLFolder addDLFolder(
			DLAppLocalService dlAppLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId)
		throws Exception {

		return addDLFolder(
			dlAppLocalService, dlFolderLocalService, userId, groupId, 0L);
	}

	public static DLFolder addDLFolder(
			DLAppLocalService dlAppLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId, long parentFolderId)
		throws Exception {

		Folder folder = dlAppLocalService.addFolder(
			null, userId, groupId, parentFolderId,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		return (DLFolder)folder.getModel();
	}

	public static DLFolder addDLFolderWithStatusByUserId(
			DLAppLocalService dlAppLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId, long statusByUserId)
		throws Exception {

		DLFolder dlFolder = addDLFolder(
			dlAppLocalService, dlFolderLocalService, userId, groupId);

		return dlFolderLocalService.updateStatus(
			statusByUserId, dlFolder.getFolderId(),
			WorkflowConstants.STATUS_DRAFT, null,
			ServiceContextTestUtil.getServiceContext());
	}

	public static void cleanUpDependencies(List<DLFolder> dlFolders)
		throws Exception {
	}

}