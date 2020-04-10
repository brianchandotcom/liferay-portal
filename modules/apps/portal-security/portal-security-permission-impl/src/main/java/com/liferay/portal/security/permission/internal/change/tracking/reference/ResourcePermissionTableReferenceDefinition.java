/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.permission.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.ResourceActionTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceDefinitionHelper<ResourcePermissionTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				ResourcePermissionTable.INSTANCE,
				ResourcePermissionTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ResourcePermissionTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ResourcePermissionTable.INSTANCE.scope);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ResourcePermissionTable.INSTANCE.primKey);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ResourcePermissionTable.INSTANCE.primKeyId);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				RoleTable.INSTANCE
			).innerJoinON(
				ResourcePermissionTable.INSTANCE,
				ResourcePermissionTable.INSTANCE.roleId.eq(
					RoleTable.INSTANCE.roleId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				ResourcePermissionTable.INSTANCE,
				ResourcePermissionTable.INSTANCE.ownerId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			ResourcePermissionTable.INSTANCE.viewActionId);
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