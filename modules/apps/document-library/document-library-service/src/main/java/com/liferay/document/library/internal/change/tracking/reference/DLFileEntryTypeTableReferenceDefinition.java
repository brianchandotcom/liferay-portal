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

package com.liferay.document.library.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypePersistence;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileEntryTypeTableReferenceDefinition
	implements TableReferenceDefinition<DLFileEntryTypeTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DLFileEntryTypeTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DLFileEntryTypeTable.INSTANCE
		).nonreferenceColumns(
			DLFileEntryTypeTable.INSTANCE.createDate,
			DLFileEntryTypeTable.INSTANCE.description,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeKey,
			DLFileEntryTypeTable.INSTANCE.lastPublishDate,
			DLFileEntryTypeTable.INSTANCE.modifiedDate,
			DLFileEntryTypeTable.INSTANCE.name,
			DLFileEntryTypeTable.INSTANCE.userName,
			DLFileEntryTypeTable.INSTANCE.uuid
		).singleColumnReference(
			DLFileEntryTypeTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DLFileEntryTypeTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureLinkTable.INSTANCE
			).innerJoinON(
				DLFileEntryTypeTable.INSTANCE,
				DLFileEntryTypeTable.INSTANCE.fileEntryTypeId.eq(
					DDMStructureLinkTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DLFileEntryType.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMStructureLinkTable.INSTANCE.classNameId)
				)
			)
		).resourcePermissionReference(
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileEntryTypePersistence;
	}

	@Override
	public DLFileEntryTypeTable getTable() {
		return DLFileEntryTypeTable.INSTANCE;
	}

	@Reference
	private DLFileEntryTypePersistence _dlFileEntryTypePersistence;

}