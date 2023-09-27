/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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