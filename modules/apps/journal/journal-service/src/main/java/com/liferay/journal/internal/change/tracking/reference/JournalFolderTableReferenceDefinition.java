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

package com.liferay.journal.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.journal.model.JournalFolderTable;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalFolderTableReferenceDefinition
	implements TableReferenceDefinition<JournalFolderTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<JournalFolderTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				JournalFolderTable.INSTANCE,
				JournalFolderTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				JournalFolderTable.INSTANCE,
				JournalFolderTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				JournalFolderTable.INSTANCE,
				JournalFolderTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				JournalFolderTable aliasJournalFolderTable =
					JournalFolderTable.INSTANCE.as("aliasJournalFolderTable");

				return fromStep.from(
					aliasJournalFolderTable
				).innerJoinON(
					JournalFolderTable.INSTANCE,
					JournalFolderTable.INSTANCE.parentFolderId.eq(
						aliasJournalFolderTable.folderId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.treePath);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.treePath);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.restrictionType);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.lastPublishDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.status);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				JournalFolderTable.INSTANCE,
				JournalFolderTable.INSTANCE.statusByUserId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.statusByUserName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			JournalFolderTable.INSTANCE.statusDate);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalFolderPersistence;
	}

	@Override
	public JournalFolderTable getTable() {
		return JournalFolderTable.INSTANCE;
	}

	@Reference
	private JournalFolderPersistence _journalFolderPersistence;

}