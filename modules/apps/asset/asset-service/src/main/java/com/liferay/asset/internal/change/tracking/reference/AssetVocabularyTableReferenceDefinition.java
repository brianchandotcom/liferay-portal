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
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
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
		TableReferenceInfoBuilder<AssetVocabularyTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			AssetVocabularyTable.INSTANCE
		).nonreferenceColumns(
			AssetVocabularyTable.INSTANCE.uuid,
			AssetVocabularyTable.INSTANCE.externalReferenceCode,
			AssetVocabularyTable.INSTANCE.name,
			AssetVocabularyTable.INSTANCE.title,
			AssetVocabularyTable.INSTANCE.description,
			AssetVocabularyTable.INSTANCE.settings,
			AssetVocabularyTable.INSTANCE.lastPublishDate
		);
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