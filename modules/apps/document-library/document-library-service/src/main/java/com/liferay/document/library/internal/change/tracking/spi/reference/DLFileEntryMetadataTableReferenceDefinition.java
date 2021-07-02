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
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryMetadataTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataPersistence;
import com.liferay.dynamic.data.mapping.model.DDMStorageLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileEntryMetadataTableReferenceDefinition
	implements TableReferenceDefinition<DLFileEntryMetadataTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileEntryMetadataTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			DLFileEntryMetadataTable.INSTANCE.fileEntryMetadataId,
			DDMStructureLinkTable.INSTANCE.classPK, DLFileEntryMetadata.class
		).singleColumnReference(
			DLFileEntryMetadataTable.INSTANCE.DDMStorageId,
			DDMStorageLinkTable.INSTANCE.classPK
		).singleColumnReference(
			DLFileEntryMetadataTable.INSTANCE.DDMStructureId,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileEntryMetadataTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			DLFileEntryMetadataTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DLFileEntryMetadataTable.INSTANCE.fileVersionId,
			DLFileVersionTable.INSTANCE.fileVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileEntryMetadataPersistence;
	}

	@Override
	public DLFileEntryMetadataTable getTable() {
		return DLFileEntryMetadataTable.INSTANCE;
	}

	@Reference
	private DLFileEntryMetadataPersistence _dlFileEntryMetadataPersistence;

}