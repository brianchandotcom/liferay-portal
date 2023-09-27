/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.change.tracking.spi.reference;

import com.liferay.asset.list.model.AssetListEntryUsageTable;
import com.liferay.asset.list.service.persistence.AssetListEntryUsagePersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
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