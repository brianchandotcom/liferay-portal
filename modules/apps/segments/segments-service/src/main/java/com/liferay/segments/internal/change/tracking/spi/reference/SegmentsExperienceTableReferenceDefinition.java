/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceTable;
import com.liferay.segments.model.SegmentsExperimentTable;
import com.liferay.segments.service.persistence.SegmentsExperiencePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SegmentsExperienceTableReferenceDefinition
	implements TableReferenceDefinition<SegmentsExperienceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SegmentsExperienceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				SegmentsExperimentTable.INSTANCE
			).innerJoinON(
				SegmentsExperienceTable.INSTANCE,
				SegmentsExperienceTable.INSTANCE.segmentsExperienceId.eq(
					SegmentsExperimentTable.INSTANCE.segmentsExperienceId
				).and(
					SegmentsExperienceTable.INSTANCE.plid.eq(
						SegmentsExperimentTable.INSTANCE.plid)
				)
			)
		).resourcePermissionReference(
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId,
			SegmentsExperience.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SegmentsExperienceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SegmentsExperienceTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				SegmentsExperienceTable.INSTANCE,
				SegmentsExperienceTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					SegmentsExperienceTable.INSTANCE.plid.eq(
						LayoutTable.INSTANCE.plid)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				SegmentsExperienceTable.INSTANCE,
				SegmentsExperienceTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					SegmentsExperienceTable.INSTANCE.plid.eq(
						LayoutTable.INSTANCE.classPK)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _segmentsExperiencePersistence;
	}

	@Override
	public SegmentsExperienceTable getTable() {
		return SegmentsExperienceTable.INSTANCE;
	}

	@Reference
	private SegmentsExperiencePersistence _segmentsExperiencePersistence;

}