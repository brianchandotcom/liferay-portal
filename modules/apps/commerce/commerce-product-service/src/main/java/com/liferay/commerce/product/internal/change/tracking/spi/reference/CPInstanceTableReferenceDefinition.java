/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPInstanceOptionValueRelTable;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasureTable;
import com.liferay.commerce.product.service.persistence.CPInstancePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPInstanceTableReferenceDefinition
	implements TableReferenceDefinition<CPInstanceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPInstanceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CPInstanceTable.INSTANCE.CPInstanceId,
			CPInstanceOptionValueRelTable.INSTANCE.CPInstanceId
		).singleColumnReference(
			CPInstanceTable.INSTANCE.CPInstanceId,
			CPInstanceUnitOfMeasureTable.INSTANCE.CPInstanceId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPInstanceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPInstanceTable.INSTANCE
		).singleColumnReference(
			CPInstanceTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpInstancePersistence;
	}

	@Override
	public CPInstanceTable getTable() {
		return CPInstanceTable.INSTANCE;
	}

	@Reference
	private CPInstancePersistence _cpInstancePersistence;

}