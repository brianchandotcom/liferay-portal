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

package com.liferay.dynamic.data.mapping.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructurePersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMStructureTableReferenceDefinition
	implements TableReferenceDefinition<DDMStructureTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<DDMStructureTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				DDMStructureTable.INSTANCE,
				DDMStructureTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				DDMStructureTable.INSTANCE,
				DDMStructureTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMStructureTable.INSTANCE,
				DDMStructureTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMStructureTable.INSTANCE,
				DDMStructureTable.INSTANCE.versionUserId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.versionUserName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				DDMStructureTable aliasDDMStructureTable =
					DDMStructureTable.INSTANCE.as("aliasDDMStructureTable");

				return fromStep.from(
					aliasDDMStructureTable
				).innerJoinON(
					DDMStructureTable.INSTANCE,
					DDMStructureTable.INSTANCE.parentStructureId.eq(
						aliasDDMStructureTable.structureId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.structureKey);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.version);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.definition);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.storageType);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureTable.INSTANCE.lastPublishDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				DDMStructureTable.INSTANCE,
				DDMStructureTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.like(
						"%" + DDMStructure.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.scope.eq(
						ResourceConstants.SCOPE_INDIVIDUAL)
				).and(
					DDMStructureTable.INSTANCE.structureId.eq(
						ResourcePermissionTable.INSTANCE.primKeyId)
				)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmStructurePersistence;
	}

	@Override
	public DDMStructureTable getTable() {
		return DDMStructureTable.INSTANCE;
	}

	@Reference
	private DDMStructurePersistence _ddmStructurePersistence;

}