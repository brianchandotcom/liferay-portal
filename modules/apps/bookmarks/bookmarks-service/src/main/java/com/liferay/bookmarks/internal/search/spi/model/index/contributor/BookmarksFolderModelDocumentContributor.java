/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search.spi.model.index.contributor;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 */
@Component(
	property = "indexer.class.name=com.liferay.bookmarks.model.BookmarksFolder",
	service = ModelDocumentContributor.class
)
public class BookmarksFolderModelDocumentContributor
	implements ModelDocumentContributor<BookmarksFolder> {

	@Override
	public void contribute(Document document, BookmarksFolder bookmarksFolder) {
		document.addText(Field.DESCRIPTION, bookmarksFolder.getDescription());
		document.addKeyword(
			Field.FOLDER_ID, bookmarksFolder.getParentFolderId());
		document.addText(Field.TITLE, bookmarksFolder.getName());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(bookmarksFolder.getTreePath(), CharPool.SLASH));
	}

}