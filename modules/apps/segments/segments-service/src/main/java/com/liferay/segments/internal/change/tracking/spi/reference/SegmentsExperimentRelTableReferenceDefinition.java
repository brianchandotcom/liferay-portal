/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsExperienceTable;
import com.liferay.segments.model.SegmentsExperimentRelTable;
import com.liferay.segments.model.SegmentsExperimentTable;
import com.liferay.segments.service.persistence.SegmentsExperimentRelPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsExperimentRelTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsExperimentRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsExperimentRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsExperimentRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SegmentsExperimentRelTable.INSTANCE
		).singleColumnReference(
			SegmentsExperimentRelTable.INSTANCE.segmentsExperienceId,
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId
		).singleColumnReference(
			SegmentsExperimentRelTable.INSTANCE.segmentsExperimentId,
			SegmentsExperimentTable.INSTANCE.segmentsExperimentId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsExperimentRelPersistence;
	}

	@Override
	public SegmentsExperimentRelTable getTable() {
		return SegmentsExperimentRelTable.INSTANCE;
	}

	@Reference
	private SegmentsExperimentRelPersistence _segmentsExperimentRelPersistence;

}