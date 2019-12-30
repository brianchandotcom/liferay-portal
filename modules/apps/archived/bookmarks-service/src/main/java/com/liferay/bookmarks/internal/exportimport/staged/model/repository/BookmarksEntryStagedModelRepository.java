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

package com.liferay.bookmarks.internal.exportimport.staged.model.repository;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.bookmarks.model.BookmarksEntry",
	service = {
		BookmarksEntryStagedModelRepository.class, StagedModelRepository.class
	}
)
public class BookmarksEntryStagedModelRepository
	implements StagedModelRepository<BookmarksEntry> {

	@Override
	public BookmarksEntry addStagedModel(
			PortletDataContext portletDataContext,
			BookmarksEntry bookmarksEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			bookmarksEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			bookmarksEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(bookmarksEntry.getUuid());
		}

		return _bookmarksEntryLocalService.addEntry(
			userId, bookmarksEntry.getGroupId(), bookmarksEntry.getFolderId(),
			bookmarksEntry.getName(), bookmarksEntry.getUrl(),
			bookmarksEntry.getDescription(), serviceContext);
	}

	@Override
	public void deleteStagedModel(BookmarksEntry bookmarksEntry)
		throws PortalException {

		_bookmarksEntryLocalService.deleteEntry(bookmarksEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		BookmarksEntry bookmarksEntry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (bookmarksEntry != null) {
			deleteStagedModel(bookmarksEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_bookmarksEntryLocalService.deleteEntries(
			portletDataContext.getScopeGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	public BookmarksEntry fetchMissingReference(String uuid, long groupId) {
		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public BookmarksEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _bookmarksEntryLocalService.fetchBookmarksEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BookmarksEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _bookmarksEntryLocalService.
			getBookmarksEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _bookmarksEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public BookmarksEntry getStagedModel(long entryId) throws PortalException {
		return _bookmarksEntryLocalService.getBookmarksEntry(entryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			BookmarksEntry bookmarksEntry)
		throws PortletDataException {

		BookmarksEntry existingBookmarksEntry =
			fetchStagedModelByUuidAndGroupId(
				bookmarksEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingBookmarksEntry == null) ||
			!_stagedModelRepositoryHelper.isStagedModelInTrash(
				existingBookmarksEntry)) {

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			BookmarksEntry.class.getName());

		try {
			if (trashHandler.isRestorable(
					existingBookmarksEntry.getEntryId())) {

				long userId = portletDataContext.getUserId(
					bookmarksEntry.getUserUuid());

				trashHandler.restoreTrashEntry(
					userId, existingBookmarksEntry.getEntryId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	@Override
	public BookmarksEntry saveStagedModel(BookmarksEntry bookmarksEntry) {
		return _bookmarksEntryLocalService.updateBookmarksEntry(bookmarksEntry);
	}

	@Override
	public BookmarksEntry updateStagedModel(
		PortletDataContext portletDataContext, BookmarksEntry bookmarksEntry) {

		throw new UnsupportedOperationException();
	}

	public BookmarksEntry updateStagedModel(
			PortletDataContext portletDataContext,
			BookmarksEntry bookmarksEntry, long existingEntryId)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			bookmarksEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			bookmarksEntry);

		return _bookmarksEntryLocalService.updateEntry(
			userId, existingEntryId, bookmarksEntry.getGroupId(),
			bookmarksEntry.getFolderId(), bookmarksEntry.getName(),
			bookmarksEntry.getUrl(), bookmarksEntry.getDescription(),
			serviceContext);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}