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

package com.liferay.document.library.demo.data.creator.internal;

import com.liferay.document.library.demo.data.creator.BaseFolderDemoDataCreator;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseFolderDemoDataCreatorImpl
	implements BaseFolderDemoDataCreator {

	@Override
	public void delete() throws PortalException {
		for (long folderId : _folderIds) {
			try {
				dlAppLocalService.deleteFolder(folderId);
			}
			catch (NoSuchFolderException nsfe) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsfe, nsfe);
				}
			}

			_folderIds.remove(folderId);
		}
	}

	protected Folder createFolder(
			long userId, long groupId, long folderId, String name)
		throws PortalException {

		Folder folder = null;

		try {
			folder = dlAppLocalService.getFolder(groupId, folderId, name);
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsfe, nsfe);
			}

			folder = dlAppLocalService.addFolder(
				userId, groupId, folderId, name, StringPool.BLANK,
				new ServiceContext());
		}

		_folderIds.add(folder.getFolderId());

		return folder;
	}

	@Reference
	protected DLAppLocalService dlAppLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFolderDemoDataCreatorImpl.class);

	private final List<Long> _folderIds = new CopyOnWriteArrayList<>();

}