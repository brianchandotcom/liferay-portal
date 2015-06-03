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

import com.liferay.portal.kernel.search.filter.BooleanFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eduardo Garcia
 */
public class FolderSearcher extends BaseSearcher {

	public static Indexer getInstance() {
		return new FolderSearcher();
	}

	public FolderSearcher() {
		setDefaultSelectedFieldNames(Field.TITLE, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);

		List<String> folderClassNames = new ArrayList<>();

		for (Indexer indexer : IndexerRegistryUtil.getIndexers()) {
			if (indexer instanceof FolderIndexer) {
				FolderIndexer folderIndexer = (FolderIndexer)indexer;

				for (String folderClassName :
						folderIndexer.getFolderClassNames()) {

					folderClassNames.add(folderClassName);
				}
			}
		}

		_classNames = folderClassNames.toArray(
			new String[folderClassNames.size()]);
	}

	@Override
	public String[] getSearchClassNames() {
		return _classNames;
	}

	@Override
	protected BooleanQuery createFullQuery(
			BooleanFilter fullQueryBooleanFilter, SearchContext searchContext)
		throws Exception {

		long[] folderIds = searchContext.getFolderIds();

		BooleanFilter entryClassPKFilter = new BooleanFilter();

		for (long folderId : folderIds) {
			entryClassPKFilter.addTerm(Field.ENTRY_CLASS_PK, folderId);
		}

		fullQueryBooleanFilter.add(entryClassPKFilter, BooleanClauseOccur.MUST);

		return super.createFullQuery(fullQueryBooleanFilter, searchContext);
	}

	private final String[] _classNames;

}