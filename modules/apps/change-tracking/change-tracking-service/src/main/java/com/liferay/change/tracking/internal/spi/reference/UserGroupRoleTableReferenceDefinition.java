/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class UserGroupRoleTableReferenceDefinition
	implements TableReferenceDefinition<UserGroupRoleTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<UserGroupRoleTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<UserGroupRoleTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			UserGroupRoleTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			UserGroupRoleTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			UserGroupRoleTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			UserGroupRoleTable.INSTANCE.roleId, RoleTable.INSTANCE.roleId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _userGroupRolePersistence;
	}

	@Override
	public UserGroupRoleTable getTable() {
		return UserGroupRoleTable.INSTANCE;
	}

	@Reference
	private UserGroupRolePersistence _userGroupRolePersistence;

}