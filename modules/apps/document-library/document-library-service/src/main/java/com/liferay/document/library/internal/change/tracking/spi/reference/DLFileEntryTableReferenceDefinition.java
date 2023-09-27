/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
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
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.fileEntryId.eq(
					FriendlyURLEntryTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					FriendlyURLEntryTable.INSTANCE.classNameId
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