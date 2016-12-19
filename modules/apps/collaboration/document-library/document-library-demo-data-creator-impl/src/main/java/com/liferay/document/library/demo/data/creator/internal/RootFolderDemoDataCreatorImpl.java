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

import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = RootFolderDemoDataCreator.class)
public class RootFolderDemoDataCreatorImpl
	implements RootFolderDemoDataCreator {

	@Override
	public Folder create(long userId, long groupId) throws PortalException {
		return create(userId, groupId, "Demo");
	}

	@Override
	public Folder create(long userId, long groupId, String name)
		throws PortalException {

		Folder folder = _dlAppLocalService.addFolder(
			userId, groupId, 0, name, StringPool.BLANK, new ServiceContext());

		_folderIds.add(folder.getFolderId());

		return folder;
	}

	@Override
	public void delete() throws PortalException {
		for (long folderId : _folderIds) {
			_dlAppLocalService.deleteFolder(folderId);
		}
	}

	@Reference(unbind = "-")
	protected void setDlAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private DLAppLocalService _dlAppLocalService;
	private final List<Long> _folderIds = new ArrayList<>();

}