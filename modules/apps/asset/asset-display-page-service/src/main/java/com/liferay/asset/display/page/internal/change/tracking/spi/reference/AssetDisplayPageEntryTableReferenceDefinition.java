/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.change.tracking.spi.reference;

import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetDisplayPageEntryTableReferenceDefinition
	implements TableReferenceDefinition<AssetDisplayPageEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetDisplayPageEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetDisplayPageEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetDisplayPageEntryTable.INSTANCE
		).singleColumnReference(
			AssetDisplayPageEntryTable.INSTANCE.layoutPageTemplateEntryId,
			LayoutPageTemplateEntryTable.INSTANCE.layoutPageTemplateEntryId
		).singleColumnReference(
			AssetDisplayPageEntryTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _assetDisplayPageEntryPersistence;
	}

	@Override
	public AssetDisplayPageEntryTable getTable() {
		return AssetDisplayPageEntryTable.INSTANCE;
	}

	@Reference
	private AssetDisplayPageEntryPersistence _assetDisplayPageEntryPersistence;

}