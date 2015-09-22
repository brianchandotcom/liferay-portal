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

package com.liferay.bookmarks.exportimport.staged.model.repository;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksFolder"},
	service = StagedModelRepository.class
)
public class BookmarksFolderStagedModelRepository
	extends BaseStagedModelRepository<BookmarksFolder> {

	@Override
	public void deleteStagedModel(BookmarksFolder bookmarksFolder)
		throws PortalException {

		BookmarksFolderLocalServiceUtil.deleteFolder(bookmarksFolder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BookmarksFolder bookmarksFolder = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (bookmarksFolder != null) {
			deleteStagedModel(bookmarksFolder);
		}
	}

	@Override
	public BookmarksFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return BookmarksFolderLocalServiceUtil.
			fetchBookmarksFolderByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BookmarksFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return BookmarksFolderLocalServiceUtil.
			getBookmarksFoldersByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BookmarksFolder>());
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			BookmarksFolder bookmarksFolder)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(
			bookmarksFolder.getUserUuid());

		BookmarksFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			bookmarksFolder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !isStagedModelInTrash(existingFolder)) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

		try {
			if (trashHandler.isRestorable(existingFolder.getFolderId())) {
				trashHandler.restoreTrashEntry(
					userId, existingFolder.getFolderId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

}