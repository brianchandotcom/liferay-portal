/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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