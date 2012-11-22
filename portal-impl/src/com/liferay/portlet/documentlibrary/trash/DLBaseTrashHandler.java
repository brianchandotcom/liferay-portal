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
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zsolt Berentey
 */
public abstract class DLBaseTrashHandler extends BaseTrashHandler {

	@Override
	public List<ContainerModel> getAncestors(long containerModelId)
		throws PortalException, SystemException {

		List<ContainerModel> containerModels = new ArrayList<ContainerModel>();

		ContainerModel containerModel = getContainerModel(containerModelId);

		while (containerModel.getParentContainerModelId() > 0) {
			containerModel = getContainerModel(
				containerModel.getParentContainerModelId());

			if (containerModel == null) {
				break;
			}

			containerModels.add(containerModel);
		}

		return containerModels;
	}

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return (ContainerModel)getDLFolder(containerModelId);
	}

	@Override
	public String getRootContainerModelName() {
		return "home";
	}

	@Override
	public String getTrashContainedModelName() {
		return "documents";
	}

	@Override
	public List<TrashRenderer> getTrashContainedModels(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		List<Object> entries = repository.getFileEntriesAndFileShortcuts(
			classPK, WorkflowConstants.STATUS_ANY, start, end);

		List<TrashRenderer> trashBaseModels = new ArrayList<TrashRenderer>();

		for (Object entry : entries) {
			String curClassName = StringPool.BLANK;
			long curClassPK = 0;

			if (entry instanceof FileEntry) {
				FileEntry fileEntry = (FileEntry)entry;

				curClassName = DLFileEntry.class.getName();
				curClassPK = fileEntry.getPrimaryKey();
			}
			else if (entry instanceof DLFileShortcut) {
				DLFileShortcut dlFileShortcut = (DLFileShortcut)entry;

				curClassName = DLFileShortcut.class.getName();
				curClassPK = dlFileShortcut.getPrimaryKey();
			}
			else {
				continue;
			}

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(curClassName);

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				curClassPK);

			trashBaseModels.add(trashRenderer);
		}

		return trashBaseModels;
	}

	@Override
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		return repository.getFileEntriesCount(classPK);
	}

	@Override
	public String getTrashContainerModelName() {
		return "folders";
	}

	@Override
	public List<TrashRenderer> getTrashContainerModels(
			long classPK, int start, int end)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		List<Folder> folders = repository.getFolders(
			classPK, false, start, end, null);

		List<TrashRenderer> trashContainerModels =
			new ArrayList<TrashRenderer>();

		for (Folder folder : folders) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					DLFolder.class.getName());

			TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
				folder.getPrimaryKey());

			trashContainerModels.add(trashRenderer);
		}

		return trashContainerModels;
	}

	@Override
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		return repository.getFoldersCount(classPK, false);
	}

	protected DLFolder getDLFolder(long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		Folder folder = repository.getFolder(classPK);

		return (DLFolder)folder.getModel();
	}

	protected Repository getRepository(long classPK)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			classPK, 0, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

}