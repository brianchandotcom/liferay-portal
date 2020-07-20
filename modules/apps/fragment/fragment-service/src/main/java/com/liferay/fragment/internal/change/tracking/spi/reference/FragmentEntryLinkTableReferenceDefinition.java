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

package com.liferay.fragment.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.fragment.model.FragmentEntryLinkTable;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.segments.model.SegmentsExperienceTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FragmentEntryLinkTableReferenceDefinition
	implements TableReferenceDefinition<FragmentEntryLinkTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FragmentEntryLinkTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FragmentEntryLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			FragmentEntryLinkTable.INSTANCE
		).singleColumnReference(
			FragmentEntryLinkTable.INSTANCE.segmentsExperienceId,
			SegmentsExperienceTable.INSTANCE.segmentsExperienceId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				FragmentEntryLinkTable.INSTANCE,
				FragmentEntryLinkTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					FragmentEntryLinkTable.INSTANCE.plid.eq(
						LayoutTable.INSTANCE.plid)
				)
			)
		).parentColumnReference(
			FragmentEntryLinkTable.INSTANCE.fragmentEntryLinkId,
			FragmentEntryLinkTable.INSTANCE.originalFragmentEntryLinkId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fragmentEntryLinkPersistence;
	}

	@Override
	public FragmentEntryLinkTable getTable() {
		return FragmentEntryLinkTable.INSTANCE;
	}

	@Reference
	private FragmentEntryLinkPersistence _fragmentEntryLinkPersistence;

}