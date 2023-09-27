/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.internal.change.tracking.spi.reference;

import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.model.LayoutClassedModelUsageTable;
import com.liferay.message.boards.model.MBDiscussionTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsStatsTable;
import com.liferay.social.kernel.model.SocialActivitySetTable;
import com.liferay.subscription.model.SubscriptionTable;
import com.liferay.trash.model.TrashEntryTable;
import com.liferay.trash.model.TrashVersionTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AssetEntryTableReferenceDefinition
	implements TableReferenceDefinition<AssetEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AssetEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
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
				MBDiscussionTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					MBDiscussionTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						MBDiscussionTable.INSTANCE.classPK)
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
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				TrashEntryTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					TrashEntryTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						TrashEntryTable.INSTANCE.classPK)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				TrashVersionTable.INSTANCE
			).innerJoinON(
				AssetEntryTable.INSTANCE,
				AssetEntryTable.INSTANCE.classNameId.eq(
					TrashVersionTable.INSTANCE.classNameId
				).and(
					AssetEntryTable.INSTANCE.classPK.eq(
						TrashVersionTable.INSTANCE.classPK)
				)
			)
		).systemEventReference(
			AssetEntryTable.INSTANCE.entryId, AssetEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AssetEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			AssetEntryTable.INSTANCE
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