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
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class RoleTableReferenceDefinition
	implements TableReferenceDefinition<RoleTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<RoleTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				RoleTable.INSTANCE,
				RoleTable.INSTANCE.companyId.eq(CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				RoleTable.INSTANCE,
				RoleTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.title);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RoleTable.INSTANCE.subtype);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _rolePersistence;
	}

	@Override
	public RoleTable getTable() {
		return RoleTable.INSTANCE;
	}

	@Reference
	private RolePersistence _rolePersistence;

}