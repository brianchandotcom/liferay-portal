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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<UserGroupTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.defineNonreferenceColumns(
			UserGroupTable.INSTANCE.uuid,
			UserGroupTable.INSTANCE.externalReferenceCode);

		tableReferenceInfoBuilder.defineSingleColumnReference(
			UserGroupTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId);

		tableReferenceInfoBuilder.defineSingleColumnReference(
			UserGroupTable.INSTANCE.userId, UserTable.INSTANCE.userId);

		tableReferenceInfoBuilder.defineNonreferenceColumns(
			UserGroupTable.INSTANCE.userName,
			UserGroupTable.INSTANCE.createDate,
			UserGroupTable.INSTANCE.modifiedDate);

		tableReferenceInfoBuilder.defineParentColumnReference(
			UserGroupTable.INSTANCE.userGroupId,
			UserGroupTable.INSTANCE.parentUserGroupId);

		tableReferenceInfoBuilder.defineNonreferenceColumns(
			UserGroupTable.INSTANCE.name, UserGroupTable.INSTANCE.description,
			UserGroupTable.INSTANCE.addedByLDAPImport);

		tableReferenceInfoBuilder.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				UserGroupTable.INSTANCE,
				UserGroupTable.INSTANCE.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					UserGroupTable.INSTANCE.userGroupId.eq(
						GroupTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					GroupTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(UserGroup.class.getName())
				)
			));

		tableReferenceInfoBuilder.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				UserGroupTable.INSTANCE,
				UserGroupTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						UserGroup.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						UserGroupTable.INSTANCE.userGroupId)
				)
			));
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