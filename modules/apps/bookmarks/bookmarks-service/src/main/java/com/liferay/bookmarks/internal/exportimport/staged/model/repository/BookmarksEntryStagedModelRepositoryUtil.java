/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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