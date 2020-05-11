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

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsStatsTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetEntryTableReferenceDefinition
	implements TableReferenceDefinition<AssetEntryTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<AssetEntryTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			AssetEntryTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				RatingsStatsTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					RatingsStatsTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						RatingsStatsTable.INSTANCE.classPK)
				)
			)
		).nonreferenceColumns(
			AssetEntryTable.INSTANCE.classUuid,
			AssetEntryTable.INSTANCE.classTypeId,
			AssetEntryTable.INSTANCE.listable, AssetEntryTable.INSTANCE.visible,
			AssetEntryTable.INSTANCE.startDate,
			AssetEntryTable.INSTANCE.endDate,
			AssetEntryTable.INSTANCE.publishDate,
			AssetEntryTable.INSTANCE.expirationDate,
			AssetEntryTable.INSTANCE.mimeType, AssetEntryTable.INSTANCE.title,
			AssetEntryTable.INSTANCE.description,
			AssetEntryTable.INSTANCE.summary, AssetEntryTable.INSTANCE.url
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.layoutUuid.eq(
					LayoutTable.INSTANCE.uuid
				).and(
					AssetEntryTable.INSTANCE.groupId.eq(
						LayoutTable.INSTANCE.groupId)
				)
			)
		).nonreferenceColumns(
			AssetEntryTable.INSTANCE.height, AssetEntryTable.INSTANCE.width,
			AssetEntryTable.INSTANCE.priority
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetEntryPersistence;
	}

	@Override
	public AssetEntryTable getTable() {
		return AssetEntryTable.INSTANCE;
	}

	@Reference
	private AssetEntryPersistence _assetEntryPersistence;

}