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

package com.liferay.sharing.document.library.internal.security.permission.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.contributor.PermissionSQLContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.sharing.test.util.BaseSharingPermissionSQLContributorTestCase;

import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLFileEntrySharingPermissionSQLContributorTest
	extends BaseSharingPermissionSQLContributorTestCase<DLFileEntry> {

	@Override
	protected DLFileEntry getModel(User user, Group group)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), "text/plain", StringUtil.randomString(),
			StringUtil.randomString(), StringPool.BLANK, "test".getBytes(),
			serviceContext);

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected int getModelCount(Group group) throws PortalException {
		return _dlAppService.getFileEntriesCount(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	protected PermissionSQLContributor getPermissionSQLContributor() {
		return _permissionSQLContributor;
	}

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@Inject
	private static DLAppService _dlAppService;

	@Inject(
		filter = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		type = PermissionSQLContributor.class
	)
	private static PermissionSQLContributor _permissionSQLContributor;

}