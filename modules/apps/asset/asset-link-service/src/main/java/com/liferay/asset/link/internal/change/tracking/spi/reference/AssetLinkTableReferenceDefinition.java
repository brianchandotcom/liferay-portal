/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.link.model.AssetLinkTable;
import com.liferay.asset.link.service.persistence.AssetLinkPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetLinkTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AssetLinkTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AssetLinkTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			AssetLinkTable.INSTANCE.entryId1, AssetEntryTable.INSTANCE.entryId
		).singleColumnReference(
			AssetLinkTable.INSTANCE.entryId2, AssetEntryTable.INSTANCE.entryId
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