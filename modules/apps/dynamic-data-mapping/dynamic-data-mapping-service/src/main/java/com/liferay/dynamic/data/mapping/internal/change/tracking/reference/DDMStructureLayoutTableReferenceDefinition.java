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
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayoutTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMStructureLayoutTableReferenceDefinition
	implements TableReferenceDefinition<DDMStructureLayoutTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMStructureLayoutTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DDMStructureLayoutTable.INSTANCE
		).nonreferenceColumns(
			DDMStructureLayoutTable.INSTANCE.classNameId,
			DDMStructureLayoutTable.INSTANCE.structureLayoutKey
		).singleColumnReference(
			DDMStructureLayoutTable.INSTANCE.structureVersionId,
			DDMStructureVersionTable.INSTANCE.structureVersionId
		).nonreferenceColumns(
			DDMStructureLayoutTable.INSTANCE.name,
			DDMStructureLayoutTable.INSTANCE.description,
			DDMStructureLayoutTable.INSTANCE.definition
		).systemEventReference(
			DDMStructureLayoutTable.INSTANCE.structureLayoutId,
			DDMStructureLayout.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmStructureLayoutPersistence;
	}

	@Override
	public DDMStructureLayoutTable getTable() {
		return DDMStructureLayoutTable.INSTANCE;
	}

	@Reference
	private DDMStructureLayoutPersistence _ddmStructureLayoutPersistence;

}