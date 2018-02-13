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

package com.liferay.sync.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.sync.model.SyncDLObject;
import com.liferay.sync.service.SyncDLObjectLocalService;

import java.io.File;

/**
 * @author Matthew Tambara
 */
public interface SyncHelper {

	public void addChecksum(long modifiedTime, long typePK, String checksum);

	public void addSyncDLObject(
			SyncDLObject syncDLObject,
			SyncDLObjectLocalService syncDLObjectLocalService)
		throws PortalException;

	public String buildExceptionMessage(Throwable throwable);

	public void checkSyncEnabled(long groupId) throws PortalException;

	public void enableLanSync(long companyId) throws Exception;

	public String getChecksum(DLFileVersion dlFileVersion);

	public String getChecksum(File file);

	public String getChecksum(long modifiedTime, long typePK);

	public File getFileDelta(File sourceFile, File targetFile)
		throws PortalException;

	public String getLanTokenKey(
		long modifiedTime, long typePK, boolean addToMap);

	public boolean isSupportedFolder(DLFolder dlFolder);

	public boolean isSupportedFolder(Folder folder);

	public boolean isSyncEnabled(Group group);

	public void patchFile(File originalFile, File deltaFile, File patchedFile)
		throws PortalException;

	public void setFilePermissions(
		Group group, boolean folder, ServiceContext serviceContext);

	public SyncDLObject toSyncDLObject(
			DLFileEntry dlFileEntry, String event, boolean calculateChecksum)
		throws PortalException;

	public SyncDLObject toSyncDLObject(
			DLFileEntry dlFileEntry, String event, boolean calculateChecksum,
			boolean excludeWorkingCopy)
		throws PortalException;

	public SyncDLObject toSyncDLObject(
		DLFolder dlFolder, long userId, String userName, String event);

	public SyncDLObject toSyncDLObject(DLFolder dlFolder, String event);

	public SyncDLObject toSyncDLObject(FileEntry fileEntry, String event)
		throws PortalException;

	public SyncDLObject toSyncDLObject(
			FileEntry fileEntry, String event, boolean calculateChecksum)
		throws PortalException;

	public SyncDLObject toSyncDLObject(Folder folder, String event)
		throws PortalException;

}