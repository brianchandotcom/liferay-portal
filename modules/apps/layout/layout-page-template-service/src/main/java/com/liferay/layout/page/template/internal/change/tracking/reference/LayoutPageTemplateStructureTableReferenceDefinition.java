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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructurePersistence;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutPageTemplateStructureTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateStructureTable.INSTANCE
		).nonreferenceColumns(
			LayoutPageTemplateStructureTable.INSTANCE.uuid,
			LayoutPageTemplateStructureTable.INSTANCE.classNameId,
			LayoutPageTemplateStructureTable.INSTANCE.classPK
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