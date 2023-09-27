/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.fragment.model.FragmentEntryLink;
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

		childTableReferenceInfoBuilder.systemEventReference(
			FragmentEntryLinkTable.INSTANCE.fragmentEntryLinkId,
			FragmentEntryLink.class);
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