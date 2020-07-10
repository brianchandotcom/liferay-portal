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

package com.liferay.layout.page.template.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollectionTable;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.LayoutPrototypeTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutPageTemplateEntryTableReferenceDefinition
	implements TableReferenceDefinition<LayoutPageTemplateEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutPageTemplateEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureLinkTable.INSTANCE
			).innerJoinON(
				LayoutPageTemplateEntryTable.INSTANCE,
				LayoutPageTemplateEntryTable.INSTANCE.layoutPageTemplateEntryId.
					eq(
						DDMStructureLinkTable.INSTANCE.classPK
					).and(
						LayoutPageTemplateEntryTable.INSTANCE.classTypeId.eq(
							DDMStructureLinkTable.INSTANCE.structureId)
					)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					DDMStructureLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						LayoutPageTemplateEntry.class.getName())
				)
			)
		).resourcePermissionReference(
			LayoutPageTemplateEntryTable.INSTANCE.
				layoutPageTemplateCollectionId,
			LayoutPageTemplateEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutPageTemplateEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateEntryTable.INSTANCE
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.
				layoutPageTemplateCollectionId,
			LayoutPageTemplateCollectionTable.INSTANCE.
				layoutPageTemplateCollectionId
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.layoutPrototypeId,
			LayoutPrototypeTable.INSTANCE.layoutPrototypeId
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.plid,
			LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPageTemplateEntryPersistence;
	}

	@Override
	public LayoutPageTemplateEntryTable getTable() {
		return LayoutPageTemplateEntryTable.INSTANCE;
	}

	@Reference
	private LayoutPageTemplateEntryPersistence
		_layoutPageTemplateEntryPersistence;

}