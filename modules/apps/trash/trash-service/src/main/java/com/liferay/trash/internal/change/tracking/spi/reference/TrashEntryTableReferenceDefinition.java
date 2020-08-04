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

package com.liferay.trash.internal.change.tracking.spi.reference;

import com.liferay.asset.kernel.model.AssetEntryTable;
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

		parentTableReferenceInfoBuilder.groupedModel(
			TrashEntryTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				TrashEntryTable.INSTANCE,
				TrashEntryTable.INSTANCE.classNameId.eq(
					AssetEntryTable.INSTANCE.classNameId
				).and(
					TrashEntryTable.INSTANCE.classPK.eq(
						AssetEntryTable.INSTANCE.classPK)
				)
			)
		);
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