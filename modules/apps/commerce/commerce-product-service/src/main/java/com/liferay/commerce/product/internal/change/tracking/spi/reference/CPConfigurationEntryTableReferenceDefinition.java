/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPConfigurationEntryTable;
import com.liferay.commerce.product.model.CPConfigurationListTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.service.persistence.CPConfigurationEntryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = TableReferenceDefinition.class)
public class CPConfigurationEntryTableReferenceDefinition
	implements TableReferenceDefinition<CPConfigurationEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPConfigurationEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CPConfigurationEntryTable.INSTANCE.classPK,
			CPDefinitionTable.INSTANCE.CPDefinitionId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPConfigurationEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPConfigurationEntryTable.INSTANCE.CPConfigurationListId,
			CPConfigurationListTable.INSTANCE.CPConfigurationListId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpConfigurationEntryPersistence;
	}

	@Override
	public CPConfigurationEntryTable getTable() {
		return CPConfigurationEntryTable.INSTANCE;
	}

	@Reference
	private CPConfigurationEntryPersistence _cpConfigurationEntryPersistence;

}