/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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