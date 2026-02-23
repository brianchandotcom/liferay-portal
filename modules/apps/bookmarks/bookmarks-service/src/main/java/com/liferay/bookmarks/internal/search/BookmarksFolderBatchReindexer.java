/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

/**
 * @author Luan Maoski
 */
public class BookmarksFolderBatchReindexer {

	public BookmarksFolderBatchReindexer(
		IndexerDocumentBuilder indexerDocumentBuilder,
		IndexerWriter<BookmarksFolder> indexerWriter) {

		_indexerDocumentBuilder = indexerDocumentBuilder;
		_indexerWriter = indexerWriter;
	}

	public void reindex(long folderId, long companyId) {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			_indexerWriter.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property folderIdProperty = PropertyFactoryUtil.forName(
					"folderId");

				dynamicQuery.add(folderIdProperty.eq(folderId));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(BookmarksFolder bookmarksFolder) ->
				indexableActionableDynamicQuery.addDocument(
					_indexerDocumentBuilder.getDocument(bookmarksFolder)));

		try {
			indexableActionableDynamicQuery.performActions();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<BookmarksFolder> _indexerWriter;

}