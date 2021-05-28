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

package com.liferay.asset.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyPersistence;
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
public class AssetVocabularyTableReferenceDefinition
	implements TableReferenceDefinition<AssetVocabularyTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetVocabularyTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			AssetVocabularyTable.INSTANCE.vocabularyId, AssetVocabulary.class
		).systemEventReference(
			AssetVocabularyTable.INSTANCE.vocabularyId, AssetVocabulary.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetVocabularyTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetVocabularyTable.INSTANCE);
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