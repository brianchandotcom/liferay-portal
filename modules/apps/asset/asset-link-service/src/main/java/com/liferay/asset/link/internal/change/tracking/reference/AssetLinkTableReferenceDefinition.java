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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<AssetLinkTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				AssetLinkTable.INSTANCE,
				AssetLinkTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				AssetLinkTable.INSTANCE,
				AssetLinkTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetLinkTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetLinkTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				AssetLinkTable.INSTANCE,
				AssetLinkTable.INSTANCE.entryId1.eq(
					AssetEntryTable.INSTANCE.entryId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				AssetLinkTable.INSTANCE,
				AssetLinkTable.INSTANCE.entryId2.eq(
					AssetEntryTable.INSTANCE.entryId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetLinkTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetLinkTable.INSTANCE.weight);
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