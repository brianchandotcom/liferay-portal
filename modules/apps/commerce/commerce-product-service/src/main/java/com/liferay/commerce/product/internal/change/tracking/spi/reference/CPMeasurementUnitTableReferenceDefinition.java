/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPMeasurementUnitTable;
import com.liferay.commerce.product.service.persistence.CPMeasurementUnitPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPMeasurementUnitTableReferenceDefinition
	implements TableReferenceDefinition<CPMeasurementUnitTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPMeasurementUnitTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPMeasurementUnitTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPMeasurementUnitTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpMeasurementUnitPersistence;
	}

	@Override
	public CPMeasurementUnitTable getTable() {
		return CPMeasurementUnitTable.INSTANCE;
	}

	@Reference
	private CPMeasurementUnitPersistence _cpMeasurementUnitPersistence;

}