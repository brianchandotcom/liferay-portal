/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.search;

import com.liferay.bookmarks.internal.search.spi.model.index.contributor.BookmarksEntryModelIndexerWriterContributor;
import com.liferay.bookmarks.internal.search.spi.model.result.contributor.BookmarksEntryModelSummaryContributor;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(service = ModelSearchConfigurator.class)
public class BookmarksEntryModelSearchConfigurator
	implements ModelSearchConfigurator<BookmarksEntry> {

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public String[] getDefaultSelectedFieldNames() {
		return new String[] {
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.TITLE, Field.UID, Field.URL
		};
	}

	@Override
	public ModelIndexerWriterContributor<BookmarksEntry>
		getModelIndexerWriterContributor() {

		return _modelIndexWriterContributor;
	}

	@Override
	public ModelSummaryContributor getModelSummaryContributor() {
		return _modelSummaryContributor;
	}

	@Activate
	protected void activate() {
		_modelIndexWriterContributor =
			new BookmarksEntryModelIndexerWriterContributor(
				_bookmarksEntryLocalService,
				new BookmarksFolderBatchReindexer(
					_indexerDocumentBuilder, _indexerWriter),
				_dynamicQueryBatchIndexingActionableFactory);
	}

	@Reference
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private IndexerDocumentBuilder _indexerDocumentBuilder;

	@Reference(
		target = "(indexer.class.name=com.liferay.bookmarks.model.BookmarksFolder)"
	)
	private IndexerWriter<BookmarksFolder> _indexerWriter;

	private ModelIndexerWriterContributor<BookmarksEntry>
		_modelIndexWriterContributor;
	private final ModelSummaryContributor _modelSummaryContributor =
		new BookmarksEntryModelSummaryContributor();

}