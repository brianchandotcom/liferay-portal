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
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.model.DDLRecordSetVersionTable;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetPersistence;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDLRecordSetTableReferenceDefinition
	implements TableReferenceDefinition<DDLRecordSetTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDLRecordSetTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			DDLRecordSetTable.INSTANCE.recordSetId,
			DDLRecordSetVersionTable.INSTANCE.recordSetId
		).classNameReference(
			DDLRecordSetTable.INSTANCE.recordSetId,
			DDMStructureLinkTable.INSTANCE.classPK, DDLRecordSet.class
		).resourcePermissionReference(
			DDLRecordSetTable.INSTANCE.recordSetId, DDLRecordSet.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				DDLRecordSetTable.INSTANCE,
				DDLRecordSetTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					DDLRecordSetTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					DDLRecordSetTable.INSTANCE.recordSetId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						DDLRecordSet.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDLRecordSetTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDLRecordSetTable.INSTANCE
		).singleColumnReference(
			DDLRecordSetTable.INSTANCE.DDMStructureId,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddlRecordSetPersistence;
	}

	@Override
	public DDLRecordSetTable getTable() {
		return DDLRecordSetTable.INSTANCE;
	}

	@Reference
	private DDLRecordSetPersistence _ddlRecordSetPersistence;

}