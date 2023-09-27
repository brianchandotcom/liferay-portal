/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.content.model.DLContentTable;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLFolderTable;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.trash.model.TrashVersionTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileVersionTableReferenceDefinition
	implements TableReferenceDefinition<DLFileVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileVersionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DSLFunctionFactoryUtil.concat(
					DLFileVersionTable.INSTANCE.version,
					new Scalar<>(StringPool.TILDE),
					DLFileVersionTable.INSTANCE.storeUUID
				).eq(
					CTSContentTable.INSTANCE.version
				)
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
				).and(
					DLFileEntryTable.INSTANCE.fileEntryId.eq(
						DLFileVersionTable.INSTANCE.fileEntryId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileEntryId.eq(
					FriendlyURLEntryTable.INSTANCE.classPK)
			).innerJoinON(
				DLFileEntryTable.INSTANCE,
				DLFileEntryTable.INSTANCE.groupId.eq(
					FriendlyURLEntryTable.INSTANCE.groupId
				).and(
					DLFileVersionTable.INSTANCE.fileEntryId.eq(
						DLFileEntryTable.INSTANCE.fileEntryId)
				).and(
					DLFileEntryTable.INSTANCE.fileEntryId.eq(
						FriendlyURLEntryTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					FriendlyURLEntryTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(FileEntry.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DSLFunctionFactoryUtil.concat(
					DLFileVersionTable.INSTANCE.version,
					new Scalar<>(StringPool.TILDE),
					DLFileVersionTable.INSTANCE.storeUUID
				).eq(
					CTSContentTable.INSTANCE.version
				)
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
				).and(
					DLFileEntryTable.INSTANCE.fileEntryId.eq(
						DLFileVersionTable.INSTANCE.fileEntryId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DLContentTable.INSTANCE
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.version.eq(
					DLContentTable.INSTANCE.version)
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
				).and(
					DLFileEntryTable.INSTANCE.fileEntryId.eq(
						DLFileVersionTable.INSTANCE.fileEntryId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				TrashVersionTable.INSTANCE
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileVersionId.eq(
					TrashVersionTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					TrashVersionTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						DLFileEntry.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileVersionTable.INSTANCE
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.folderId,
			DLFolderTable.INSTANCE.folderId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).singleColumnReference(
			DLFileVersionTable.INSTANCE.fileEntryTypeId,
			DLFileEntryTypeTable.INSTANCE.fileEntryTypeId
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