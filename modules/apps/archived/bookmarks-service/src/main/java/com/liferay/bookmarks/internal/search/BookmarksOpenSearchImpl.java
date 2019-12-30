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

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.OpenSearch;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = OpenSearch.class)
public class BookmarksOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String TITLE = "Liferay Bookmarks Search: ";

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public Indexer<BookmarksEntry> getIndexer() {
		return IndexerRegistryUtil.getIndexer(BookmarksEntry.class);
	}

	@Override
	public String getSearchPath() {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

}