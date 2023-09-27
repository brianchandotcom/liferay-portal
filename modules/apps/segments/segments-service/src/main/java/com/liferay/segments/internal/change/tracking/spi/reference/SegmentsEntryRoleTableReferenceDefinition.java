/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntryRoleTable;
import com.liferay.segments.service.persistence.SegmentsEntryRolePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsEntryRoleTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsEntryRoleTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsEntryRoleTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsEntryRoleTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			SegmentsEntryRoleTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			SegmentsEntryRoleTable.INSTANCE.roleId, RoleTable.INSTANCE.roleId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsEntryRolePersistence;
	}

	@Override
	public SegmentsEntryRoleTable getTable() {
		return SegmentsEntryRoleTable.INSTANCE;
	}

	@Reference
	private SegmentsEntryRolePersistence _segmentsEntryRolePersistence;

}