/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.permission.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.ResourceActionTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ResourcePermissionTableReferenceDefinition
	implements TableReferenceDefinition<ResourcePermissionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ResourcePermissionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourceActionTable.INSTANCE
			).innerJoinON(
				ResourcePermissionTable.INSTANCE,
				DSLFunctionFactoryUtil.bitAnd(
					ResourcePermissionTable.INSTANCE.actionIds,
					ResourceActionTable.INSTANCE.bitwiseValue
				).eq(
					ResourceActionTable.INSTANCE.bitwiseValue
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ResourcePermissionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ResourcePermissionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ResourcePermissionTable.INSTANCE.roleId, RoleTable.INSTANCE.roleId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _resourcePermissionPersistence;
	}

	@Override
	public ResourcePermissionTable getTable() {
		return ResourcePermissionTable.INSTANCE;
	}

	@Reference
	private ResourcePermissionPersistence _resourcePermissionPersistence;

}