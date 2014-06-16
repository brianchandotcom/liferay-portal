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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileVersionLocalServiceImpl
	extends DLFileVersionLocalServiceBaseImpl {

	@Override
	public DLFileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return dlFileVersionPersistence.findByPrimaryKey(fileVersionId);
	}

	@Override
	public DLFileVersion getFileVersion(long fileEntryId, String version)
		throws PortalException {

		return dlFileVersionPersistence.findByF_V(fileEntryId, version);
	}

	@Override
	public DLFileVersion getFileVersionByUuidAndGroupId(
		String uuid, long groupId) {

		return dlFileVersionPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public List<DLFileVersion> getFileVersions(long fileEntryId, int status) {
		List<DLFileVersion> dlFileVersions = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			dlFileVersions = dlFileVersionPersistence.findByFileEntryId(
				fileEntryId);
		}
		else {
			dlFileVersions = dlFileVersionPersistence.findByF_S(
				fileEntryId, status);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		return dlFileVersions;
	}

	@Override
	public int getFileVersionsCount(long fileEntryId, int status) {
		return dlFileVersionPersistence.countByF_S(fileEntryId, status);
	}

	@Override
	public DLFileVersion getLatestFileVersion(
			long fileEntryId, boolean excludeWorkingCopy)
		throws PortalException {

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(fileEntryId);

		if (dlFileVersions.isEmpty()) {
			throw new NoSuchFileVersionException(
				"No file versions found for fileEntryId " + fileEntryId);
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, new FileVersionVersionComparator());

		DLFileVersion dlFileVersion = dlFileVersions.get(0);

		String version = dlFileVersion.getVersion();

		if (excludeWorkingCopy &&
			version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

			return dlFileVersions.get(1);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion getLatestFileVersion(long userId, long fileEntryId)
		throws PortalException {

		boolean excludeWorkingCopy = true;

		if (dlFileEntryLocalService.isFileEntryCheckedOut(fileEntryId)) {
			excludeWorkingCopy = !dlFileEntryLocalService.hasFileEntryLock(
				userId, fileEntryId);
		}

		return getLatestFileVersion(fileEntryId, excludeWorkingCopy);
	}

	@Override
	public void rebuildTree(long companyId) {
		dlFolderLocalService.rebuildTree(companyId);

		Session session = dlFileVersionPersistence.openSession();

		try {
			TreePathUtil.rebuildTree(
				session, companyId, DLFileVersionModelImpl.TABLE_NAME,
				DLFolderModelImpl.TABLE_NAME, "folderId", true);
		}
		finally {
			dlFileVersionPersistence.closeSession(session);

			dlFileVersionPersistence.clearCache();
		}
	}

}