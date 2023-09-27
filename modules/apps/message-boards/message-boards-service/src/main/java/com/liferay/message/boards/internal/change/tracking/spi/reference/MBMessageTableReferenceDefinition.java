/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageTable;
import com.liferay.message.boards.model.MBThreadTable;
import com.liferay.message.boards.service.persistence.MBMessagePersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBMessageTableReferenceDefinition
	implements TableReferenceDefinition<MBMessageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBMessageTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			MBMessageTable.INSTANCE.messageId, MBDiscussion.class
		).assetEntryReference(
			MBMessageTable.INSTANCE.messageId, MBMessage.class
		).resourcePermissionReference(
			MBMessageTable.INSTANCE.messageId, MBMessage.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DLFileEntryTable.INSTANCE
			).innerJoinON(
				MBMessageTable.INSTANCE,
				MBMessageTable.INSTANCE.companyId.eq(
					DLFileEntryTable.INSTANCE.companyId
				).and(
					MBMessageTable.INSTANCE.groupId.eq(
						DLFileEntryTable.INSTANCE.groupId)
				).and(
					MBMessageTable.INSTANCE.messageId.eq(
						DLFileEntryTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					DLFileEntryTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(MBMessage.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				MBMessageTable.INSTANCE,
				MBMessageTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					MBMessageTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					MBMessageTable.INSTANCE.messageId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						MBDiscussion.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				WorkflowInstanceLinkTable.INSTANCE
			).innerJoinON(
				MBMessageTable.INSTANCE,
				MBMessageTable.INSTANCE.companyId.eq(
					WorkflowInstanceLinkTable.INSTANCE.companyId
				).and(
					MBMessageTable.INSTANCE.groupId.eq(
						WorkflowInstanceLinkTable.INSTANCE.groupId)
				).and(
					MBMessageTable.INSTANCE.messageId.eq(
						WorkflowInstanceLinkTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					WorkflowInstanceLinkTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(MBMessage.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBMessageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBMessageTable.INSTANCE
		).singleColumnReference(
			MBMessageTable.INSTANCE.threadId, MBThreadTable.INSTANCE.threadId
		).parentColumnReference(
			MBMessageTable.INSTANCE.messageId,
			MBMessageTable.INSTANCE.parentMessageId
		).parentColumnReference(
			MBMessageTable.INSTANCE.messageId,
			MBMessageTable.INSTANCE.rootMessageId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbMessagePersistence;
	}

	@Override
	public MBMessageTable getTable() {
		return MBMessageTable.INSTANCE;
	}

	@Reference
	private MBMessagePersistence _mbMessagePersistence;

}