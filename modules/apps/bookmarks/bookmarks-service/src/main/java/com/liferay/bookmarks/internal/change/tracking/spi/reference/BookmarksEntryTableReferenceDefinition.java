/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.change.tracking.spi.reference;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksEntryTable;
import com.liferay.bookmarks.model.BookmarksFolderTable;
import com.liferay.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class BookmarksEntryTableReferenceDefinition
	implements TableReferenceDefinition<BookmarksEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<BookmarksEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			BookmarksEntryTable.INSTANCE.entryId, BookmarksEntry.class
		).resourcePermissionReference(
			BookmarksEntryTable.INSTANCE.entryId, BookmarksEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<BookmarksEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			BookmarksEntryTable.INSTANCE
		).singleColumnReference(
			BookmarksEntryTable.INSTANCE.folderId,
			BookmarksFolderTable.INSTANCE.folderId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _bookmarksEntryPersistence;
	}

	@Override
	public BookmarksEntryTable getTable() {
		return BookmarksEntryTable.INSTANCE;
	}

	@Reference
	private BookmarksEntryPersistence _bookmarksEntryPersistence;

}