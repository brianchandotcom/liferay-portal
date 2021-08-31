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

package com.liferay.ratings.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsEntryTable;
import com.liferay.ratings.kernel.model.RatingsStatsTable;
import com.liferay.ratings.kernel.service.persistence.RatingsStatsPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class RatingsStatsTableReferenceDefinition
	implements TableReferenceDefinition<RatingsStatsTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<RatingsStatsTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				RatingsEntryTable.INSTANCE
			).innerJoinON(
				RatingsStatsTable.INSTANCE,
				RatingsStatsTable.INSTANCE.classNameId.eq(
					RatingsEntryTable.INSTANCE.classNameId
				).and(
					RatingsStatsTable.INSTANCE.classPK.eq(
						RatingsEntryTable.INSTANCE.classPK)
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<RatingsStatsTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			RatingsStatsTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ratingsStatsPersistence;
	}

	@Override
	public RatingsStatsTable getTable() {
		return RatingsStatsTable.INSTANCE;
	}

	@Reference
	private RatingsStatsPersistence _ratingsStatsPersistence;

}