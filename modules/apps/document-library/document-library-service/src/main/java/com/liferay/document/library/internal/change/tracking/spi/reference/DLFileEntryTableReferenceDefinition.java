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

import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadataTable;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			DLFileEntryTable.INSTANCE.smallImageId, ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.largeImageId, ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.custom1ImageId,
			ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.custom2ImageId,
			ImageTable.INSTANCE.imageId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.fileEntryId,
			DLFileEntryMetadataTable.INSTANCE.fileEntryId
		).assetEntryReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntry.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetDisplayPageEntryTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.groupId.eq(
					AssetDisplayPageEntryTable.INSTANCE.groupId
				).and(
					DLFileEntryTable.INSTANCE.fileEntryId.eq(
						AssetDisplayPageEntryTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					AssetDisplayPageEntryTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(FileEntry.class.getName())
				)
			)
		).resourcePermissionReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntry.class
		).systemEventReference(
			DLFileEntryTable.INSTANCE.fileEntryId, DLFileEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileEntryTable.INSTANCE
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.folderId, DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileEntryTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
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