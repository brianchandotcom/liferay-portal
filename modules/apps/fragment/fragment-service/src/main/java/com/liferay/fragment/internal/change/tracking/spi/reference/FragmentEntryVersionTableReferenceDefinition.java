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