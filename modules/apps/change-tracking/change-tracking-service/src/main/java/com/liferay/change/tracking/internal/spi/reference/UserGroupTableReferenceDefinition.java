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

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class UserGroupTableReferenceDefinition
	implements TableReferenceDefinition<UserGroupTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<UserGroupTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			UserGroupTable.INSTANCE.userGroupId, GroupTable.INSTANCE.classPK,
			UserGroup.class
		).resourcePermissionReference(
			UserGroupTable.INSTANCE.userGroupId, UserGroup.class
		).systemEventReference(
			UserGroupTable.INSTANCE.userGroupId, UserGroup.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<UserGroupTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			UserGroupTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			UserGroupTable.INSTANCE.userGroupId,
			UserGroupTable.INSTANCE.parentUserGroupId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _userGroupPersistence;
	}

	@Override
	public UserGroupTable getTable() {
		return UserGroupTable.INSTANCE;
	}

	@Reference
	private UserGroupPersistence _userGroupPersistence;

}