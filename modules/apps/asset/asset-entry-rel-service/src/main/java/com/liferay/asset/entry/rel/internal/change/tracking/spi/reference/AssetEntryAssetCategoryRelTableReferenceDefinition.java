/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.internal.change.tracking.spi.reference;

import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRelTable;
import com.liferay.asset.entry.rel.service.persistence.AssetEntryAssetCategoryRelPersistence;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetEntryAssetCategoryRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetEntryAssetCategoryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetEntryAssetCategoryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetEntryAssetCategoryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetEntryAssetCategoryRelTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AssetEntryAssetCategoryRelTable.INSTANCE.assetEntryId,
			AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetEntryAssetCategoryRelTable.INSTANCE.assetCategoryId,
			AssetCategoryTable.INSTANCE.categoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetEntryAssetCategoryRelPersistence;
	}

	@Override
	public AssetEntryAssetCategoryRelTable getTable() {
		return AssetEntryAssetCategoryRelTable.INSTANCE;
	}

	@Reference
	private AssetEntryAssetCategoryRelPersistence
		_assetEntryAssetCategoryRelPersistence;

}