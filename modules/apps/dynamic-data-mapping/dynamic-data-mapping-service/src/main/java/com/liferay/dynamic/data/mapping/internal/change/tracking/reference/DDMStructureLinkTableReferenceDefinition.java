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
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLinkPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMStructureLinkTableReferenceDefinition
	implements TableReferenceDefinition<DDMStructureLinkTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<DDMStructureLinkTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				DDMStructureLinkTable.INSTANCE,
				DDMStructureLinkTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureLinkTable.INSTANCE.classNameId);
		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMStructureLinkTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				DDMStructureLinkTable.INSTANCE,
				DDMStructureLinkTable.INSTANCE.structureId.eq(
					DDMStructureTable.INSTANCE.structureId)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmStructureLinkPersistence;
	}

	@Override
	public DDMStructureLinkTable getTable() {
		return DDMStructureLinkTable.INSTANCE;
	}

	@Reference
	private DDMStructureLinkPersistence _ddmStructureLinkPersistence;

}