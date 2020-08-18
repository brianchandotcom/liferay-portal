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
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.model.DLFileVersionPreviewTable;
import com.liferay.document.library.service.persistence.DLFileVersionPreviewPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileVersionPreviewTableReferenceDefinition
	implements TableReferenceDefinition<DLFileVersionPreviewTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileVersionPreviewTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileVersionPreviewTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileVersionPreviewTable.INSTANCE
		).singleColumnReference(
			DLFileVersionPreviewTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).singleColumnReference(
			DLFileVersionPreviewTable.INSTANCE.fileVersionId,
			DLFileVersionTable.INSTANCE.fileVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileVersionPreviewPersistence;
	}

	@Override
	public DLFileVersionPreviewTable getTable() {
		return DLFileVersionPreviewTable.INSTANCE;
	}

	@Reference
	private DLFileVersionPreviewPersistence _dlFileVersionPreviewPersistence;

}