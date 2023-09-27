/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.internal.change.tracking.spi.reference;

import com.liferay.asset.category.property.model.AssetCategoryPropertyTable;
import com.liferay.asset.category.property.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.asset.kernel.model.AssetCategoryTable;
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
public class AssetCategoryPropertyTableReferenceDefinition
	implements TableReferenceDefinition<AssetCategoryPropertyTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetCategoryPropertyTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetCategoryPropertyTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetCategoryPropertyTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AssetCategoryPropertyTable.INSTANCE.categoryId,
			AssetCategoryTable.INSTANCE.categoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetCategoryPropertyPersistence;
	}

	@Override
	public AssetCategoryPropertyTable getTable() {
		return AssetCategoryPropertyTable.INSTANCE;
	}

	@Reference
	private AssetCategoryPropertyPersistence _assetCategoryPropertyPersistence;

}