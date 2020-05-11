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

package com.liferay.layout.page.template.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollectionTable;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.LayoutPrototypeTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutPageTemplateEntryTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateEntryTable.INSTANCE
		).nonreferenceColumn(
			LayoutPageTemplateEntryTable.INSTANCE.uuid
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.
				layoutPageTemplateCollectionId,
			LayoutPageTemplateCollectionTable.INSTANCE.
				layoutPageTemplateCollectionId
		).nonreferenceColumns(
			LayoutPageTemplateEntryTable.INSTANCE.layoutPageTemplateEntryKey,
			LayoutPageTemplateEntryTable.INSTANCE.classNameId,
			LayoutPageTemplateEntryTable.INSTANCE.classTypeId,
			LayoutPageTemplateEntryTable.INSTANCE.name,
			LayoutPageTemplateEntryTable.INSTANCE.type
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).nonreferenceColumn(
			LayoutPageTemplateEntryTable.INSTANCE.defaultTemplate
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.layoutPrototypeId,
			LayoutPrototypeTable.INSTANCE.layoutPrototypeId
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.plid,
			LayoutTable.INSTANCE.plid
		).nonreferenceColumns(
			LayoutPageTemplateEntryTable.INSTANCE.lastPublishDate,
			LayoutPageTemplateEntryTable.INSTANCE.status
		).singleColumnReference(
			LayoutPageTemplateEntryTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			LayoutPageTemplateEntryTable.INSTANCE.statusByUserName,
			LayoutPageTemplateEntryTable.INSTANCE.statusDate
		).referenceInnerJoin(
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