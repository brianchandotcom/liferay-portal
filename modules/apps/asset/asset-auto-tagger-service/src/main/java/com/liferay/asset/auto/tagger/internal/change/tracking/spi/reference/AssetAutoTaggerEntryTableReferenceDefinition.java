/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.change.tracking.spi.reference;

import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntryTable;
import com.liferay.asset.auto.tagger.service.persistence.AssetAutoTaggerEntryPersistence;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.model.AssetTagTable;
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
public class AssetAutoTaggerEntryTableReferenceDefinition
	implements TableReferenceDefinition<AssetAutoTaggerEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetAutoTaggerEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetAutoTaggerEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetAutoTaggerEntryTable.INSTANCE
		).singleColumnReference(
			AssetAutoTaggerEntryTable.INSTANCE.assetEntryId,
			AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetAutoTaggerEntryTable.INSTANCE.assetTagId,
			AssetTagTable.INSTANCE.tagId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetAutoTaggerEntryPersistence;
	}

	@Override
	public AssetAutoTaggerEntryTable getTable() {
		return AssetAutoTaggerEntryTable.INSTANCE;
	}

	@Reference
	private AssetAutoTaggerEntryPersistence _assetAutoTaggerEntryPersistence;

}