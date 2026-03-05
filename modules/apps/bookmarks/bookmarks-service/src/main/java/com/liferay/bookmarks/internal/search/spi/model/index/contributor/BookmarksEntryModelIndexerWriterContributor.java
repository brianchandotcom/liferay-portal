/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search.spi.model.index.contributor;

import com.liferay.bookmarks.internal.search.BookmarksFolderBatchReindexer;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;

/**
 * @author Luan Maoski
 */
public class BookmarksEntryModelIndexerWriterContributor
	extends ModelIndexerWriterContributor<BookmarksEntry> {

	public BookmarksEntryModelIndexerWriterContributor(
		BookmarksEntryLocalService bookmarksEntryLocalService,
		BookmarksFolderBatchReindexer bookmarksFolderBatchReindexer) {

		super(bookmarksEntryLocalService::getIndexableActionableDynamicQuery);

		_bookmarksFolderBatchReindexer = bookmarksFolderBatchReindexer;
	}

	@Override
	public void customize(
		IndexableActionableDynamicQuery indexableActionableDynamicQuery,
		IndexerDocumentBuilder indexerDocumentBuilder) {

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_IN_TRASH
						}));
			});
		indexableActionableDynamicQuery.setPerformActionMethod(
			(BookmarksEntry bookmarksEntry) -> {
				_bookmarksFolderBatchReindexer.reindex(
					bookmarksEntry.getFolderId(),
					bookmarksEntry.getCompanyId());

				return indexerDocumentBuilder.getDocument(bookmarksEntry);
			});
	}

	@Override
	public void modelIndexed(BookmarksEntry bookmarksEntry) {
		_bookmarksFolderBatchReindexer.reindex(
			bookmarksEntry.getFolderId(), bookmarksEntry.getCompanyId());
	}

	private final BookmarksFolderBatchReindexer _bookmarksFolderBatchReindexer;

}