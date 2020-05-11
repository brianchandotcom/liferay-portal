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

package com.liferay.asset.categories.internal.change.tracking.reference;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<AssetCategoryTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			AssetCategoryTable.INSTANCE
		).nonreferenceColumns(
			AssetCategoryTable.INSTANCE.uuid,
			AssetCategoryTable.INSTANCE.externalReferenceCode
		).parentColumnReference(
			AssetCategoryTable.INSTANCE.categoryId,
			AssetCategoryTable.INSTANCE.parentCategoryId
		).nonreferenceColumns(
			AssetCategoryTable.INSTANCE.treePath,
			AssetCategoryTable.INSTANCE.name, AssetCategoryTable.INSTANCE.title,
			AssetCategoryTable.INSTANCE.description
		).singleColumnReference(
			AssetCategoryTable.INSTANCE.vocabularyId,
			AssetVocabularyTable.INSTANCE.vocabularyId
		).nonreferenceColumn(
			AssetCategoryTable.INSTANCE.lastPublishDate
		).resourcePermissionReference(
			AssetCategoryTable.INSTANCE.categoryId, AssetCategory.class
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