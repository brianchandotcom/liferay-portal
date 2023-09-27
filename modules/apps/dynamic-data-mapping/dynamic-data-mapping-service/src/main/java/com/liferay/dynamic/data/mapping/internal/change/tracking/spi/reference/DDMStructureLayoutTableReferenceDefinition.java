/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.data.engine.model.DEDataDefinitionFieldLinkTable;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMStructureLayoutTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			DDMStructureLayoutTable.INSTANCE.structureLayoutId,
			DEDataDefinitionFieldLinkTable.INSTANCE.classPK,
			DDMStructureLayout.class
		).systemEventReference(
			DDMStructureLayoutTable.INSTANCE.structureLayoutId,
			DDMStructureLayout.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMStructureLayoutTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMStructureLayoutTable.INSTANCE
		).singleColumnReference(
			DDMStructureLayoutTable.INSTANCE.structureVersionId,
			DDMStructureVersionTable.INSTANCE.structureVersionId
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