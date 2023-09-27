/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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