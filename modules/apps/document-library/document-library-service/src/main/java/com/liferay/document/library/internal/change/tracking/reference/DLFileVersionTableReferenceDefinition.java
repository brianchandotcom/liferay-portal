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

package com.liferay.document.library.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.content.model.DLContentTable;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.portal.kernel.model.RepositoryTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileVersionTableReferenceDefinition
	implements TableReferenceDefinition<DLFileVersionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DLFileVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DLFileVersionTable.INSTANCE
		).nonreferenceColumn(
			DLFileVersionTable.INSTANCE.uuid
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).nonreferenceColumns(
			DLFileVersionTable.INSTANCE.treePath,
			DLFileVersionTable.INSTANCE.fileName,
			DLFileVersionTable.INSTANCE.extension,
			DLFileVersionTable.INSTANCE.mimeType,
			DLFileVersionTable.INSTANCE.title,
			DLFileVersionTable.INSTANCE.description,
			DLFileVersionTable.INSTANCE.changeLog,
			DLFileVersionTable.INSTANCE.extraSettings
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
		).nonreferenceColumns(
			DLFileVersionTable.INSTANCE.version,
			DLFileVersionTable.INSTANCE.size,
			DLFileVersionTable.INSTANCE.checksum,
			DLFileVersionTable.INSTANCE.lastPublishDate,
			DLFileVersionTable.INSTANCE.status
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DLFileVersionTable.INSTANCE.statusByUserName,
			DLFileVersionTable.INSTANCE.statusDate
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.companyId.eq(
					CTSContentTable.INSTANCE.companyId
				).and(
					DLFileEntryTable.INSTANCE.repositoryId.eq(
						CTSContentTable.INSTANCE.repositoryId)
				).and(
					DLFileEntryTable.INSTANCE.folderId.eq(
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)
				).and(
					DLFileEntryTable.INSTANCE.name.eq(
						CTSContentTable.INSTANCE.path)
				)
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileEntryId.eq(
					DLFileEntryTable.INSTANCE.fileEntryId
				).and(
					DLFileVersionTable.INSTANCE.version.eq(
						CTSContentTable.INSTANCE.version)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.companyId.eq(
					CTSContentTable.INSTANCE.companyId
				).and(
					DLFileEntryTable.INSTANCE.folderId.eq(
						CTSContentTable.INSTANCE.repositoryId)
				).and(
					DLFileEntryTable.INSTANCE.name.eq(
						CTSContentTable.INSTANCE.path)
				)
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileEntryId.eq(
					DLFileEntryTable.INSTANCE.fileEntryId
				).and(
					DLFileVersionTable.INSTANCE.version.eq(
						CTSContentTable.INSTANCE.version)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DLContentTable.INSTANCE
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.companyId.eq(
					DLContentTable.INSTANCE.companyId
				).and(
					DLFileEntryTable.INSTANCE.repositoryId.eq(
						DLContentTable.INSTANCE.repositoryId)
				).and(
					DLFileEntryTable.INSTANCE.name.eq(
						DLContentTable.INSTANCE.path)
				)
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileEntryId.eq(
					DLFileEntryTable.INSTANCE.fileEntryId
				).and(
					DLFileVersionTable.INSTANCE.version.eq(
						DLContentTable.INSTANCE.version)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileVersionPersistence;
	}

	@Override
	public DLFileVersionTable getTable() {
		return DLFileVersionTable.INSTANCE;
	}

	@Reference
	private DLFileVersionPersistence _dlFileVersionPersistence;

}