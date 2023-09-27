/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateStructurePersistence;
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
					LayoutPageTemplateStructureTable.INSTANCE.plid.eq(
						LayoutTable.INSTANCE.plid)
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