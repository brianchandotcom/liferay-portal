/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search.spi.model.index.contributor;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Luan Maoski
 */
public class BookmarksFolderModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<BookmarksFolder> {

	public BookmarksFolderModelIndexerWriterContributor(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _bookmarksFolderLocalService.
			getIndexableActionableDynamicQuery();
	}

	private final BookmarksFolderLocalService _bookmarksFolderLocalService;

}