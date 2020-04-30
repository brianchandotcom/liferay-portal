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
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
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
public class DLFileVersionTableReferenceDefinition
	implements TableReferenceDefinition<DLFileVersionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DLFileVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DLFileVersionTable.INSTANCE
		).nonreferenceColumns(
			DLFileVersionTable.INSTANCE.changeLog,
			DLFileVersionTable.INSTANCE.checksum,
			DLFileVersionTable.INSTANCE.createDate,
			DLFileVersionTable.INSTANCE.description,
			DLFileVersionTable.INSTANCE.extension,
			DLFileVersionTable.INSTANCE.extraSettings,
			DLFileVersionTable.INSTANCE.fileName,
			DLFileVersionTable.INSTANCE.lastPublishDate,
			DLFileVersionTable.INSTANCE.mimeType,
			DLFileVersionTable.INSTANCE.modifiedDate,
			DLFileVersionTable.INSTANCE.size,
			DLFileVersionTable.INSTANCE.status,
			DLFileVersionTable.INSTANCE.statusByUserId,
			DLFileVersionTable.INSTANCE.statusByUserName,
			DLFileVersionTable.INSTANCE.statusDate,
			DLFileVersionTable.INSTANCE.title,
			DLFileVersionTable.INSTANCE.treePath,
			DLFileVersionTable.INSTANCE.userName,
			DLFileVersionTable.INSTANCE.uuid,
			DLFileVersionTable.INSTANCE.version
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileVersionPersistence;
	}

	@Override
	public DLFileVersionTable getTable() {
		return DLFileVersionTable.INSTANCE;
	}

	@Reference
	private DLFileVersionPersistence _dlFileVersionPersistence;

}