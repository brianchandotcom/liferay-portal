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
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLinkTable;
import com.liferay.fragment.model.FragmentEntryTable;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class FragmentEntryTableReferenceDefinition
	implements TableReferenceDefinition<FragmentEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<FragmentEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			FragmentEntryTable.INSTANCE.fragmentEntryId,
			FragmentEntryLinkTable.INSTANCE.fragmentEntryId
		).singleColumnReference(
			FragmentEntryTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).resourcePermissionReference(
			FragmentEntryTable.INSTANCE.fragmentEntryId, FragmentEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<FragmentEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			FragmentEntryTable.INSTANCE
		).singleColumnReference(
			FragmentEntryTable.INSTANCE.fragmentCollectionId,
			FragmentCollectionTable.INSTANCE.fragmentCollectionId
		).parentColumnReference(
			FragmentEntryTable.INSTANCE.fragmentEntryId,
			FragmentEntryTable.INSTANCE.headId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _fragmentEntryPersistence;
	}

	@Override
	public FragmentEntryTable getTable() {
		return FragmentEntryTable.INSTANCE;
	}

	@Reference
	private FragmentEntryPersistence _fragmentEntryPersistence;

}