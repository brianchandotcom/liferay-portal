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
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Joao Victor Alves
 */
public class BookmarksEntryStagedModelRepositoryUtil {

	public static BookmarksEntry updateStagedModel(
			PortletDataContext portletDataContext,
			BookmarksEntry bookmarksEntry, long existingEntryId)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			bookmarksEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			bookmarksEntry);

		BookmarksEntryLocalService bookmarksEntryLocalService =
			_bookmarksEntryLocalServiceSnapshot.get();

		return bookmarksEntryLocalService.updateEntry(
			userId, existingEntryId, bookmarksEntry.getGroupId(),
			bookmarksEntry.getFolderId(), bookmarksEntry.getName(),
			bookmarksEntry.getUrl(), bookmarksEntry.getDescription(),
			serviceContext);
	}

	private static final Snapshot<BookmarksEntryLocalService>
		_bookmarksEntryLocalServiceSnapshot = new Snapshot<>(
			BookmarksEntryStagedModelRepositoryUtil.class,
			BookmarksEntryLocalService.class);

}