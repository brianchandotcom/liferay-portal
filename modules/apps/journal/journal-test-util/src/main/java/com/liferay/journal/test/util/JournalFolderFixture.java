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

package com.liferay.journal.test.util;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class JournalFolderFixture {

	public JournalFolderFixture(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = Objects.requireNonNull(
			journalFolderLocalService);
	}

	public JournalFolder addFolder(
			long userId, long groupId, long parentFolderId, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return addFolder(parentFolderId, name, serviceContext);
	}

	public JournalFolder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return addFolder(parentFolderId, name, serviceContext);
	}

	public JournalFolder addFolder(long groupId, String name) throws Exception {
		return addFolder(
			groupId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
	}

	public JournalFolder addFolder(
			long parentFolderId, String name, ServiceContext serviceContext)
		throws Exception {

		return addFolder(
			parentFolderId, name, "This is a test folder.", serviceContext);
	}

	public JournalFolder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = _journalFolderLocalService.fetchFolder(
			serviceContext.getScopeGroupId(), parentFolderId, name);

		if (folder != null) {
			return folder;
		}

		return _journalFolderLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			parentFolderId, name, description, serviceContext);
	}

	private final JournalFolderLocalService _journalFolderLocalService;

}