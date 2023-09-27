/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasureTable;
import com.liferay.commerce.product.service.persistence.CPInstanceUnitOfMeasurePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = TableReferenceDefinition.class)
public class CPInstanceUnitOfMeasureTableReferenceDefinition
	implements TableReferenceDefinition<CPInstanceUnitOfMeasureTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPInstanceUnitOfMeasureTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPInstanceUnitOfMeasureTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPInstanceUnitOfMeasureTable.INSTANCE.CPInstanceId,
			CPInstanceTable.INSTANCE.CPInstanceId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpInstanceUnitOfMeasurePersistence;
	}

	@Override
	public CPInstanceUnitOfMeasureTable getTable() {
		return CPInstanceUnitOfMeasureTable.INSTANCE;
	}

	@Reference
	private CPInstanceUnitOfMeasurePersistence
		_cpInstanceUnitOfMeasurePersistence;

}