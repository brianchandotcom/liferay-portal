/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.search.batch.BatchIndexingActionable;
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
		BatchIndexingActionable batchIndexingActionable =
			_indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property folderIdProperty = PropertyFactoryUtil.forName(
					"folderId");

				dynamicQuery.add(folderIdProperty.eq(folderId));
			});
		batchIndexingActionable.setCompanyId(companyId);
		batchIndexingActionable.setPerformActionMethod(
			(BookmarksFolder bookmarksFolder) ->
				batchIndexingActionable.addDocuments(
					_indexerDocumentBuilder.getDocument(bookmarksFolder)));

		batchIndexingActionable.performActions();
	}

	private final IndexerDocumentBuilder _indexerDocumentBuilder;
	private final IndexerWriter<BookmarksFolder> _indexerWriter;

}