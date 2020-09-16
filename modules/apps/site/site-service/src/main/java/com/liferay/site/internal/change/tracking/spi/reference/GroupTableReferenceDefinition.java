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

package com.liferay.site.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class GroupTableReferenceDefinition
	implements TableReferenceDefinition<GroupTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<GroupTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			GroupTable.INSTANCE.groupId, Group.class
		).resourcePermissionReference(
			GroupTable.INSTANCE.groupId, Group.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.scope.eq(
						ResourceConstants.SCOPE_GROUP)
				).and(
					GroupTable.INSTANCE.groupId.eq(
						ResourcePermissionTable.INSTANCE.primKeyId)
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<GroupTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			GroupTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			GroupTable.INSTANCE.groupId, GroupTable.INSTANCE.parentGroupId
		).parentColumnReference(
			GroupTable.INSTANCE.groupId, GroupTable.INSTANCE.liveGroupId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _groupPersistence;
	}

	@Override
	public GroupTable getTable() {
		return GroupTable.INSTANCE;
	}

	@Reference
	private GroupPersistence _groupPersistence;

}