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