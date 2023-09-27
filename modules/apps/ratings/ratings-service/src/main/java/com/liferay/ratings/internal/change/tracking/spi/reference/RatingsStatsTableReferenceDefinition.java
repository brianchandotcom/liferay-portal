/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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