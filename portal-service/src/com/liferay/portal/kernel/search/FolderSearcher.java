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

package com.liferay.portal.kernel.search;

import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.journal.model.JournalFolder;

/**
 * @author Eduardo Garcia
 */
public class FolderSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		BookmarksFolder.class.getName(), DLFolder.class.getName(),
		JournalFolder.class.getName()
	};

	public static Indexer getInstance() {
		return new FolderSearcher();
	}

	public FolderSearcher() {
		setDefaultSelectedFieldNames(Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		long[] folderIds = searchContext.getFolderIds();

		BooleanQuery entryClassPKQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long folderId : folderIds) {
			entryClassPKQuery.addTerm(Field.ENTRY_CLASS_PK, folderId);
		}

		contextQuery.add(entryClassPKQuery, BooleanClauseOccur.MUST);

		return super.createFullQuery(contextQuery, searchContext);
	}

}