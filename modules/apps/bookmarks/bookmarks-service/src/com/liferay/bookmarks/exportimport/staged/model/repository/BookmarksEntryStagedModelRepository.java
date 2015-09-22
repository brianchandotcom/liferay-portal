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

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
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
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksEntry"},
	service = StagedModelRepository.class
)
public class BookmarksEntryStagedModelRepository
	extends BaseStagedModelRepository<BookmarksEntry> {

	@Override
	public void deleteStagedModel(BookmarksEntry bookmarksEntry)
		throws PortalException {

		BookmarksEntryLocalServiceUtil.deleteEntry(bookmarksEntry);
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
	public BookmarksEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return BookmarksEntryLocalServiceUtil.
			fetchBookmarksEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<BookmarksEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return BookmarksEntryLocalServiceUtil.
			getBookmarksEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BookmarksEntry>());
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			BookmarksEntry bookmarksEntry)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(
			bookmarksEntry.getUserUuid());

		BookmarksEntry existingBookmarksEntry =
			fetchStagedModelByUuidAndGroupId(
				bookmarksEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingBookmarksEntry == null) ||
			!isStagedModelInTrash(existingBookmarksEntry)) {

			return;
		}

		TrashHandler trashHandler = existingBookmarksEntry.getTrashHandler();

		try {
			if (trashHandler.isRestorable(
					existingBookmarksEntry.getEntryId())) {

				trashHandler.restoreTrashEntry(
					userId, existingBookmarksEntry.getEntryId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

}