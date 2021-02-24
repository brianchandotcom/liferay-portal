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
import com.liferay.style.book.model.StyleBookEntryVersionTable;
import com.liferay.style.book.service.persistence.StyleBookEntryVersionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class StyleBookEntryVersionTableReferenceDefinition
	implements TableReferenceDefinition<StyleBookEntryVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<StyleBookEntryVersionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			StyleBookEntryVersionTable.INSTANCE.previewFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<StyleBookEntryVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			StyleBookEntryVersionTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				StyleBookEntryTable.INSTANCE
			).innerJoinON(
				StyleBookEntryVersionTable.INSTANCE,
				StyleBookEntryVersionTable.INSTANCE.styleBookEntryId.eq(
					StyleBookEntryTable.INSTANCE.styleBookEntryId
				).and(
					StyleBookEntryTable.INSTANCE.head.eq(true)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _styleBookEntryVersionPersistence;
	}

	@Override
	public StyleBookEntryVersionTable getTable() {
		return StyleBookEntryVersionTable.INSTANCE;
	}

	@Reference
	private StyleBookEntryVersionPersistence _styleBookEntryVersionPersistence;

}