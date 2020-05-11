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

package com.liferay.site.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<GroupTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumn(
			GroupTable.INSTANCE.uuid
		).singleColumnReference(
			GroupTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			GroupTable.INSTANCE.creatorUserId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			GroupTable.INSTANCE.classNameId, GroupTable.INSTANCE.classPK
		).parentColumnReference(
			GroupTable.INSTANCE.groupId, GroupTable.INSTANCE.parentGroupId
		).parentColumnReference(
			GroupTable.INSTANCE.groupId, GroupTable.INSTANCE.liveGroupId
		).nonreferenceColumns(
			GroupTable.INSTANCE.treePath, GroupTable.INSTANCE.groupKey,
			GroupTable.INSTANCE.name, GroupTable.INSTANCE.description,
			GroupTable.INSTANCE.type, GroupTable.INSTANCE.typeSettings,
			GroupTable.INSTANCE.manualMembership,
			GroupTable.INSTANCE.membershipRestriction,
			GroupTable.INSTANCE.friendlyURL, GroupTable.INSTANCE.site,
			GroupTable.INSTANCE.remoteStagingGroupCount,
			GroupTable.INSTANCE.inheritContent, GroupTable.INSTANCE.active
		).assetEntryReference(
			GroupTable.INSTANCE.groupId, Group.class
		).resourcePermissionReference(
			GroupTable.INSTANCE.groupId, Group.class
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