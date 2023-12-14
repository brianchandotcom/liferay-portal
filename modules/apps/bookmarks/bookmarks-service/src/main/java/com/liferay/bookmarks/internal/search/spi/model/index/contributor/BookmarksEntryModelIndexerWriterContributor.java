/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search.spi.model.index.contributor;

import com.liferay.bookmarks.internal.search.BookmarksFolderBatchReindexer;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

/**
 * @author Luan Maoski
 */
public class BookmarksEntryModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<BookmarksEntry> {

	public BookmarksEntryModelIndexerWriterContributor(
		BookmarksEntryLocalService bookmarksEntryLocalService,
		BookmarksFolderBatchReindexer bookmarksFolderBatchReindexer,
		DynamicQueryBatchIndexingActionableFactory
			dynamicQueryBatchIndexingActionableFactory) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
		_bookmarksFolderBatchReindexer = bookmarksFolderBatchReindexer;
		_dynamicQueryBatchIndexingActionableFactory =
			dynamicQueryBatchIndexingActionableFactory;
	}

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setAddCriteriaMethod(
			dynamicQuery -> {
				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_IN_TRASH
						}));
			});
		batchIndexingActionable.setPerformActionMethod(
			(BookmarksEntry bookmarksEntry) -> {
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						bookmarksEntry));

				_bookmarksFolderBatchReindexer.reindex(
					bookmarksEntry.getFolderId(),
					bookmarksEntry.getCompanyId());
			});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_bookmarksEntryLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(BookmarksEntry bookmarksEntry) {
		return bookmarksEntry.getCompanyId();
	}

	@Override
	public void modelIndexed(BookmarksEntry bookmarksEntry) {
		_bookmarksFolderBatchReindexer.reindex(
			bookmarksEntry.getFolderId(), bookmarksEntry.getCompanyId());
	}

	private final BookmarksEntryLocalService _bookmarksEntryLocalService;
	private final BookmarksFolderBatchReindexer _bookmarksFolderBatchReindexer;
	private final DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

}