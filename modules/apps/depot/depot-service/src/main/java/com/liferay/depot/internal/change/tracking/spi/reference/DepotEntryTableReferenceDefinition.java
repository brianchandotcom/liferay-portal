/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryTable;
import com.liferay.depot.service.persistence.DepotEntryPersistence;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class DepotEntryTableReferenceDefinition
	implements TableReferenceDefinition<DepotEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DepotEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			DepotEntryTable.INSTANCE.depotEntryId, GroupTable.INSTANCE.classPK,
			DepotEntry.class
		).resourcePermissionReference(
			DepotEntryTable.INSTANCE.depotEntryId, DepotEntry.class
		).singleColumnReference(
			DepotEntryTable.INSTANCE.groupId, LayoutSetTable.INSTANCE.groupId
		).singleColumnReference(
			DepotEntryTable.INSTANCE.groupId,
			UserGroupRoleTable.INSTANCE.groupId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DepotEntryTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _depotEntryPersistence;
	}

	@Override
	public DepotEntryTable getTable() {
		return DepotEntryTable.INSTANCE;
	}

	@Reference
	private DepotEntryPersistence _depotEntryPersistence;

}