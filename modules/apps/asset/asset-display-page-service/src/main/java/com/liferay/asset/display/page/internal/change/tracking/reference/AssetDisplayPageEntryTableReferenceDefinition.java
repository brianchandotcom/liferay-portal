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

package com.liferay.asset.display.page.internal.change.tracking.reference;

import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<AssetDisplayPageEntryTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			AssetDisplayPageEntryTable.INSTANCE
		).nonreferenceColumn(
			AssetDisplayPageEntryTable.INSTANCE.uuid
		).singleColumnReference(
			AssetDisplayPageEntryTable.INSTANCE.layoutPageTemplateEntryId,
			LayoutPageTemplateEntryTable.INSTANCE.layoutPageTemplateEntryId
		).nonreferenceColumn(
			AssetDisplayPageEntryTable.INSTANCE.type
		).singleColumnReference(
			AssetDisplayPageEntryTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				AssetDisplayPageEntryTable.INSTANCE,
				AssetDisplayPageEntryTable.INSTANCE.classPK.eq(
					AssetEntryTable.INSTANCE.classPK
				).and(
					AssetDisplayPageEntryTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId)
				)
			)
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