/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.depot.model.DepotEntryGroupRelTable;
import com.liferay.depot.model.DepotEntryTable;
import com.liferay.depot.service.persistence.DepotEntryGroupRelPersistence;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class DepotEntryGroupRelTableReferenceDefinition
	implements TableReferenceDefinition<DepotEntryGroupRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DepotEntryGroupRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DepotEntryGroupRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			DepotEntryGroupRelTable.INSTANCE.depotEntryId,
			DepotEntryTable.INSTANCE.depotEntryId
		).singleColumnReference(
			DepotEntryGroupRelTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _depotEntryGroupRelPersistence;
	}

	@Override
	public DepotEntryGroupRelTable getTable() {
		return DepotEntryGroupRelTable.INSTANCE;
	}

	@Reference
	private DepotEntryGroupRelPersistence _depotEntryGroupRelPersistence;

}