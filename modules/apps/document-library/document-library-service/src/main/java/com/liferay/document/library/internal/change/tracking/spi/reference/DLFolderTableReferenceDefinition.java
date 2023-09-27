/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFolderTableReferenceDefinition
	implements TableReferenceDefinition<DLFolderTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFolderTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			DLFolderTable.INSTANCE.folderId, DLFolder.class
		).resourcePermissionReference(
			DLFolderTable.INSTANCE.folderId, DLFolder.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFolderTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFolderTable.INSTANCE
		).parentColumnReference(
			DLFolderTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.parentFolderId
		).singleColumnReference(
			DLFolderTable.INSTANCE.defaultFileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFolderPersistence;
	}

	@Override
	public DLFolderTable getTable() {
		return DLFolderTable.INSTANCE;
	}

	@Reference
	private DLFolderPersistence _dlFolderPersistence;

}