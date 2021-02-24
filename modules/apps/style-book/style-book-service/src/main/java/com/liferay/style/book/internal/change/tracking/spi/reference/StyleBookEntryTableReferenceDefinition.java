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

package com.liferay.style.book.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.style.book.model.StyleBookEntryTable;
import com.liferay.style.book.service.persistence.StyleBookEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class StyleBookEntryTableReferenceDefinition
	implements TableReferenceDefinition<StyleBookEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<StyleBookEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			StyleBookEntryTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<StyleBookEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			StyleBookEntryTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> {
				StyleBookEntryTable parentStyleBookEntryTable =
					StyleBookEntryTable.INSTANCE.as(
						"parentStyleBookEntryTable");

				return fromStep.from(
					parentStyleBookEntryTable
				).innerJoinON(
					StyleBookEntryTable.INSTANCE,
					StyleBookEntryTable.INSTANCE.headId.eq(
						parentStyleBookEntryTable.styleBookEntryId
					).and(
						StyleBookEntryTable.INSTANCE.head.eq(false)
					)
				);
			}
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _styleBookEntryPersistence;
	}

	@Override
	public StyleBookEntryTable getTable() {
		return StyleBookEntryTable.INSTANCE;
	}

	@Reference
	private StyleBookEntryPersistence _styleBookEntryPersistence;

}