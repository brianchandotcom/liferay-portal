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
		TableReferenceDefinitionHelper<DDMStructureVersionTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.userId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.structureId.eq(
					DDMStructureTable.INSTANCE.structureId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.version);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.parentStructureId.eq(
					DDMStructureTable.INSTANCE.structureId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.definition);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.storageType);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.status);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMStructureVersionTable.INSTANCE,
				DDMStructureVersionTable.INSTANCE.statusByUserId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.statusByUserName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureVersionTable.INSTANCE.statusDate);
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