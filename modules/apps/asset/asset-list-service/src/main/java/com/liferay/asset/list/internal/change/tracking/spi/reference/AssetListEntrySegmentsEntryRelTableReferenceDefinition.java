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

package com.liferay.asset.list.internal.change.tracking.spi.reference;

import com.liferay.asset.list.model.AssetListEntryAssetEntryRelTable;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRelTable;
import com.liferay.asset.list.model.AssetListEntryTable;
import com.liferay.asset.list.service.persistence.AssetListEntrySegmentsEntryRelPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntryTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetListEntrySegmentsEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<AssetListEntrySegmentsEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetListEntrySegmentsEntryRelTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetListEntryAssetEntryRelTable.INSTANCE
			).innerJoinON(
				AssetListEntrySegmentsEntryRelTable.INSTANCE,
				AssetListEntrySegmentsEntryRelTable.INSTANCE.assetListEntryId.
					eq(
						AssetListEntryAssetEntryRelTable.INSTANCE.assetEntryId
					).and(
						AssetListEntrySegmentsEntryRelTable.INSTANCE.
							segmentsEntryId.eq(
								AssetListEntryAssetEntryRelTable.INSTANCE.
									segmentsEntryId)
					)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetListEntrySegmentsEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetListEntrySegmentsEntryRelTable.INSTANCE
		).singleColumnReference(
			AssetListEntrySegmentsEntryRelTable.INSTANCE.assetListEntryId,
			AssetListEntryTable.INSTANCE.assetListEntryId
		).singleColumnReference(
			AssetListEntrySegmentsEntryRelTable.INSTANCE.segmentsEntryId,
			SegmentsEntryTable.INSTANCE.segmentsEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetListEntrySegmentsEntryRelPersistence;
	}

	@Override
	public AssetListEntrySegmentsEntryRelTable getTable() {
		return AssetListEntrySegmentsEntryRelTable.INSTANCE;
	}

	@Reference
	private AssetListEntrySegmentsEntryRelPersistence
		_assetListEntrySegmentsEntryRelPersistence;

}