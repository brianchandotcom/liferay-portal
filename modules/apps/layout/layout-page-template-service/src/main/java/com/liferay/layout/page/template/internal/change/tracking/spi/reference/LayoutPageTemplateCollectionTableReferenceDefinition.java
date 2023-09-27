/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollectionTable;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutPageTemplateCollectionTableReferenceDefinition
	implements TableReferenceDefinition<LayoutPageTemplateCollectionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutPageTemplateCollectionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			LayoutPageTemplateCollectionTable.INSTANCE.
				layoutPageTemplateCollectionId,
			LayoutPageTemplateCollection.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutPageTemplateCollectionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutPageTemplateCollectionTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPageTemplateCollectionPersistence;
	}

	@Override
	public LayoutPageTemplateCollectionTable getTable() {
		return LayoutPageTemplateCollectionTable.INSTANCE;
	}

	@Reference
	private LayoutPageTemplateCollectionPersistence
		_layoutPageTemplateCollectionPersistence;

}