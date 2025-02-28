/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetTagGroupRelTable;
import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.kernel.service.persistence.AssetTagGroupRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = TableReferenceDefinition.class)
public class AssetTagGroupRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetTagGroupRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetTagGroupRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetTagGroupRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetTagGroupRelTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			AssetTagGroupRelTable.INSTANCE.tagId, AssetTagTable.INSTANCE.tagId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetTagGroupRelPersistence;
	}

	@Override
	public AssetTagGroupRelTable getTable() {
		return AssetTagGroupRelTable.INSTANCE;
	}

	@Reference
	private AssetTagGroupRelPersistence _assetTagGroupRelPersistence;

}