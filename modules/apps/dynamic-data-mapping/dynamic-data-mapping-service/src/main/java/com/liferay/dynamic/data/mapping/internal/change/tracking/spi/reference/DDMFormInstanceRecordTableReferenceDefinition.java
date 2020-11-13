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

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMStorageLinkTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMFormInstanceRecordTableReferenceDefinition
	implements TableReferenceDefinition<DDMFormInstanceRecordTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMFormInstanceRecordTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMFormInstanceRecordVersionTable.INSTANCE
			).innerJoinON(
				DDMFormInstanceRecordTable.INSTANCE,
				DDMFormInstanceRecordTable.INSTANCE.formInstanceId.eq(
					DDMFormInstanceRecordVersionTable.INSTANCE.formInstanceId
				).and(
					DDMFormInstanceRecordTable.INSTANCE.version.eq(
						DDMFormInstanceRecordVersionTable.INSTANCE.version)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMFormInstanceVersionTable.INSTANCE
			).innerJoinON(
				DDMFormInstanceRecordTable.INSTANCE,
				DDMFormInstanceRecordTable.INSTANCE.formInstanceId.eq(
					DDMFormInstanceVersionTable.INSTANCE.formInstanceId
				).and(
					DDMFormInstanceRecordTable.INSTANCE.formInstanceVersion.eq(
						DDMFormInstanceVersionTable.INSTANCE.version)
				)
			)
		).singleColumnReference(
			DDMFormInstanceRecordTable.INSTANCE.storageId,
			DDMStorageLinkTable.INSTANCE.classPK
		).assetEntryReference(
			DDMFormInstanceRecordTable.INSTANCE.formInstanceRecordId,
			DDMFormInstanceRecord.class
		).systemEventReference(
			DDMFormInstanceRecordTable.INSTANCE.formInstanceRecordId,
			DDMFormInstanceRecord.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMFormInstanceRecordTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMFormInstanceRecordTable.INSTANCE
		).singleColumnReference(
			DDMFormInstanceRecordTable.INSTANCE.formInstanceId,
			DDMFormInstanceTable.INSTANCE.formInstanceId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstanceRecordPersistence;
	}

	@Override
	public DDMFormInstanceRecordTable getTable() {
		return DDMFormInstanceRecordTable.INSTANCE;
	}

	@Reference
	private DDMFormInstanceRecordPersistence _ddmFormInstanceRecordPersistence;

}