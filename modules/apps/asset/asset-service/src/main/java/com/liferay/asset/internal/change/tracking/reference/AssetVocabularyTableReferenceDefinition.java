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

package com.liferay.asset.internal.change.tracking.reference;

import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyPersistence;
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
public class AssetVocabularyTableReferenceDefinition
	implements TableReferenceDefinition<AssetVocabularyTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<AssetVocabularyTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.externalReferenceCode);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				AssetVocabularyTable.INSTANCE,
				AssetVocabularyTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				AssetVocabularyTable.INSTANCE,
				AssetVocabularyTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				AssetVocabularyTable.INSTANCE,
				AssetVocabularyTable.INSTANCE.userId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.title);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.settings);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetVocabularyTable.INSTANCE.lastPublishDate);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetVocabularyPersistence;
	}

	@Override
	public AssetVocabularyTable getTable() {
		return AssetVocabularyTable.INSTANCE;
	}

	@Reference
	private AssetVocabularyPersistence _assetVocabularyPersistence;

}