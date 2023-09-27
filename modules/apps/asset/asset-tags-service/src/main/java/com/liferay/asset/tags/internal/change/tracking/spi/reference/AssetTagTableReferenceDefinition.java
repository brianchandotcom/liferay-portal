/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
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
public class AssetTagTableReferenceDefinition
	implements TableReferenceDefinition<AssetTagTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetTagTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.systemEventReference(
			AssetTagTable.INSTANCE.tagId, AssetTag.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetTagTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(AssetTagTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetTagPersistence;
	}

	@Override
	public AssetTagTable getTable() {
		return AssetTagTable.INSTANCE;
	}

	@Reference
	private AssetTagPersistence _assetTagPersistence;

}