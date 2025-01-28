/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.kernel.model.DLFileEntryTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.document.library.model.DLFileVersionPreviewTable;
import com.liferay.document.library.service.persistence.DLFileVersionPreviewPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DLFileVersionPreviewTableReferenceDefinition
	implements TableReferenceDefinition<DLFileVersionPreviewTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLFileVersionPreviewTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				DLFileVersionPreviewTable.INSTANCE,
				CTSContentTable.INSTANCE.path.like(
					DSLFunctionFactoryUtil.concat(
						new Scalar<>(StringPool.PERCENT),
						DSLFunctionFactoryUtil.castText(
							DLFileVersionPreviewTable.INSTANCE.fileEntryId),
						new Scalar<>(StringPool.SLASH),
						DSLFunctionFactoryUtil.castText(
							DLFileVersionPreviewTable.INSTANCE.fileVersionId),
						new Scalar<>(StringPool.PERCENT)))
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLFileVersionPreviewTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DLFileVersionPreviewTable.INSTANCE
		).singleColumnReference(
			DLFileVersionPreviewTable.INSTANCE.fileEntryId,
			DLFileEntryTable.INSTANCE.fileEntryId
		).singleColumnReference(
			DLFileVersionPreviewTable.INSTANCE.fileVersionId,
			DLFileVersionTable.INSTANCE.fileVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlFileVersionPreviewPersistence;
	}

	@Override
	public DLFileVersionPreviewTable getTable() {
		return DLFileVersionPreviewTable.INSTANCE;
	}

	@Reference
	private DLFileVersionPreviewPersistence _dlFileVersionPreviewPersistence;

}