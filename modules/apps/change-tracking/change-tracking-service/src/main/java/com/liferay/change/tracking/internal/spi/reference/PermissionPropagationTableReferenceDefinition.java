/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.model.PermissionPropagationTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Quan Huynh
 */
@Component(service = TableReferenceDefinition.class)
public class PermissionPropagationTableReferenceDefinition
	implements TableReferenceDefinition<PermissionPropagationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<PermissionPropagationTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			PermissionPropagationTable.INSTANCE.permissionPropagationId,
			PermissionPropagation.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<PermissionPropagationTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _permissionPropagationPersistence;
	}

	@Override
	public PermissionPropagationTable getTable() {
		return PermissionPropagationTable.INSTANCE;
	}

	@Reference
	private PermissionPropagationPersistence _permissionPropagationPersistence;

}