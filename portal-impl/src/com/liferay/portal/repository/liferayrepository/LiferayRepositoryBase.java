/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public abstract class LiferayRepositoryBase extends LiferayBase {

	public LiferayRepositoryBase(
		RepositoryService repositoryService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService, long repositoryId) {

		this.repositoryService = repositoryService;
		this.dlFileEntryLocalService = dlFileEntryLocalService;
		this.dlFileEntryService = dlFileEntryService;
		this.dlFileVersionLocalService = dlFileVersionLocalService;
		this.dlFileVersionService = dlFileVersionService;
		this.dlFolderLocalService = dlFolderLocalService;
		this.dlFolderService = dlFolderService;

		initByRepositoryId(repositoryId);
	}

	public LiferayRepositoryBase(
		RepositoryService repositoryService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService, long folderId, long fileEntryId,
		long fileVersionId) {

		this.repositoryService = repositoryService;
		this.dlFileEntryLocalService = dlFileEntryLocalService;
		this.dlFileEntryService = dlFileEntryService;
		this.dlFileVersionLocalService = dlFileVersionLocalService;
		this.dlFileVersionService = dlFileVersionService;
		this.dlFolderLocalService = dlFolderLocalService;
		this.dlFolderService = dlFolderService;

		if (folderId != 0) {
			initByFolderId(folderId);
		}
		else if (fileEntryId != 0) {
			initByFileEntryId(fileEntryId);
		}
		else if (fileVersionId != 0) {
			initByFileVersionId(fileVersionId);
		}
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	protected long getGroupId() {
		return _groupId;
	}

	protected void initByFileEntryId(long fileEntryId) {
		try {
			DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
				fileEntryId);

			initByRepositoryId(dlFileEntry.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFileEntryException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	protected void initByFileVersionId(long fileVersionId) {
		try {
			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.getFileVersion(fileVersionId);

			initByRepositoryId(dlFileVersion.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFileVersionException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	protected void initByFolderId(long folderId) {
		try {
			DLFolder dlFolder = dlFolderLocalService.getFolder(folderId);

			initByRepositoryId(dlFolder.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFolderException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	protected void initByRepositoryId(long repositoryId) {
		_repositoryId = repositoryId;
		_groupId = repositoryId;

		try {
			Repository repository = repositoryService.getRepository(
				repositoryId);

			_repositoryId = repository.getRepositoryId();
			_groupId = repository.getGroupId();
			_dlFolderId = repository.getDlFolderId();
		}
		catch (Exception e) {
		}
	}

	protected boolean isDefaultRepository() {
		if (_groupId == _repositoryId) {
			return false;
		}
		else {
			return true;
		}
	}

	protected long toFolderId(long folderId) {
		if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			isDefaultRepository()) {

			return _dlFolderId;
		}
		else {
			return folderId;
		}
	}

	protected List<Long> toFolderIds(List<Long> folderIds) {
		List<Long> toFolderIds = new ArrayList<Long>(folderIds.size());

		for (long folderId : folderIds) {
			toFolderIds.add(toFolderId(folderId));
		}

		return toFolderIds;
	}

	protected DLFileEntryLocalService dlFileEntryLocalService;
	protected DLFileEntryService dlFileEntryService;
	protected DLFileVersionLocalService dlFileVersionLocalService;
	protected DLFileVersionService dlFileVersionService;
	protected DLFolderLocalService dlFolderLocalService;
	protected DLFolderService dlFolderService;
	protected RepositoryService repositoryService;

	private static Log _log = LogFactoryUtil.getLog(
		LiferayRepositoryBase.class);

	private long _dlFolderId;
	private long _groupId;
	private long _repositoryId;

}