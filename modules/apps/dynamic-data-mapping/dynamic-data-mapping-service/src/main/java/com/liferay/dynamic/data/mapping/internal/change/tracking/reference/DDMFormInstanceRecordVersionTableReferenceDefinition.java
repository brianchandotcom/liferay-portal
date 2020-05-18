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
import com.liferay.dynamic.data.mapping.model.DDMContentTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceRecordVersionPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMFormInstanceRecordVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.userId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMFormInstanceRecordVersionTable.INSTANCE.userName,
			DDMFormInstanceRecordVersionTable.INSTANCE.createDate
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
		).nonreferenceColumn(
			DDMFormInstanceRecordVersionTable.INSTANCE.version
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.storageId,
			DDMContentTable.INSTANCE.contentId
		).nonreferenceColumn(
			DDMFormInstanceRecordVersionTable.INSTANCE.status
		).singleColumnReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMFormInstanceRecordVersionTable.INSTANCE.statusByUserName,
			DDMFormInstanceRecordVersionTable.INSTANCE.statusDate
		).assetEntryReference(
			DDMFormInstanceRecordVersionTable.INSTANCE.
				formInstanceRecordVersionId,
			DDMFormInstanceRecord.class
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