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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.FolderSearcher;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class FolderTitleLookupImpl implements FolderTitleLookup {

	public FolderTitleLookupImpl(HttpServletRequest request) {
		_request = request;
	}

	@Override
	public String getFolderTitle(long curFolderId) {
		Hits results = searchFolder(curFolderId);

		if (results.getLength() == 0) {
			return null;
		}

		Document document = results.doc(0);

		Field field = document.getField(Field.TITLE);

		return field.getValue();
	}

	protected SearchContext getSearchContext(long curFolderId) {
		SearchContext searchContext = SearchContextFactory.getInstance(
			_request);

		searchContext.setFolderIds(new long[] {curFolderId});
		searchContext.setKeywords(StringPool.BLANK);

		return searchContext;
	}

	protected Hits searchFolder(long curFolderId) {
		FolderSearcher folderSearcher = new FolderSearcher();

		try {
			return folderSearcher.search(getSearchContext(curFolderId));
		}
		catch (SearchException se) {
			throw new RuntimeException(se);
		}
	}

	private final HttpServletRequest _request;

}