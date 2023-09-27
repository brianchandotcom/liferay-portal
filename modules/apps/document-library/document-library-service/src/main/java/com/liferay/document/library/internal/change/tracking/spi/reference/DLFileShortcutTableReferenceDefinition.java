/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutTable;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileShortcutPersistence;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileShortcutTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			DLFileShortcutTable.INSTANCE.fileShortcutId, DLFileShortcut.class
		).resourcePermissionReference(
			DLFileShortcutTable.INSTANCE.fileShortcutId, DLFileShortcut.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileShortcutTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileShortcutTable.INSTANCE
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileShortcutTable.INSTANCE.toFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
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