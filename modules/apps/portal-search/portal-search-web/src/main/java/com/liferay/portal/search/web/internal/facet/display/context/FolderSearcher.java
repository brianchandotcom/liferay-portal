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

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Eduardo García
 * @author André de Oliveira
 */
public class FolderSearcher extends BaseSearcher {

	public FolderSearcher() {
		setDefaultSelectedFieldNames(Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return _FOLDER_CLASS_NAMES;
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		TermsFilter termsFilter = new TermsFilter(Field.ENTRY_CLASS_PK);

		termsFilter.addValues(
			ArrayUtil.toStringArray(searchContext.getFolderIds()));

		booleanFilter.add(termsFilter, BooleanClauseOccur.MUST);

		return super.createFullQuery(booleanFilter, searchContext);
	}

	private static final String[] _FOLDER_CLASS_NAMES = {
		"com.liferay.bookmarks.model.BookmarksFolder",
		"com.liferay.document.library.kernel.model.DLFolder",
		"com.liferay.journal.model.JournalFolder"
	};

}