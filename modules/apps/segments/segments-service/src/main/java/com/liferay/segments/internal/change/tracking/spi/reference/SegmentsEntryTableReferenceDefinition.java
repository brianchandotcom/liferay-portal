/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsEntryTable;
import com.liferay.segments.model.SegmentsExperienceTable;
import com.liferay.segments.service.persistence.SegmentsEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsEntryTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			SegmentsEntryTable.INSTANCE.segmentsEntryId,
			SegmentsExperienceTable.INSTANCE.segmentsEntryId
		).resourcePermissionReference(
			SegmentsEntryTable.INSTANCE.segmentsEntryId, SegmentsEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SegmentsEntryTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsEntryPersistence;
	}

	@Override
	public SegmentsEntryTable getTable() {
		return SegmentsEntryTable.INSTANCE;
	}

	@Reference
	private SegmentsEntryPersistence _segmentsEntryPersistence;

}