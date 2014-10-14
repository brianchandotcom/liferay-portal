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

		List<String> folderClassNames = new ArrayList<String>();

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
	public String[] getClassNames() {
		return _classNames;
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

	private final String[] _classNames;

}