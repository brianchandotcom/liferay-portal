/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsEntryTable;
import com.liferay.ratings.kernel.service.persistence.RatingsEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class RatingsEntryTableReferenceDefinition
	implements TableReferenceDefinition<RatingsEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<RatingsEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.systemEventReference(
			RatingsEntryTable.INSTANCE.entryId, RatingsEntry.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<RatingsEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			RatingsEntryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			RatingsEntryTable.INSTANCE.userId, UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ratingsEntryPersistence;
	}

	@Override
	public RatingsEntryTable getTable() {
		return RatingsEntryTable.INSTANCE;
	}

	@Reference
	private RatingsEntryPersistence _ratingsEntryPersistence;

}