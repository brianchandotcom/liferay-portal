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