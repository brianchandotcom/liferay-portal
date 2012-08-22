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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public abstract class DLBaseTrashHandler extends BaseTrashHandler {

	@Override
	public ContainerModel getContainer(long containerId)
		throws PortalException, SystemException {

		return (ContainerModel)getDLFolder(containerId);
	}

	@Override
	public List<ContainerModel> getContainers(
			long entryId, long containerId, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(entryId);

		List<Folder> folders = repository.getFolders(
			containerId, false, start, end, null);

		List<ContainerModel> containers = new ArrayList<ContainerModel>();

		for (Folder folder : folders) {
			containers.add((ContainerModel)folder.getModel());
		}

		return containers;
	}

	@Override
	public int getContainersCount(long entryId, long containerId)
		throws PortalException, SystemException {

		Repository repository = getRepository(entryId);

		return repository.getFoldersCount(containerId, false);
	}

	@Override
	public String getRootContainerName() {
		return "home";
	}

	protected DLFileEntry getDLFileEntry(long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(0, classPK);

		FileEntry fileEntry = repository.getFileEntry(classPK);

		return (DLFileEntry)fileEntry.getModel();
	}

	protected DLFileShortcut getDLFileShortcut(long classPK)
		throws PortalException, SystemException {

		return DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);
	}

	protected DLFolder getDLFolder(long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK, 0);

		Folder folder = repository.getFolder(classPK);

		return (DLFolder)folder.getModel();
	}

	protected Repository getRepository(long entryId)
		throws PortalException, SystemException {

		TrashEntry entry = getEntry(entryId);

		String className = entry.getClassName();

		if (className.equals(DLFolder.class.getName())) {
			return getRepository(entry.getClassPK(), 0);
		}

		return getRepository(0, entry.getClassPK());
	}

	protected Repository getRepository(long folderId, long fileEntryid)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			folderId, fileEntryid, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

}