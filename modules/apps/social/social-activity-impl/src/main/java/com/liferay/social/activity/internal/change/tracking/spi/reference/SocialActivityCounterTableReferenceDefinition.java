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
import com.liferay.social.kernel.model.SocialActivityCounterTable;
import com.liferay.social.kernel.service.persistence.SocialActivityCounterPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivityCounterTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivityCounterTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SocialActivityCounterTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SocialActivityCounterTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SocialActivityCounterTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				SocialActivityCounterTable.INSTANCE,
				SocialActivityCounterTable.INSTANCE.classNameId.eq(
					AssetEntryTable.INSTANCE.classNameId
				).and(
					SocialActivityCounterTable.INSTANCE.classPK.eq(
						AssetEntryTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivityCounterPersistence;
	}

	@Override
	public SocialActivityCounterTable getTable() {
		return SocialActivityCounterTable.INSTANCE;
	}

	@Reference
	private SocialActivityCounterPersistence _socialActivityCounterPersistence;

}