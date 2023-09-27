/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypePersistence;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassNameTable;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileEntryTypeTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId,
			DDMStructureLinkTable.INSTANCE.classPK, DLFileEntryType.class
		).resourcePermissionReference(
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId, DLFileEntryType.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				DLFileEntryTypeTable.INSTANCE,
				DLFileEntryTypeTable.INSTANCE.groupId.eq(
					DDMStructureTable.INSTANCE.groupId
				).and(
					DSLFunctionFactoryUtil.lower(
						DDMStructureTable.INSTANCE.structureKey
					).eq(
						DSLFunctionFactoryUtil.lower(
							DSLFunctionFactoryUtil.concat(
								new Scalar<>(
									DLUtil.getDDMStructureKey(
										StringPool.BLANK)),
								DLFileEntryTypeTable.INSTANCE.uuid))
					)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					DDMStructureTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						DLFileEntryMetadata.class.getName())
				)
			)
		).singleColumnReference(
			DLFileEntryTypeTable.INSTANCE.dataDefinitionId,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileEntryTypeTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileEntryTypeTable.INSTANCE);
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