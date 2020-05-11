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

package com.liferay.ratings.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<RatingsEntryTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumn(
			RatingsEntryTable.INSTANCE.uuid
		).singleColumnReference(
			RatingsEntryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			RatingsEntryTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			RatingsEntryTable.INSTANCE.userName,
			RatingsEntryTable.INSTANCE.createDate,
			RatingsEntryTable.INSTANCE.modifiedDate,
			RatingsEntryTable.INSTANCE.classNameId,
			RatingsEntryTable.INSTANCE.classPK, RatingsEntryTable.INSTANCE.score
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