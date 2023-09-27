/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.layout.seo.model.LayoutSEOEntryTable;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryPersistence;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutSEOEntryTableReferenceDefinition
	implements TableReferenceDefinition<LayoutSEOEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutSEOEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			LayoutSEOEntryTable.INSTANCE.openGraphImageFileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutSEOEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutSEOEntryTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSEOEntryTable.INSTANCE,
				LayoutSEOEntryTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					LayoutSEOEntryTable.INSTANCE.layoutId.eq(
						LayoutTable.INSTANCE.layoutId)
				).and(
					LayoutSEOEntryTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutSEOEntryPersistence;
	}

	@Override
	public LayoutSEOEntryTable getTable() {
		return LayoutSEOEntryTable.INSTANCE;
	}

	@Reference
	private LayoutSEOEntryPersistence _layoutSEOEntryPersistence;

}