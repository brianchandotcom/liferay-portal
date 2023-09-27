/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBCategoryTable;
import com.liferay.message.boards.model.MBMailingListTable;
import com.liferay.message.boards.service.persistence.MBMailingListPersistence;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBMailingListTableReferenceDefinition
	implements TableReferenceDefinition<MBMailingListTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBMailingListTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBMailingListTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBMailingListTable.INSTANCE
		).singleColumnReference(
			MBMailingListTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			MBMailingListTable.INSTANCE.categoryId,
			MBCategoryTable.INSTANCE.categoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbMailingListPersistence;
	}

	@Override
	public MBMailingListTable getTable() {
		return MBMailingListTable.INSTANCE;
	}

	@Reference
	private MBMailingListPersistence _mbMailingListPersistence;

}