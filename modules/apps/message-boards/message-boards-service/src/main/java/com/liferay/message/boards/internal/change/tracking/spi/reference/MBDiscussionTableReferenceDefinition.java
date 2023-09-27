/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBDiscussionTable;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.service.persistence.MBDiscussionPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBDiscussionTableReferenceDefinition
	implements TableReferenceDefinition<MBDiscussionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBDiscussionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			MBDiscussionTable.INSTANCE.threadId,
			MBThreadTable.INSTANCE.threadId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBDiscussionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBDiscussionTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbDiscussionPersistence;
	}

	@Override
	public MBDiscussionTable getTable() {
		return MBDiscussionTable.INSTANCE;
	}

	@Reference
	private MBDiscussionPersistence _mbDiscussionPersistence;

}