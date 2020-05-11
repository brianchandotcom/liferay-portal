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
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
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
		TableReferenceInfoBuilder<RoleTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumn(
			RoleTable.INSTANCE.uuid
		).singleColumnReference(
			RoleTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			RoleTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			RoleTable.INSTANCE.userName, RoleTable.INSTANCE.createDate,
			RoleTable.INSTANCE.modifiedDate, RoleTable.INSTANCE.classNameId,
			RoleTable.INSTANCE.classPK, RoleTable.INSTANCE.name,
			RoleTable.INSTANCE.title, RoleTable.INSTANCE.description,
			RoleTable.INSTANCE.type, RoleTable.INSTANCE.subtype
		);
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