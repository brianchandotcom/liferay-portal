/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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