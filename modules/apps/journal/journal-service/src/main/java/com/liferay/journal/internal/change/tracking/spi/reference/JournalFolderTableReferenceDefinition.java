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

package com.liferay.journal.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderTable;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalFolderTableReferenceDefinition
	implements TableReferenceDefinition<JournalFolderTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<JournalFolderTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			JournalFolderTable.INSTANCE.folderId, JournalFolder.class
		).resourcePermissionReference(
			JournalFolderTable.INSTANCE.folderId, JournalFolder.class
		).systemEventReference(
			JournalFolderTable.INSTANCE.folderId, JournalFolder.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<JournalFolderTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			JournalFolderTable.INSTANCE
		).parentColumnReference(
			JournalFolderTable.INSTANCE.folderId,
			JournalFolderTable.INSTANCE.parentFolderId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalFolderPersistence;
	}

	@Override
	public JournalFolderTable getTable() {
		return JournalFolderTable.INSTANCE;
	}

	@Reference
	private JournalFolderPersistence _journalFolderPersistence;

}