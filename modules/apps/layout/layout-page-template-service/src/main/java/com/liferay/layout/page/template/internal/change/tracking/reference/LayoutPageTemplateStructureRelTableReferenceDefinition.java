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
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelTable;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructureRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsExperienceTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutPageTemplateStructureRelTableReferenceDefinition
	implements TableReferenceDefinition<LayoutPageTemplateStructureRelTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutPageTemplateStructureRelTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateStructureRelTable.INSTANCE
		).nonreferenceColumn(
			LayoutPageTemplateStructureRelTable.INSTANCE.uuid
		).singleColumnReference(
			LayoutPageTemplateStructureRelTable.INSTANCE.
				layoutPageTemplateStructureId,
			LayoutPageTemplateStructureTable.INSTANCE.
				layoutPageTemplateStructureId
		).singleColumnReference(
			LayoutPageTemplateStructureRelTable.INSTANCE.segmentsExperienceId,
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId
		).nonreferenceColumn(
			LayoutPageTemplateStructureRelTable.INSTANCE.data
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPageTemplateStructureRelPersistence;
	}

	@Override
	public LayoutPageTemplateStructureRelTable getTable() {
		return LayoutPageTemplateStructureRelTable.INSTANCE;
	}

	@Reference
	private LayoutPageTemplateStructureRelPersistence
		_layoutPageTemplateStructureRelPersistence;

}