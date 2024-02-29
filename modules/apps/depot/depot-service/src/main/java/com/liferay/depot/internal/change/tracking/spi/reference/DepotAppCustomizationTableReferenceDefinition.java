/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.depot.model.DepotAppCustomizationTable;
import com.liferay.depot.model.DepotEntryTable;
import com.liferay.depot.service.persistence.DepotAppCustomizationPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class DepotAppCustomizationTableReferenceDefinition
	implements TableReferenceDefinition<DepotAppCustomizationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DepotAppCustomizationTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DepotAppCustomizationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			DepotAppCustomizationTable.INSTANCE.depotEntryId,
			DepotEntryTable.INSTANCE.depotEntryId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _depotAppCustomizationPersistence;
	}

	@Override
	public DepotAppCustomizationTable getTable() {
		return DepotAppCustomizationTable.INSTANCE;
	}

	@Reference
	private DepotAppCustomizationPersistence _depotAppCustomizationPersistence;

}