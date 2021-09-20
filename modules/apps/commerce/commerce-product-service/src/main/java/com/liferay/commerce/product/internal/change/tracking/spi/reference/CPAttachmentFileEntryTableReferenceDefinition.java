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

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPAttachmentFileEntryTable;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CPAttachmentFileEntryTableReferenceDefinition
	implements TableReferenceDefinition<CPAttachmentFileEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPAttachmentFileEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CPAttachmentFileEntryTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				CPAttachmentFileEntryTable.INSTANCE,
				CPAttachmentFileEntryTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					CPAttachmentFileEntryTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					CPAttachmentFileEntryTable.INSTANCE.CPAttachmentFileEntryId.
						eq(WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						CPAttachmentFileEntry.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPAttachmentFileEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.classNameReference(
			CPAttachmentFileEntryTable.INSTANCE.classPK,
			CPDefinitionTable.INSTANCE.CPDefinitionId, CPDefinition.class
		).groupedModel(
			CPAttachmentFileEntryTable.INSTANCE
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpAttachmentFileEntryPersistence;
	}

	@Override
	public CPAttachmentFileEntryTable getTable() {
		return CPAttachmentFileEntryTable.INSTANCE;
	}

	@Reference
	private CPAttachmentFileEntryPersistence _cpAttachmentFileEntryPersistence;

}