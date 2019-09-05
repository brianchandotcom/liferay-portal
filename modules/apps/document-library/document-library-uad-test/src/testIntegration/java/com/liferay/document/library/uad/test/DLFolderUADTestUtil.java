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
			userId, groupId, parentFolderId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
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

	public void cleanUpDependencies(List<DLFolder> dlFolders) throws Exception {
	}

}