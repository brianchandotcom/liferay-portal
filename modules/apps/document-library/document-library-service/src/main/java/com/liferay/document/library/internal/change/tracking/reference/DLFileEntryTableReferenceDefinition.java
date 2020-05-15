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
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.content.model.DLContentTable;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.RepositoryTable;
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
			DLFileEntryTable.INSTANCE.uuid,
			DLFileEntryTable.INSTANCE.classNameId,
			DLFileEntryTable.INSTANCE.classPK
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.folderId, DLFolderTable.INSTANCE.folderId
		).nonreferenceColumns(
			DLFileEntryTable.INSTANCE.treePath, DLFileEntryTable.INSTANCE.name,
			DLFileEntryTable.INSTANCE.fileName,
			DLFileEntryTable.INSTANCE.extension,
			DLFileEntryTable.INSTANCE.mimeType, DLFileEntryTable.INSTANCE.title,
			DLFileEntryTable.INSTANCE.description,
			DLFileEntryTable.INSTANCE.extraSettings
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
		).nonreferenceColumns(
			DLFileEntryTable.INSTANCE.version, DLFileEntryTable.INSTANCE.size
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.smallImageId, ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.largeImageId, ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.custom1ImageId,
			ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.custom2ImageId,
			ImageTable.INSTANCE.imageId
		).nonreferenceColumns(
			DLFileEntryTable.INSTANCE.manualCheckInRequired,
			DLFileEntryTable.INSTANCE.lastPublishDate
		).assetEntryReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntry.class
		).resourcePermissionReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntry.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.companyId.eq(
					CTSContentTable.INSTANCE.companyId
				).and(
					DLFileEntryTable.INSTANCE.repositoryId.eq(
						CTSContentTable.INSTANCE.repositoryId)
				).and(
					DLFileEntryTable.INSTANCE.name.eq(
						CTSContentTable.INSTANCE.path)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DLContentTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.companyId.eq(
					DLContentTable.INSTANCE.companyId
				).and(
					DLFileEntryTable.INSTANCE.repositoryId.eq(
						DLContentTable.INSTANCE.repositoryId)
				).and(
					DLFileEntryTable.INSTANCE.name.eq(
						DLContentTable.INSTANCE.path)
				)
			)
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