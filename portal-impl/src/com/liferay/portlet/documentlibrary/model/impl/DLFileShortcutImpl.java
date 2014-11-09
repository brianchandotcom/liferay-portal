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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutImpl extends DLFileShortcutBaseImpl {

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return StringPool.SLASH;
		}

		DLFolder dlFolder = getDLFolder();

		return dlFolder.buildTreePath();
	}

	@Override
	public DLFolder getDLFolder() throws PortalException {
		Folder folder = getFolder();

		return (DLFolder)folder.getModel();
	}

	@Override
	public Folder getFolder() throws PortalException {
		if (getFolderId() <= 0) {
			return new LiferayFolder(new DLFolderImpl());
		}

		return DLAppLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public String getToTitle() {
		String toTitle = null;

		try {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				getToFileEntryId());

			toTitle = fileEntry.getTitle();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return toTitle;
	}

	@Override
	public boolean isInHiddenFolder() {
		try {
			long repositoryId = getRepositoryId();

			Repository repository = RepositoryLocalServiceUtil.getRepository(
				repositoryId);

			long dlFolderId = repository.getDlFolderId();

			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(dlFolderId);

			return dlFolder.isHidden();
		}
		catch (Exception e) {
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileShortcutImpl.class);

}