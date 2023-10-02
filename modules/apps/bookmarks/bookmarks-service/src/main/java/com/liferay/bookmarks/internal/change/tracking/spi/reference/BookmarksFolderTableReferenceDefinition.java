/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.internal.change.tracking.spi.reference;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderTable;
import com.liferay.bookmarks.service.persistence.BookmarksFolderPersistence;
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
public class BookmarksFolderTableReferenceDefinition
	implements TableReferenceDefinition<BookmarksFolderTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<BookmarksFolderTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			BookmarksFolderTable.INSTANCE.folderId, BookmarksFolder.class
		).resourcePermissionReference(
			BookmarksFolderTable.INSTANCE.folderId, BookmarksFolder.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<BookmarksFolderTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			BookmarksFolderTable.INSTANCE
		).parentColumnReference(
			BookmarksFolderTable.INSTANCE.folderId,
			BookmarksFolderTable.INSTANCE.parentFolderId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _bookmarksFolderPersistence;
	}

	@Override
	public BookmarksFolderTable getTable() {
		return BookmarksFolderTable.INSTANCE;
	}

	@Reference
	private BookmarksFolderPersistence _bookmarksFolderPersistence;

}