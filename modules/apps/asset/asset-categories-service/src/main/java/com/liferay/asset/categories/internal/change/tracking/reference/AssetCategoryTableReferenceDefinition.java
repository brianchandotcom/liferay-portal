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

import com.liferay.asset.kernel.model.AssetCategoryTable;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
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
		TableReferenceDefinitionHelper<AssetCategoryTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.externalReferenceCode);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				AssetCategoryTable.INSTANCE,
				AssetCategoryTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				AssetCategoryTable.INSTANCE,
				AssetCategoryTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				AssetCategoryTable.INSTANCE,
				AssetCategoryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				AssetCategoryTable aliasAssetCategoryTable =
					AssetCategoryTable.INSTANCE.as("aliasAssetCategoryTable");

				return fromStep.from(
					aliasAssetCategoryTable
				).innerJoinON(
					AssetCategoryTable.INSTANCE,
					AssetCategoryTable.INSTANCE.parentCategoryId.eq(
						aliasAssetCategoryTable.categoryId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.treePath);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.title);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AssetVocabularyTable.INSTANCE
			).innerJoinON(
				AssetCategoryTable.INSTANCE,
				AssetCategoryTable.INSTANCE.vocabularyId.eq(
					AssetVocabularyTable.INSTANCE.vocabularyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetCategoryTable.INSTANCE.lastPublishDate);
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