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
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructurePersistence;
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
		TableReferenceInfoBuilder<DDMStructureTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumns(
			DDMStructureTable.INSTANCE.uuid
		).groupedModel(
			DDMStructureTable.INSTANCE
		).singleColumnReference(
			DDMStructureTable.INSTANCE.versionUserId, UserTable.INSTANCE.userId
		).nonreferenceColumn(
			DDMStructureTable.INSTANCE.versionUserName
		).parentColumnReference(
			DDMStructureTable.INSTANCE.structureId,
			DDMStructureTable.INSTANCE.parentStructureId
		).nonreferenceColumns(
			DDMStructureTable.INSTANCE.classNameId,
			DDMStructureTable.INSTANCE.structureKey,
			DDMStructureTable.INSTANCE.version, DDMStructureTable.INSTANCE.name,
			DDMStructureTable.INSTANCE.description,
			DDMStructureTable.INSTANCE.definition,
			DDMStructureTable.INSTANCE.storageType,
			DDMStructureTable.INSTANCE.type,
			DDMStructureTable.INSTANCE.lastPublishDate
		).referenceInnerJoin(
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
			)
		);
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