/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileVersionServiceImpl extends DLFileVersionServiceBaseImpl {

	@Override
	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		DLFileVersion fileVersion = dlFileVersionLocalService.getFileVersion(
			fileVersionId);

		_fileEntryModelResourcePermission.check(
			getPermissionChecker(), fileVersion.getFileEntryId(),
			ActionKeys.VIEW);

		return fileVersion;
	}

	@Override
	public List<DLFileVersion> getFileVersions(long fileEntryId, int status)
		throws PortalException {

		_fileEntryModelResourcePermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileVersionLocalService.getFileVersions(fileEntryId, status);
	}

	@Override
	public int getFileVersionsCount(long fileEntryId, int status)
		throws PortalException {

		_fileEntryModelResourcePermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileVersionPersistence.countByF_S(fileEntryId, status);
	}

	@Override
	public DLFileVersion getLatestFileVersion(long fileEntryId)
		throws PortalException {

		_fileEntryModelResourcePermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileVersionLocalService.getLatestFileVersion(
			getGuestOrUserId(), fileEntryId);
	}

	@Override
	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException {

		_fileEntryModelResourcePermission.check(
			getPermissionChecker(), fileEntryId, ActionKeys.VIEW);

		return dlFileVersionLocalService.getLatestFileVersion(
			fileEntryId, excludeWorkingCopy);
	}

	private static volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileVersionServiceImpl.class,
				"_fileEntryModelResourcePermission", FileEntry.class);

}