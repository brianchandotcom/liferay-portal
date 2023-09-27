/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalizationTable;
import com.liferay.friendly.url.model.FriendlyURLEntryMappingTable;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryPersistence;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FriendlyURLEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
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
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryLocalizationTable.INSTANCE
			).innerJoinON(
				FriendlyURLEntryTable.INSTANCE,
				FriendlyURLEntryTable.INSTANCE.classNameId.eq(
					FriendlyURLEntryLocalizationTable.INSTANCE.classNameId
				).and(
					FriendlyURLEntryTable.INSTANCE.classPK.eq(
						FriendlyURLEntryLocalizationTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FriendlyURLEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			FriendlyURLEntryTable.INSTANCE);
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