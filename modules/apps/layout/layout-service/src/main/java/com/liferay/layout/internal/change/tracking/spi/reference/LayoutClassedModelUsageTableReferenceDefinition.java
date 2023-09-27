/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.model.LayoutClassedModelUsageTable;
import com.liferay.layout.service.persistence.LayoutClassedModelUsagePersistence;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutClassedModelUsageTableReferenceDefinition
	implements TableReferenceDefinition<LayoutClassedModelUsageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutClassedModelUsageTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutClassedModelUsageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutClassedModelUsageTable.INSTANCE
		).singleColumnReference(
			LayoutClassedModelUsageTable.INSTANCE.plid,
			LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutClassedModelUsagePersistence;
	}

	@Override
	public LayoutClassedModelUsageTable getTable() {
		return LayoutClassedModelUsageTable.INSTANCE;
	}

	@Reference
	private LayoutClassedModelUsagePersistence
		_layoutClassedModelUsagePersistence;

}