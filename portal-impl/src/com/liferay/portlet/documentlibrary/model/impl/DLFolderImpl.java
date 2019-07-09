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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFolderImpl extends DLFolderBaseImpl {

	@Override
	public List<Long> getAncestorFolderIds() throws PortalException {
		List<Long> ancestorFolderIds = new ArrayList<>();

		DLFolder folder = this;

		while (!folder.isRoot()) {
			try {
				folder = folder.getParentFolder();

				ancestorFolderIds.add(folder.getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
				if (folder.isInTrash()) {
					break;
				}

				throw nsfe;
			}
		}

		return ancestorFolderIds;
	}

	@Override
	public List<DLFolder> getAncestors() throws PortalException {
		List<DLFolder> ancestors = new ArrayList<>();

		DLFolder folder = this;

		while (!folder.isRoot()) {
			try {
				folder = folder.getParentFolder();

				ancestors.add(folder);
			}
			catch (NoSuchFolderException nsfe) {
				if (folder.isInTrash()) {
					break;
				}

				throw nsfe;
			}
		}

		return ancestors;
	}

	@Override
	public DLFolder getParentFolder() throws PortalException {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return DLFolderLocalServiceUtil.getFolder(getParentFolderId());
	}

	@Override
	public String getPath() throws PortalException {
		StringBuilder sb = new StringBuilder();

		DLFolder folder = this;

		while (folder != null) {
			sb.insert(0, folder.getName());
			sb.insert(0, StringPool.SLASH);

			folder = folder.getParentFolder();
		}

		return sb.toString();
	}

	@Override
	public String[] getPathArray() throws PortalException {
		String path = getPath();

		// Remove leading /

		path = path.substring(1);

		return StringUtil.split(path, CharPool.SLASH);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(DLFolderConstants.getClassName());
	}

	@Override
	public boolean hasInheritableLock() {
		return DLFolderLocalServiceUtil.hasInheritableLock(getFolderId());
	}

	@Override
	public boolean hasLock() {
		try {
			return DLFolderServiceUtil.hasFolderLock(getFolderId());
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isInHiddenFolder() {
		try {
			long repositoryId = getRepositoryId();

			if (getGroupId() == repositoryId) {
				return false;
			}

			Repository repository = RepositoryLocalServiceUtil.getRepository(
				repositoryId);

			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
				repository.getDlFolderId());

			return dlFolder.isHidden();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}

		return false;
	}

	@Override
	public boolean isLocked() {
		return DLFolderServiceUtil.isFolderLocked(getFolderId());
	}

	@Override
	public boolean isRoot() {
		if (getParentFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(DLFolderImpl.class);

}