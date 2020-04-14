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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.friendly.url.model.FriendlyURLEntryMappingTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryMappingPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FriendlyURLEntryMappingTableReferenceDefinition
	implements TableReferenceDefinition<FriendlyURLEntryMappingTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<FriendlyURLEntryMappingTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				FriendlyURLEntryMappingTable.INSTANCE,
				FriendlyURLEntryMappingTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			FriendlyURLEntryMappingTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			FriendlyURLEntryMappingTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				FriendlyURLEntryMappingTable.INSTANCE,
				FriendlyURLEntryMappingTable.INSTANCE.friendlyURLEntryId.eq(
					FriendlyURLEntryTable.INSTANCE.friendlyURLEntryId)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _friendlyURLEntryMappingPersistence;
	}

	@Override
	public FriendlyURLEntryMappingTable getTable() {
		return FriendlyURLEntryMappingTable.INSTANCE;
	}

	@Reference
	private FriendlyURLEntryMappingPersistence
		_friendlyURLEntryMappingPersistence;

}