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
import com.liferay.friendly.url.model.FriendlyURLEntryLocalizationTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FriendlyURLEntryLocalizationTableReferenceDefinition
	implements TableReferenceDefinition<FriendlyURLEntryLocalizationTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<FriendlyURLEntryLocalizationTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			FriendlyURLEntryLocalizationTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			FriendlyURLEntryLocalizationTable.INSTANCE.friendlyURLEntryId,
			FriendlyURLEntryTable.INSTANCE.friendlyURLEntryId
		).nonreferenceColumns(
			FriendlyURLEntryLocalizationTable.INSTANCE.languageId,
			FriendlyURLEntryLocalizationTable.INSTANCE.urlTitle
		).singleColumnReference(
			FriendlyURLEntryLocalizationTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				FriendlyURLEntryLocalizationTable.INSTANCE,
				FriendlyURLEntryLocalizationTable.INSTANCE.classNameId.eq(
					FriendlyURLEntryTable.INSTANCE.classNameId
				).and(
					FriendlyURLEntryLocalizationTable.INSTANCE.classPK.eq(
						FriendlyURLEntryTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _friendlyURLEntryLocalizationPersistence;
	}

	@Override
	public FriendlyURLEntryLocalizationTable getTable() {
		return FriendlyURLEntryLocalizationTable.INSTANCE;
	}

	@Reference
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

}