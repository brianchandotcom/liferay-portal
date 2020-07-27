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

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntryRelTable;
import com.liferay.segments.model.SegmentsEntryTable;
import com.liferay.segments.service.persistence.SegmentsEntryRelPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsEntryRelTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsEntryRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsEntryRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsEntryRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SegmentsEntryRelTable.INSTANCE
		).singleColumnReference(
			SegmentsEntryRelTable.INSTANCE.segmentsEntryId,
			SegmentsEntryTable.INSTANCE.segmentsEntryId
		).classNameReference(
			SegmentsEntryRelTable.INSTANCE.classPK, UserTable.INSTANCE.userId,
			User.class
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsEntryRelPersistence;
	}

	@Override
	public SegmentsEntryRelTable getTable() {
		return SegmentsEntryRelTable.INSTANCE;
	}

	@Reference
	private SegmentsEntryRelPersistence _segmentsEntryRelPersistence;

}