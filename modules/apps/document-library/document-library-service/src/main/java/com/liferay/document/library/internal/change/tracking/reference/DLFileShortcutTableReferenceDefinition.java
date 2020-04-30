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
import com.liferay.document.library.kernel.model.DLFileShortcutTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileShortcutPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
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
		).nonreferenceColumns(
			DLFileShortcutTable.INSTANCE.active,
			DLFileShortcutTable.INSTANCE.createDate,
			DLFileShortcutTable.INSTANCE.lastPublishDate,
			DLFileShortcutTable.INSTANCE.modifiedDate,
			DLFileShortcutTable.INSTANCE.status,
			DLFileShortcutTable.INSTANCE.statusByUserId,
			DLFileShortcutTable.INSTANCE.statusByUserName,
			DLFileShortcutTable.INSTANCE.statusDate,
			DLFileShortcutTable.INSTANCE.toFileEntryId,
			DLFileShortcutTable.INSTANCE.treePath,
			DLFileShortcutTable.INSTANCE.userName,
			DLFileShortcutTable.INSTANCE.uuid
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).resourcePermissionReference(
			DLFileShortcutTable.INSTANCE.fileShortcutId,
			DLFileShortcutTable.class
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