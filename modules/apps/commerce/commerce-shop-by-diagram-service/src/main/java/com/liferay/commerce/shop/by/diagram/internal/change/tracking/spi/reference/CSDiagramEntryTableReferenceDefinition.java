/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.commerce.product.model.CProductTable;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntryTable;
import com.liferay.commerce.shop.by.diagram.service.persistence.CSDiagramEntryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CSDiagramEntryTableReferenceDefinition
	implements TableReferenceDefinition<CSDiagramEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CSDiagramEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CSDiagramEntryTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		).singleColumnReference(
			CSDiagramEntryTable.INSTANCE.CPInstanceId,
			CPInstanceTable.INSTANCE.CPInstanceId
		).singleColumnReference(
			CSDiagramEntryTable.INSTANCE.CProductId,
			CProductTable.INSTANCE.CProductId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CSDiagramEntryTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _csDiagramEntryPersistence;
	}

	@Override
	public CSDiagramEntryTable getTable() {
		return CSDiagramEntryTable.INSTANCE;
	}

	@Reference
	private CSDiagramEntryPersistence _csDiagramEntryPersistence;

}