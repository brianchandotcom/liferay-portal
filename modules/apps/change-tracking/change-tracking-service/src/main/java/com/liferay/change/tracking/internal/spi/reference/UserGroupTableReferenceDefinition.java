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