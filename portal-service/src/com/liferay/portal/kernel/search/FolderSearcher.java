/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Eduardo Garcia
 */
public class FolderSearcher extends BaseIndexer {

	public static final String[] CLASS_NAMES = {
		BookmarksFolder.class.getName(), DLFolder.class.getName(),
		JournalFolder.class.getName()
	};

	public static Indexer getInstance() {
		return new FolderSearcher();
	}

	public FolderSearcher() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getPortletId() {
		return null;
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		throw new UnsupportedOperationException();
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

	@Override
	protected void doDelete(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return null;
	}

}