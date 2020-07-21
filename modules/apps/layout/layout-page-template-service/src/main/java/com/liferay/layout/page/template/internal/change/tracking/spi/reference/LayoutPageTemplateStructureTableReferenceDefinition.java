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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructurePersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutPageTemplateStructureTableReferenceDefinition
	implements TableReferenceDefinition<LayoutPageTemplateStructureTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutPageTemplateStructureTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutPageTemplateStructureTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateStructureTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutPageTemplateStructureTable.INSTANCE,
				LayoutPageTemplateStructureTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					LayoutPageTemplateStructureTable.INSTANCE.classPK.eq(
						LayoutTable.INSTANCE.plid)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				LayoutPageTemplateStructureTable.INSTANCE.classNameId.eq(
					ClassNameTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Layout.class.getName())
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPageTemplateStructurePersistence;
	}

	@Override
	public LayoutPageTemplateStructureTable getTable() {
		return LayoutPageTemplateStructureTable.INSTANCE;
	}

	@Reference
	private LayoutPageTemplateStructurePersistence
		_layoutPageTemplateStructurePersistence;

}