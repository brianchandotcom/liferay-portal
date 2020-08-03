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