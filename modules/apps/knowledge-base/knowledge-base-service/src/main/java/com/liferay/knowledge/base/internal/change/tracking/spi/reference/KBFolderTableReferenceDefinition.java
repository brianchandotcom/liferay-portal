/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBFolderTable;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vy Bui
 */
@Component(service = TableReferenceDefinition.class)
public class KBFolderTableReferenceDefinition
	implements TableReferenceDefinition<KBFolderTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KBFolderTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			KBFolderTable.INSTANCE.kbFolderId, KBFolder.class
		).resourcePermissionReference(
			KBFolderTable.INSTANCE.kbFolderId, KBFolder.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KBFolderTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(KBFolderTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kbFolderPersistence;
	}

	@Override
	public KBFolderTable getTable() {
		return KBFolderTable.INSTANCE;
	}

	@Reference
	private KBFolderPersistence _kbFolderPersistence;

}