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
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.model.DDLRecordTable;
import com.liferay.dynamic.data.lists.model.DDLRecordVersionTable;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordVersionPersistence;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDLRecordVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDLRecordVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDLRecordVersionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			DDLRecordVersionTable.INSTANCE.DDMStorageId,
			DDMStructureLinkTable.INSTANCE.classPK
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				DDLRecordVersionTable.INSTANCE,
				DDLRecordVersionTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					DDLRecordVersionTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					DDLRecordVersionTable.INSTANCE.recordVersionId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						DDLFormRecord.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				DDLRecordVersionTable.INSTANCE,
				DDLRecordVersionTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					DDLRecordVersionTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					DDLRecordVersionTable.INSTANCE.recordVersionId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(DDLRecord.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDLRecordVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDLRecordVersionTable.INSTANCE
		).singleColumnReference(
			DDLRecordVersionTable.INSTANCE.recordSetId,
			DDLRecordSetTable.INSTANCE.recordSetId
		).singleColumnReference(
			DDLRecordVersionTable.INSTANCE.recordId,
			DDLRecordTable.INSTANCE.recordId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddlRecordVersionPersistence;
	}

	@Override
	public DDLRecordVersionTable getTable() {
		return DDLRecordVersionTable.INSTANCE;
	}

	@Reference
	private DDLRecordVersionPersistence _ddlRecordVersionPersistence;

}