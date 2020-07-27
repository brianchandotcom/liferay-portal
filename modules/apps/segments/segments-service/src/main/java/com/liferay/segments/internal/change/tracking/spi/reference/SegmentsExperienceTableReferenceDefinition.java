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
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.Layout;
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
					SegmentsExperienceTable.INSTANCE.classNameId.eq(
						SegmentsExperimentTable.INSTANCE.classNameId)
				).and(
					SegmentsExperienceTable.INSTANCE.classPK.eq(
						SegmentsExperimentTable.INSTANCE.classPK)
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
					SegmentsExperienceTable.INSTANCE.classPK.eq(
						LayoutTable.INSTANCE.plid)
				).and(
					LayoutTable.INSTANCE.classPK.eq(0L)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					SegmentsExperienceTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Layout.class.getName())
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
					SegmentsExperienceTable.INSTANCE.classPK.eq(
						LayoutTable.INSTANCE.classPK)
				).and(
					SegmentsExperienceTable.INSTANCE.classNameId.eq(
						LayoutTable.INSTANCE.classNameId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					SegmentsExperienceTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(Layout.class.getName())
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