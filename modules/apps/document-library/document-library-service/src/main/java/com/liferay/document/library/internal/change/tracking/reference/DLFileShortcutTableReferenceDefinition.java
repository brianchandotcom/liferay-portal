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

package com.liferay.document.library.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileShortcutPersistence;
import com.liferay.portal.kernel.model.RepositoryTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileShortcutTableReferenceDefinition
	implements TableReferenceDefinition<DLFileShortcutTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DLFileShortcutTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DLFileShortcutTable.INSTANCE
		).nonreferenceColumn(
			DLFileShortcutTable.INSTANCE.uuid
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.toFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).nonreferenceColumns(
			DLFileShortcutTable.INSTANCE.treePath,
			DLFileShortcutTable.INSTANCE.active,
			DLFileShortcutTable.INSTANCE.lastPublishDate,
			DLFileShortcutTable.INSTANCE.status
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DLFileShortcutTable.INSTANCE.statusByUserName,
			DLFileShortcutTable.INSTANCE.statusDate
		).assetEntryReference(
			DLFileShortcutTable.INSTANCE.fileShortcutId, DLFileShortcut.class
		).resourcePermissionReference(
			DLFileShortcutTable.INSTANCE.fileShortcutId, DLFileShortcut.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileShortcutPersistence;
	}

	@Override
	public DLFileShortcutTable getTable() {
		return DLFileShortcutTable.INSTANCE;
	}

	@Reference
	private DLFileShortcutPersistence _dlFileShortcutPersistence;

}