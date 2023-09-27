/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.fragment.model.FragmentCollectionTable;
import com.liferay.fragment.model.FragmentEntryTable;
import com.liferay.fragment.model.FragmentEntryVersionTable;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FragmentEntryVersionTableReferenceDefinition
	implements TableReferenceDefinition<FragmentEntryVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FragmentEntryVersionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FragmentEntryVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			FragmentEntryVersionTable.INSTANCE
		).singleColumnReference(
			FragmentEntryVersionTable.INSTANCE.fragmentCollectionId,
			FragmentCollectionTable.INSTANCE.fragmentCollectionId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FragmentEntryTable.INSTANCE
			).innerJoinON(
				FragmentEntryVersionTable.INSTANCE,
				FragmentEntryVersionTable.INSTANCE.fragmentEntryId.eq(
					FragmentEntryTable.INSTANCE.fragmentEntryId
				).and(
					FragmentEntryTable.INSTANCE.head.eq(true)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fragmentEntryVersionPersistence;
	}

	@Override
	public FragmentEntryVersionTable getTable() {
		return FragmentEntryVersionTable.INSTANCE;
	}

	@Reference
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

}