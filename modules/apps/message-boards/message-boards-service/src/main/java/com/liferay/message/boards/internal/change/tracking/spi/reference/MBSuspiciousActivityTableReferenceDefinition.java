/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBMessageTable;
import com.liferay.message.boards.model.MBSuspiciousActivityTable;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.service.persistence.MBSuspiciousActivityPersistence;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = TableReferenceDefinition.class)
public class MBSuspiciousActivityTableReferenceDefinition
	implements TableReferenceDefinition<MBSuspiciousActivityTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBSuspiciousActivityTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBSuspiciousActivityTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBSuspiciousActivityTable.INSTANCE
		).singleColumnReference(
			MBSuspiciousActivityTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).singleColumnReference(
			MBSuspiciousActivityTable.INSTANCE.messageId,
			MBMessageTable.INSTANCE.messageId
		).singleColumnReference(
			MBSuspiciousActivityTable.INSTANCE.threadId,
			MBThreadTable.INSTANCE.threadId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbSuspiciousActivityPersistence;
	}

	@Override
	public MBSuspiciousActivityTable getTable() {
		return MBSuspiciousActivityTable.INSTANCE;
	}

	@Reference
	private MBSuspiciousActivityPersistence _mbSuspiciousActivityPersistence;

}