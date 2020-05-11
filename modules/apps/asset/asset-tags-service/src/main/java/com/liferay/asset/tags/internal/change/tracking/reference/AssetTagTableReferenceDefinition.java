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

package com.liferay.asset.tags.internal.change.tracking.reference;

import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<AssetTagTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			AssetTagTable.INSTANCE
		).nonreferenceColumns(
			AssetTagTable.INSTANCE.uuid, AssetTagTable.INSTANCE.name,
			AssetTagTable.INSTANCE.assetCount,
			AssetTagTable.INSTANCE.lastPublishDate
		);
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