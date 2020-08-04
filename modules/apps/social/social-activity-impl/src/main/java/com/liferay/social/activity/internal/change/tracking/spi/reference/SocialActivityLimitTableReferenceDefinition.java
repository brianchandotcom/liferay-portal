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

package com.liferay.social.activity.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivityLimitTable;
import com.liferay.social.kernel.service.persistence.SocialActivityLimitPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivityLimitTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivityLimitTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SocialActivityLimitTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SocialActivityLimitTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SocialActivityLimitTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				SocialActivityLimitTable.INSTANCE,
				SocialActivityLimitTable.INSTANCE.classNameId.eq(
					AssetEntryTable.INSTANCE.classNameId
				).and(
					SocialActivityLimitTable.INSTANCE.classPK.eq(
						AssetEntryTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivityLimitPersistence;
	}

	@Override
	public SocialActivityLimitTable getTable() {
		return SocialActivityLimitTable.INSTANCE;
	}

	@Reference
	private SocialActivityLimitPersistence _socialActivityLimitPersistence;

}