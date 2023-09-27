/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBThreadFlagTable;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.service.persistence.MBThreadFlagPersistence;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBThreadFlagTableReferenceDefinition
	implements TableReferenceDefinition<MBThreadFlagTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBThreadFlagTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBThreadFlagTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBThreadFlagTable.INSTANCE
		).singleColumnReference(
			MBThreadFlagTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			MBThreadFlagTable.INSTANCE.threadId, MBThreadTable.INSTANCE.threadId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbThreadFlagPersistence;
	}

	@Override
	public MBThreadFlagTable getTable() {
		return MBThreadFlagTable.INSTANCE;
	}

	@Reference
	private MBThreadFlagPersistence _mbThreadFlagPersistence;

}