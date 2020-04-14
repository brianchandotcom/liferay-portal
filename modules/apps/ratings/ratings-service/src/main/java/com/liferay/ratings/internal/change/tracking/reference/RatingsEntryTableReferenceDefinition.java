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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<RatingsEntryTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			RatingsEntryTable.INSTANCE.score);
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