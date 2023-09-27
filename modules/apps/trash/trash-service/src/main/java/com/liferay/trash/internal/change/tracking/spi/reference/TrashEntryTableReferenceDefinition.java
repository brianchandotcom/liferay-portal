/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.SystemEventTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.trash.model.TrashEntryTable;
import com.liferay.trash.model.TrashVersionTable;
import com.liferay.trash.service.persistence.TrashEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class TrashEntryTableReferenceDefinition
	implements TableReferenceDefinition<TrashEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<TrashEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			TrashEntryTable.INSTANCE.entryId, TrashVersionTable.INSTANCE.entryId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				SystemEventTable.INSTANCE
			).innerJoinON(
				TrashEntryTable.INSTANCE,
				TrashEntryTable.INSTANCE.groupId.eq(
					SystemEventTable.INSTANCE.groupId
				).and(
					TrashEntryTable.INSTANCE.systemEventSetKey.eq(
						SystemEventTable.INSTANCE.systemEventSetKey)
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<TrashEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(TrashEntryTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _trashEntryPersistence;
	}

	@Override
	public TrashEntryTable getTable() {
		return TrashEntryTable.INSTANCE;
	}

	@Reference
	private TrashEntryPersistence _trashEntryPersistence;

}