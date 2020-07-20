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
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.fragment.model.FragmentCollectionTable;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentCompositionTable;
import com.liferay.fragment.service.persistence.FragmentCompositionPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FragmentCompositionTableReferenceDefinition
	implements TableReferenceDefinition<FragmentCompositionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FragmentCompositionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			FragmentCompositionTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).resourcePermissionReference(
			FragmentCompositionTable.INSTANCE.fragmentCompositionId,
			FragmentComposition.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FragmentCompositionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			FragmentCompositionTable.INSTANCE
		).singleColumnReference(
			FragmentCompositionTable.INSTANCE.fragmentCollectionId,
			FragmentCollectionTable.INSTANCE.fragmentCollectionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fragmentCompositionPersistence;
	}

	@Override
	public FragmentCompositionTable getTable() {
		return FragmentCompositionTable.INSTANCE;
	}

	@Reference
	private FragmentCompositionPersistence _fragmentCompositionPersistence;

}