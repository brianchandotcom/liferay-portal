/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutPageTemplateStructureRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutPageTemplateStructureRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateStructureRelTable.INSTANCE
		).singleColumnReference(
			LayoutPageTemplateStructureRelTable.INSTANCE.
				layoutPageTemplateStructureId,
			LayoutPageTemplateStructureTable.INSTANCE.
				layoutPageTemplateStructureId
		).singleColumnReference(
			LayoutPageTemplateStructureRelTable.INSTANCE.segmentsExperienceId,
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId
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