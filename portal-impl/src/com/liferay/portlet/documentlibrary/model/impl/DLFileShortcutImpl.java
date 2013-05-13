/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutImpl extends DLFileShortcutBaseImpl {

	public DLFileShortcutImpl() {
	}

	@Override
	public Folder getFolder() {
		Folder folder = new LiferayFolder(new DLFolderImpl());

		if (getFolderId() > 0) {
			try {
				folder = DLAppLocalServiceUtil.getFolder(getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
				try {
					if (!isInTrash()) {
						_log.error(nsfe, nsfe);
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return folder;
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
	public DLFolder getTrashContainer() {
		Folder folder = getFolder();

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrash()) {
			return dlFolder;
		}

		return dlFolder.getTrashContainer();
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

	@Override
	public boolean isInTrashContainer() {
		if (getTrashContainer() != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutImpl.class);

}