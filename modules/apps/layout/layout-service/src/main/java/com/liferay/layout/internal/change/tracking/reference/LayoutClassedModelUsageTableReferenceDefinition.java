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

package com.liferay.layout.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.layout.model.LayoutClassedModelUsageTable;
import com.liferay.layout.service.persistence.LayoutClassedModelUsagePersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutClassedModelUsageTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumn(
			LayoutClassedModelUsageTable.INSTANCE.uuid
		).singleColumnReference(
			LayoutClassedModelUsageTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			LayoutClassedModelUsageTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			LayoutClassedModelUsageTable.INSTANCE.createDate,
			LayoutClassedModelUsageTable.INSTANCE.modifiedDate,
			LayoutClassedModelUsageTable.INSTANCE.classNameId,
			LayoutClassedModelUsageTable.INSTANCE.classPK,
			LayoutClassedModelUsageTable.INSTANCE.containerKey,
			LayoutClassedModelUsageTable.INSTANCE.containerType
		).singleColumnReference(
			LayoutClassedModelUsageTable.INSTANCE.plid,
			LayoutTable.INSTANCE.plid
		).nonreferenceColumns(
			LayoutClassedModelUsageTable.INSTANCE.type,
			LayoutClassedModelUsageTable.INSTANCE.lastPublishDate
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