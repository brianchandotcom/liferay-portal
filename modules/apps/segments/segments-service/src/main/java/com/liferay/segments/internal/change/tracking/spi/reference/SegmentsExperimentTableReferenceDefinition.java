/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsEntryTable;
import com.liferay.segments.model.SegmentsExperienceTable;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentTable;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsExperimentTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsExperimentTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsExperimentTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			SegmentsExperimentTable.INSTANCE.segmentsExperimentId,
			SegmentsExperiment.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsExperimentTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SegmentsExperimentTable.INSTANCE
		).singleColumnReference(
			SegmentsExperimentTable.INSTANCE.segmentsEntryId,
			SegmentsEntryTable.INSTANCE.segmentsEntryId
		).singleColumnReference(
			SegmentsExperimentTable.INSTANCE.segmentsExperienceId,
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsExperimentPersistence;
	}

	@Override
	public SegmentsExperimentTable getTable() {
		return SegmentsExperimentTable.INSTANCE;
	}

	@Reference
	private SegmentsExperimentPersistence _segmentsExperimentPersistence;

}