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

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetTable;
import com.liferay.dynamic.data.lists.model.DDLRecordTable;
import com.liferay.dynamic.data.lists.model.DDLRecordVersionTable;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDLRecordTableReferenceDefinition
	implements TableReferenceDefinition<DDLRecordTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDLRecordTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			DDLRecordTable.INSTANCE.recordId, DDLRecord.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				DDLRecordTable.INSTANCE,
				DDLRecordTable.INSTANCE.recordId.eq(
					AssetEntryTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DDLFormRecord.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId)
				)
			)
		).singleColumnReference(
			DDLRecordTable.INSTANCE.recordId,
			DDLRecordVersionTable.INSTANCE.recordId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDLRecordTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDLRecordTable.INSTANCE
		).singleColumnReference(
			DDLRecordTable.INSTANCE.recordSetId,
			DDLRecordSetTable.INSTANCE.recordSetId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddlRecordPersistence;
	}

	@Override
	public DDLRecordTable getTable() {
		return DDLRecordTable.INSTANCE;
	}

	@Reference
	private DDLRecordPersistence _ddlRecordPersistence;

}