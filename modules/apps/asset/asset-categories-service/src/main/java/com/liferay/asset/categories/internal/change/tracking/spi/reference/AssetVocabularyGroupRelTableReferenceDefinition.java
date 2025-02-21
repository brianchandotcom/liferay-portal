/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRelTable;
import com.liferay.asset.kernel.model.AssetVocabularyTable;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyGroupRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = TableReferenceDefinition.class)
public class AssetVocabularyGroupRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetVocabularyGroupRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetVocabularyGroupRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetVocabularyGroupRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetVocabularyGroupRelTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			AssetVocabularyGroupRelTable.INSTANCE.vocabularyId,
			AssetVocabularyTable.INSTANCE.vocabularyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetVocabularyGroupRelPersistence;
	}

	@Override
	public AssetVocabularyGroupRelTable getTable() {
		return AssetVocabularyGroupRelTable.INSTANCE;
	}

	@Reference
	private AssetVocabularyGroupRelPersistence
		_assetVocabularyGroupRelPersistence;

}