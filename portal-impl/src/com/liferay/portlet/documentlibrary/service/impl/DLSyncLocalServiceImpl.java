/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.model.DLSync;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLSyncLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Michael Young
 */
public class DLSyncLocalServiceImpl extends DLSyncLocalServiceBaseImpl {

	/**
	 * @deprecated {@link #addSync(long, String, long, long, long, String,
	 *             String, String, String)}
	 */
	public DLSync addSync(
			long fileId, String fileUuid, long companyId, long repositoryId,
			long parentFolderId, String name, String type, String version)
		throws SystemException {

		return addSync(
			fileId, fileUuid, companyId, repositoryId, parentFolderId, name,
			StringPool.BLANK, type, version);
	}

	public DLSync addSync(
			long fileId, String fileUuid, long companyId, long repositoryId,
			long parentFolderId, String name, String description, String type,
			String version)
		throws SystemException {

		Date now = new Date();

		long syncId = counterLocalService.increment();

		DLSync dlSync = dlSyncPersistence.create(syncId);

		dlSync.setCompanyId(companyId);
		dlSync.setCreateDate(now);
		dlSync.setDescription(description);
		dlSync.setModifiedDate(now);
		dlSync.setFileId(fileId);
		dlSync.setFileUuid(fileUuid);
		dlSync.setRepositoryId(repositoryId);
		dlSync.setParentFolderId(parentFolderId);
		dlSync.setEvent(DLSyncConstants.EVENT_ADD);
		dlSync.setType(type);
		dlSync.setName(name);
		dlSync.setVersion(version);

		dlSyncPersistence.update(dlSync, false);

		return dlSync;
	}

	/**
	 * @deprecated {@link #updateSync(long, long, String, String, String,
	 *             String)}
	 */
	public DLSync updateSync(
			long fileId, long parentFolderId, String name, String event,
			String version)
		throws PortalException, SystemException {

		return updateSync(
			fileId, parentFolderId, name, StringPool.BLANK, event, version);
	}

	public DLSync updateSync(
			long fileId, long parentFolderId, String name, String description,
			String event, String version)
		throws PortalException, SystemException {

		DLSync dlSync = null;

		if (event == DLSyncConstants.EVENT_DELETE) {
			dlSync = dlSyncPersistence.fetchByFileId(fileId);

			if (dlSync == null) {
				return null;
			}
		}
		else {
			dlSync = dlSyncPersistence.findByFileId(fileId);
		}

		dlSync.setModifiedDate(new Date());
		dlSync.setParentFolderId(parentFolderId);
		dlSync.setName(name);
		dlSync.setDescription(description);
		dlSync.setEvent(event);
		dlSync.setVersion(version);

		dlSyncPersistence.update(dlSync, false);

		return dlSync;
	}

}