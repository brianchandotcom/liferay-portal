/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetCategoryTableReferenceDefinition
	implements TableReferenceDefinition<AssetCategoryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetCategoryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			AssetCategoryTable.INSTANCE.categoryId, AssetCategory.class
		).systemEventReference(
			AssetCategoryTable.INSTANCE.categoryId, AssetCategory.class
		).classNameReference(
			AssetCategoryTable.INSTANCE.categoryId,
			FriendlyURLEntryTable.INSTANCE.classPK, AssetCategory.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetCategoryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetCategoryTable.INSTANCE
		).parentColumnReference(
			AssetCategoryTable.INSTANCE.categoryId,
			AssetCategoryTable.INSTANCE.parentCategoryId
		).singleColumnReference(
			AssetCategoryTable.INSTANCE.vocabularyId,
			AssetVocabularyTable.INSTANCE.vocabularyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetCategoryPersistence;
	}

	@Override
	public AssetCategoryTable getTable() {
		return AssetCategoryTable.INSTANCE;
	}

	@Reference
	private AssetCategoryPersistence _assetCategoryPersistence;

}