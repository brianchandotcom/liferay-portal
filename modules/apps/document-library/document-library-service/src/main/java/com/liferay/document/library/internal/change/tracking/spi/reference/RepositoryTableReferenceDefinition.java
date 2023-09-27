/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileShortcutTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.portal.kernel.model.RepositoryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RepositoryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class RepositoryTableReferenceDefinition
	implements TableReferenceDefinition<RepositoryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<RepositoryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			RepositoryTable.INSTANCE.repositoryId,
			CTSContentTable.INSTANCE.repositoryId
		).singleColumnReference(
			RepositoryTable.INSTANCE.repositoryId,
			DLFileEntryTable.INSTANCE.repositoryId
		).singleColumnReference(
			RepositoryTable.INSTANCE.repositoryId,
			DLFileShortcutTable.INSTANCE.repositoryId
		).singleColumnReference(
			RepositoryTable.INSTANCE.repositoryId,
			DLFileVersionTable.INSTANCE.repositoryId
		).singleColumnReference(
			RepositoryTable.INSTANCE.dlFolderId, DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			RepositoryTable.INSTANCE.repositoryId,
			DLFolderTable.INSTANCE.repositoryId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<RepositoryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(RepositoryTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _repositoryPersistence;
	}

	@Override
	public RepositoryTable getTable() {
		return RepositoryTable.INSTANCE;
	}

	@Reference
	private RepositoryPersistence _repositoryPersistence;

}