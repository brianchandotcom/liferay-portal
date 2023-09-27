/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivitySetTable;
import com.liferay.social.kernel.model.SocialActivityTable;
import com.liferay.social.kernel.service.persistence.SocialActivityPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivityTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivityTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SocialActivityTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SocialActivityTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SocialActivityTable.INSTANCE
		).singleColumnReference(
			SocialActivityTable.INSTANCE.activitySetId,
			SocialActivitySetTable.INSTANCE.activitySetId
		).parentColumnReference(
			SocialActivityTable.INSTANCE.activityId,
			SocialActivityTable.INSTANCE.mirrorActivityId
		).singleColumnReference(
			SocialActivityTable.INSTANCE.receiverUserId,
			UserTable.INSTANCE.userId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				SocialActivityTable.INSTANCE,
				SocialActivityTable.INSTANCE.classNameId.eq(
					AssetEntryTable.INSTANCE.classNameId
				).and(
					SocialActivityTable.INSTANCE.classPK.eq(
						AssetEntryTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivityPersistence;
	}

	@Override
	public SocialActivityTable getTable() {
		return SocialActivityTable.INSTANCE;
	}

	@Reference
	private SocialActivityPersistence _socialActivityPersistence;

}