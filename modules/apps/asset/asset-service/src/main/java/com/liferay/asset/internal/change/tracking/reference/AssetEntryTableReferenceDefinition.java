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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.UserTable;
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
		TableReferenceDefinitionHelper<AssetEntryTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.groupId.eq(GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.classUuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.classTypeId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.listable);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.visible);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.startDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.endDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.publishDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.expirationDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.mimeType);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.title);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.summary);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.url);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.height);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.width);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AssetEntryTable.INSTANCE.priority);
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