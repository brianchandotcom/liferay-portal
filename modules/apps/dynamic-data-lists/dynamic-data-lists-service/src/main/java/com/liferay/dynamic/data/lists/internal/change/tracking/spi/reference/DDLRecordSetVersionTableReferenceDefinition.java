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

package com.liferay.dynamic.data.lists.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.lists.model.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersionTable;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetVersionPersistence;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDLRecordSetVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDLRecordSetVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDLRecordSetVersionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDLRecordSetVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDLRecordSetVersionTable.INSTANCE
		).singleColumnReference(
			DDLRecordSetVersionTable.INSTANCE.recordSetId,
			DDLRecordSetTable.INSTANCE.recordSetId
		).singleColumnReference(
			DDLRecordSetVersionTable.INSTANCE.DDMStructureVersionId,
			DDMStructureVersionTable.INSTANCE.structureVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddlRecordSetVersionPersistence;
	}

	@Override
	public DDLRecordSetVersionTable getTable() {
		return DDLRecordSetVersionTable.INSTANCE;
	}

	@Reference
	private DDLRecordSetVersionPersistence _ddlRecordSetVersionPersistence;

}