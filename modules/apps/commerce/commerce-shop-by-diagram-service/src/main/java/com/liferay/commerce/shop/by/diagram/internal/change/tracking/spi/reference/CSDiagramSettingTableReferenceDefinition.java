/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = TableReferenceDefinition.class)
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