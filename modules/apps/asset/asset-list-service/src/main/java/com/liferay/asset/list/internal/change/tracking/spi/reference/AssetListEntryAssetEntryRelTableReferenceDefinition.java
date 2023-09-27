/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelTable;
import com.liferay.asset.list.model.AssetListEntryTable;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntryTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetListEntryAssetEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetListEntryAssetEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetListEntryAssetEntryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetListEntryAssetEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetListEntryAssetEntryRelTable.INSTANCE
		).singleColumnReference(
			AssetListEntryAssetEntryRelTable.INSTANCE.assetListEntryId,
			AssetListEntryTable.INSTANCE.assetListEntryId
		).singleColumnReference(
			AssetListEntryAssetEntryRelTable.INSTANCE.assetEntryId,
			AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetListEntryAssetEntryRelTable.INSTANCE.segmentsEntryId,
			SegmentsEntryTable.INSTANCE.segmentsEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetListEntryAssetEntryRelPersistence;
	}

	@Override
	public AssetListEntryAssetEntryRelTable getTable() {
		return AssetListEntryAssetEntryRelTable.INSTANCE;
	}

	@Reference
	private AssetListEntryAssetEntryRelPersistence
		_assetListEntryAssetEntryRelPersistence;

}