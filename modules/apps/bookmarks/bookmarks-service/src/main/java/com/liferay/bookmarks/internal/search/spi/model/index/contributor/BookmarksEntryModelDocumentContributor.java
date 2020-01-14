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

package com.liferay.bookmarks.internal.search.spi.model.index.contributor;

import com.liferay.bookmarks.model.BookmarksEntry;
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
	immediate = true,
	property = "indexer.class.name=com.liferay.bookmarks.model.BookmarksEntry",
	service = ModelDocumentContributor.class
)
public class BookmarksEntryModelDocumentContributor
	implements ModelDocumentContributor<BookmarksEntry> {

	@Override
	public void contribute(Document document, BookmarksEntry bookmarksEntry) {
		document.addText(Field.DESCRIPTION, bookmarksEntry.getDescription());
		document.addKeyword(Field.FOLDER_ID, bookmarksEntry.getFolderId());
		document.addText(Field.TITLE, bookmarksEntry.getName());
		document.addKeyword(
			Field.TREE_PATH,
			StringUtil.split(bookmarksEntry.getTreePath(), CharPool.SLASH));
		document.addText(Field.URL, bookmarksEntry.getUrl());
	}

}