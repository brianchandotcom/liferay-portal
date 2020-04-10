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

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
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
		TableReferenceDefinitionHelper<GroupTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromTable -> fromTable.from(
				UserTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.creatorUserId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				GroupTable aliasGroupTable = GroupTable.INSTANCE.as(
					"aliasGroupTable");

				return fromStep.from(
					aliasGroupTable
				).innerJoinON(
					GroupTable.INSTANCE,
					GroupTable.INSTANCE.parentGroupId.eq(
						aliasGroupTable.groupId)
				);
			});

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				GroupTable aliasGroupTable = GroupTable.INSTANCE.as(
					"aliasGroupTable");

				return fromStep.from(
					aliasGroupTable
				).innerJoinON(
					GroupTable.INSTANCE,
					GroupTable.INSTANCE.liveGroupId.eq(aliasGroupTable.groupId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.treePath);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.groupKey);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.typeSettings);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.manualMembership);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.membershipRestriction);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.friendlyURL);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.site);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.remoteStagingGroupCount);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.inheritContent);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			GroupTable.INSTANCE.active);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.groupId.eq(AssetEntryTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					Group.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId)
				)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						Group.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						GroupTable.INSTANCE.groupId)
				)
			));
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