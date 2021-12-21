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

package com.liferay.commerce.shop.by.diagram.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPAttachmentFileEntryTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSettingTable;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramSettingPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CSDiagramSettingTableReferenceDefinition
	implements TableReferenceDefinition<CSDiagramSettingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CSDiagramSettingTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CSDiagramSettingTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		).singleColumnReference(
			CSDiagramSettingTable.INSTANCE.CPAttachmentFileEntryId,
			CPAttachmentFileEntryTable.INSTANCE.CPAttachmentFileEntryId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CSDiagramSettingTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _csDiagramSettingPersistence;
	}

	@Override
	public CSDiagramSettingTable getTable() {
		return CSDiagramSettingTable.INSTANCE;
	}

	@Reference
	private CSDiagramSettingPersistence _csDiagramSettingPersistence;

}