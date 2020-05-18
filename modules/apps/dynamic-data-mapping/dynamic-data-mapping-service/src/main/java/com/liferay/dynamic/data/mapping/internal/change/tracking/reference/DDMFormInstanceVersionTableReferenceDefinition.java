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
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceVersionPersistence;
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
public class DDMFormInstanceVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDMFormInstanceVersionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMFormInstanceVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.userId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMFormInstanceVersionTable.INSTANCE.userName,
			DDMFormInstanceVersionTable.INSTANCE.createDate
		).singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.formInstanceId,
			DDMFormInstanceTable.INSTANCE.formInstanceId
		).singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.structureVersionId,
			DDMStructureVersionTable.INSTANCE.structureVersionId
		).nonreferenceColumns(
			DDMFormInstanceVersionTable.INSTANCE.name,
			DDMFormInstanceVersionTable.INSTANCE.description,
			DDMFormInstanceVersionTable.INSTANCE.settings,
			DDMFormInstanceVersionTable.INSTANCE.version,
			DDMFormInstanceVersionTable.INSTANCE.status
		).singleColumnReference(
			DDMFormInstanceVersionTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMFormInstanceVersionTable.INSTANCE.statusByUserName,
			DDMFormInstanceVersionTable.INSTANCE.statusDate
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstanceVersionPersistence;
	}

	@Override
	public DDMFormInstanceVersionTable getTable() {
		return DDMFormInstanceVersionTable.INSTANCE;
	}

	@Reference
	private DDMFormInstanceVersionPersistence
		_ddmFormInstanceVersionPersistence;

}