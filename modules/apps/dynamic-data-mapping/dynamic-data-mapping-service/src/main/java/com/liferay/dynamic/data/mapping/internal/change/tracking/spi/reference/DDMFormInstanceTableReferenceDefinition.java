/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersionTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstancePersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMFormInstanceTableReferenceDefinition
	implements TableReferenceDefinition<DDMFormInstanceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMFormInstanceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMFormInstanceVersionTable.INSTANCE
			).innerJoinON(
				DDMFormInstanceTable.INSTANCE,
				DDMFormInstanceTable.INSTANCE.formInstanceId.eq(
					DDMFormInstanceVersionTable.INSTANCE.formInstanceId
				).and(
					DDMFormInstanceTable.INSTANCE.version.eq(
						DDMFormInstanceVersionTable.INSTANCE.version)
				)
			)
		).resourcePermissionReference(
			DDMFormInstanceTable.INSTANCE.formInstanceId, DDMFormInstance.class
		).systemEventReference(
			DDMFormInstanceTable.INSTANCE.formInstanceId, DDMFormInstance.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				DDMFormInstanceTable.INSTANCE,
				DDMFormInstanceTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					DDMFormInstanceTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					DDMFormInstanceTable.INSTANCE.formInstanceId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						DDMFormInstance.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMFormInstanceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMFormInstanceTable.INSTANCE
		).singleColumnReference(
			DDMFormInstanceTable.INSTANCE.structureId,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmFormInstancePersistence;
	}

	@Override
	public DDMFormInstanceTable getTable() {
		return DDMFormInstanceTable.INSTANCE;
	}

	@Reference
	private DDMFormInstancePersistence _ddmFormInstancePersistence;

}