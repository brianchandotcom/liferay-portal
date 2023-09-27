/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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