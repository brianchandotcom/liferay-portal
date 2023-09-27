/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordVersionPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMFormInstanceRecordVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDMFormInstanceRecordVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMFormInstanceRecordVersionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMFormInstanceRecordVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMFormInstanceRecordVersionTable.INSTANCE
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.formInstanceId,
			DDMFormInstanceTable.INSTANCE.formInstanceId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMFormInstanceVersionTable.INSTANCE
			).innerJoinON(
				DDMFormInstanceRecordVersionTable.INSTANCE,
				DDMFormInstanceRecordVersionTable.INSTANCE.formInstanceId.eq(
					DDMFormInstanceVersionTable.INSTANCE.formInstanceId
				).and(
					DDMFormInstanceRecordVersionTable.INSTANCE.
						formInstanceVersion.eq(
							DDMFormInstanceVersionTable.INSTANCE.version)
				)
			)
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.formInstanceRecordId,
			DDMFormInstanceRecordTable.INSTANCE.formInstanceRecordId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstanceRecordVersionPersistence;
	}

	@Override
	public DDMFormInstanceRecordVersionTable getTable() {
		return DDMFormInstanceRecordVersionTable.INSTANCE;
	}

	@Reference
	private DDMFormInstanceRecordVersionPersistence
		_ddmFormInstanceRecordVersionPersistence;

}