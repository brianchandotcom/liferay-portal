/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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