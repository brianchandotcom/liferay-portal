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

import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.layout.model.LayoutClassedModelUsageTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsStatsTable;
import com.liferay.social.kernel.model.SocialActivitySetTable;
import com.liferay.subscription.model.SubscriptionTable;

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
				AssetDisplayPageEntryTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					AssetDisplayPageEntryTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						AssetDisplayPageEntryTable.INSTANCE.classPK)
				)
			)
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
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutClassedModelUsageTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.groupId.eq(
					LayoutClassedModelUsageTable.INSTANCE.groupId
				).and(
					AssetEntryTable.INSTANCE.classNameId.eq(
						LayoutClassedModelUsageTable.INSTANCE.classNameId)
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						LayoutClassedModelUsageTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					LayoutClassedModelUsageTable.INSTANCE.containerType
				).and(
					ClassNameTable.INSTANCE.value.eq(Portlet.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				SocialActivitySetTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					SocialActivitySetTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						SocialActivitySetTable.INSTANCE.classPK)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				SubscriptionTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.companyId.eq(
					SubscriptionTable.INSTANCE.companyId
				).and(
					AssetEntryTable.INSTANCE.classNameId.eq(
						SubscriptionTable.INSTANCE.classNameId)
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						SubscriptionTable.INSTANCE.classPK)
				)
			)
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