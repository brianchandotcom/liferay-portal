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

package com.liferay.bookmarks.web.internal.trash;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the trash handler for bookmarks folder entity.
 *
 * @author Eudaldo Alonso
 */
public abstract class BookmarksBaseTrashHandler extends BaseTrashHandler {

	@Override
	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException {

		return BookmarksFolderLocalServiceUtil.getFolder(containerModelId);
	}

	@Override
	public String getContainerModelClassName(long classPK) {
		return BookmarksFolder.class.getName();
	}

	@Override
	public List<ContainerModel> getContainerModels(
			long classPK, long parentContainerModelId, int start, int end)
		throws PortalException {

		List<BookmarksFolder> folders =
			BookmarksFolderLocalServiceUtil.getFolders(
				getGroupId(classPK), parentContainerModelId, start, end);

		List<ContainerModel> containerModels = new ArrayList<>(folders.size());

		for (BookmarksFolder curFolder : folders) {
			containerModels.add(curFolder);
		}

		return containerModels;
	}

	@Override
	public int getContainerModelsCount(
			long classPK, long parentContainerModelId)
		throws PortalException {

		return BookmarksFolderLocalServiceUtil.getFoldersCount(
			getGroupId(classPK), parentContainerModelId);
	}

	@Override
	public List<ContainerModel> getParentContainerModels(long classPK)
		throws PortalException {

		List<ContainerModel> containerModels = new ArrayList<>();

		ContainerModel containerModel = getParentContainerModel(classPK);

		if (containerModel == null) {
			return containerModels;
		}

		containerModels.add(containerModel);

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
	public String getRootContainerModelName() {
		return "folder";
	}

	@Override
	public String getSubcontainerModelName() {
		return "folder";
	}

	@Override
	public String getTrashContainedModelName() {
		return "bookmarks";
	}

	@Override
	public int getTrashContainedModelsCount(long classPK)
		throws PortalException {

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		return BookmarksEntryLocalServiceUtil.getEntriesCount(
			folder.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public String getTrashContainerModelName() {
		return "folders";
	}

	@Override
	public int getTrashContainerModelsCount(long classPK)
		throws PortalException {

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		return BookmarksFolderLocalServiceUtil.getFoldersCount(
			folder.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public int getTrashModelsCount(long classPK) throws PortalException {
		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		return BookmarksFolderLocalServiceUtil.getFoldersAndEntriesCount(
			folder.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Override
	public List<TrashedModel> getTrashModelTrashedModels(
			long classPK, int start, int end, OrderByComparator obc)
		throws PortalException {

		List<TrashedModel> trashedModels = new ArrayList<>();

		BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(
			classPK);

		List<Object> foldersAndEntries =
			BookmarksFolderLocalServiceUtil.getFoldersAndEntries(
				folder.getGroupId(), classPK, WorkflowConstants.STATUS_IN_TRASH,
				start, end, obc);

		for (Object folderOrEntry : foldersAndEntries) {
			if (folderOrEntry instanceof BookmarksFolder) {
				BookmarksFolder curFolder = (BookmarksFolder)folderOrEntry;

				trashedModels.add(curFolder);
			}
			else {
				BookmarksEntry entry = (BookmarksEntry)folderOrEntry;

				trashedModels.add(entry);
			}
		}

		return trashedModels;
	}

	@Override
	public boolean isMovable() {
		return true;
	}

	protected abstract long getGroupId(long classPK) throws PortalException;

}