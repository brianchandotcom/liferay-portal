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

package com.liferay.asset.link.internal.change.tracking.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.model.AssetLinkTable;
import com.liferay.asset.kernel.service.persistence.AssetLinkPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetLinkTableReferenceDefinition
	implements TableReferenceDefinition<AssetLinkTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<AssetLinkTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			AssetLinkTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AssetLinkTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			AssetLinkTable.INSTANCE.userName, AssetLinkTable.INSTANCE.createDate
		).singleColumnReference(
			AssetLinkTable.INSTANCE.entryId1, AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetLinkTable.INSTANCE.entryId2, AssetEntryTable.INSTANCE.entryId
		).nonreferenceColumns(
			AssetLinkTable.INSTANCE.type, AssetLinkTable.INSTANCE.weight
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetLinkPersistence;
	}

	@Override
	public AssetLinkTable getTable() {
		return AssetLinkTable.INSTANCE;
	}

	@Reference
	private AssetLinkPersistence _assetLinkPersistence;

}