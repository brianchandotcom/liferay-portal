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
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureVersionPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMStructureVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDMStructureVersionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMStructureVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			DDMStructureVersionTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			DDMStructureVersionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMStructureVersionTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMStructureVersionTable.INSTANCE.userName,
			DDMStructureVersionTable.INSTANCE.createDate
		).singleColumnReference(
			DDMStructureVersionTable.INSTANCE.structureId,
			DDMStructureTable.INSTANCE.structureId
		).nonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.version
		).singleColumnReference(
			DDMStructureVersionTable.INSTANCE.parentStructureId,
			DDMStructureTable.INSTANCE.structureId
		).nonreferenceColumns(
			DDMStructureVersionTable.INSTANCE.name,
			DDMStructureVersionTable.INSTANCE.description,
			DDMStructureVersionTable.INSTANCE.definition,
			DDMStructureVersionTable.INSTANCE.storageType,
			DDMStructureVersionTable.INSTANCE.type,
			DDMStructureVersionTable.INSTANCE.status
		).singleColumnReference(
			DDMStructureVersionTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMStructureVersionTable.INSTANCE.statusByUserName,
			DDMStructureVersionTable.INSTANCE.statusDate
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmStructureVersionPersistence;
	}

	@Override
	public DDMStructureVersionTable getTable() {
		return DDMStructureVersionTable.INSTANCE;
	}

	@Reference
	private DDMStructureVersionPersistence _ddmStructureVersionPersistence;

}