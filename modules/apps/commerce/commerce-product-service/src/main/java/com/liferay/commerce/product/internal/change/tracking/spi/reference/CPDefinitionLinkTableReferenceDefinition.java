/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionLinkTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CProductTable;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPDefinitionLinkTableReferenceDefinition
	implements TableReferenceDefinition<CPDefinitionLinkTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPDefinitionLinkTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			CPDefinitionLinkTable.INSTANCE.CPDefinitionLinkId,
			CPDefinitionLink.class
		).singleColumnReference(
			CPDefinitionLinkTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPDefinitionLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPDefinitionLinkTable.INSTANCE
		).singleColumnReference(
			CPDefinitionLinkTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		).singleColumnReference(
			CPDefinitionLinkTable.INSTANCE.CProductId,
			CProductTable.INSTANCE.CProductId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpDefinitionLinkPersistence;
	}

	@Override
	public CPDefinitionLinkTable getTable() {
		return CPDefinitionLinkTable.INSTANCE;
	}

	@Reference
	private CPDefinitionLinkPersistence _cpDefinitionLinkPersistence;

}