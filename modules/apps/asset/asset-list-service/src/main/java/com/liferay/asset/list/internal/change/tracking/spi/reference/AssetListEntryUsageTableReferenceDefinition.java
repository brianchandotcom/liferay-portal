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

package com.liferay.asset.list.internal.change.tracking.spi.reference;

import com.liferay.asset.list.model.AssetListEntryTable;
import com.liferay.asset.list.model.AssetListEntryUsageTable;
import com.liferay.asset.list.service.persistence.AssetListEntryUsagePersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetListEntryUsageTableReferenceDefinition
	implements TableReferenceDefinition<AssetListEntryUsageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetListEntryUsageTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetListEntryUsageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetListEntryUsageTable.INSTANCE
		).singleColumnReference(
			AssetListEntryUsageTable.INSTANCE.assetListEntryId,
			AssetListEntryTable.INSTANCE.assetListEntryId
		).classNameReference(
			AssetListEntryUsageTable.INSTANCE.classPK,
			LayoutTable.INSTANCE.plid, Layout.class
		).singleColumnReference(
			AssetListEntryUsageTable.INSTANCE.portletId,
			PortletPreferencesTable.INSTANCE.portletId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetListEntryUsagePersistence;
	}

	@Override
	public AssetListEntryUsageTable getTable() {
		return AssetListEntryUsageTable.INSTANCE;
	}

	@Reference
	private AssetListEntryUsagePersistence _assetListEntryUsagePersistence;

}