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
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.RepositoryTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileEntryTableReferenceDefinition
	implements TableReferenceDefinition<DLFileEntryTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DLFileEntryTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DLFileEntryTable.INSTANCE
		).nonreferenceColumns(
			DLFileEntryTable.INSTANCE.classNameId,
			DLFileEntryTable.INSTANCE.classPK,
			DLFileEntryTable.INSTANCE.createDate,
			DLFileEntryTable.INSTANCE.custom1ImageId,
			DLFileEntryTable.INSTANCE.custom2ImageId,
			DLFileEntryTable.INSTANCE.description,
			DLFileEntryTable.INSTANCE.extension,
			DLFileEntryTable.INSTANCE.extraSettings,
			DLFileEntryTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTable.INSTANCE.fileName,
			DLFileEntryTable.INSTANCE.largeImageId,
			DLFileEntryTable.INSTANCE.lastPublishDate,
			DLFileEntryTable.INSTANCE.manualCheckInRequired,
			DLFileEntryTable.INSTANCE.mimeType,
			DLFileEntryTable.INSTANCE.modifiedDate,
			DLFileEntryTable.INSTANCE.name, DLFileEntryTable.INSTANCE.size,
			DLFileEntryTable.INSTANCE.smallImageId,
			DLFileEntryTable.INSTANCE.title, DLFileEntryTable.INSTANCE.treePath,
			DLFileEntryTable.INSTANCE.userName, DLFileEntryTable.INSTANCE.uuid,
			DLFileEntryTable.INSTANCE.version
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.folderId, DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
		).assetEntryReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntryTable.class
		).resourcePermissionReference(
			DLFileEntryTable.INSTANCE.fileEntryId, Group.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileEntryPersistence;
	}

	@Override
	public DLFileEntryTable getTable() {
		return DLFileEntryTable.INSTANCE;
	}

	@Reference
	private DLFileEntryPersistence _dlFileEntryPersistence;

}