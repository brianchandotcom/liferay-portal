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

package com.liferay.friendly.url.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryMappingTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FriendlyURLEntryTableReferenceDefinition
	implements TableReferenceDefinition<FriendlyURLEntryTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<FriendlyURLEntryTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.nonreferenceColumns(
			FriendlyURLEntryTable.INSTANCE.uuid,
			FriendlyURLEntryTable.INSTANCE.defaultLanguageId
		).singleColumnReference(
			FriendlyURLEntryTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			FriendlyURLEntryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			FriendlyURLEntryTable.INSTANCE.createDate,
			FriendlyURLEntryTable.INSTANCE.modifiedDate
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryMappingTable.INSTANCE
			).innerJoinON(
				FriendlyURLEntryTable.INSTANCE,
				FriendlyURLEntryTable.INSTANCE.classNameId.eq(
					FriendlyURLEntryMappingTable.INSTANCE.classNameId
				).and(
					FriendlyURLEntryTable.INSTANCE.classPK.eq(
						FriendlyURLEntryMappingTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _friendlyURLEntryPersistence;
	}

	@Override
	public FriendlyURLEntryTable getTable() {
		return FriendlyURLEntryTable.INSTANCE;
	}

	@Reference
	private FriendlyURLEntryPersistence _friendlyURLEntryPersistence;

}