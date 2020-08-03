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
import com.liferay.message.boards.model.MBMessageTable;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.service.persistence.MBThreadPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBThreadTableReferenceDefinition
	implements TableReferenceDefinition<MBThreadTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBThreadTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			MBThreadTable.INSTANCE.rootMessageId,
			MBMessageTable.INSTANCE.messageId
		).assetEntryReference(
			MBThreadTable.INSTANCE.threadId, MBThread.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBThreadTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(MBThreadTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbThreadPersistence;
	}

	@Override
	public MBThreadTable getTable() {
		return MBThreadTable.INSTANCE;
	}

	@Reference
	private MBThreadPersistence _mbThreadPersistence;

}