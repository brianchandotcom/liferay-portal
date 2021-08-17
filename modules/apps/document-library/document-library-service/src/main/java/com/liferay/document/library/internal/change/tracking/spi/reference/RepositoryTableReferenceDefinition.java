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