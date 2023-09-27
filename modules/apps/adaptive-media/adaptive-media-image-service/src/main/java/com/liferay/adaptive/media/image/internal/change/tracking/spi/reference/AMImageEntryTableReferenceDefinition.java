/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.change.tracking.spi.reference;

import com.liferay.adaptive.media.image.model.AMImageEntryTable;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.change.tracking.store.model.CTSContentTable;
import com.liferay.document.library.kernel.model.DLFileVersionTable;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = TableReferenceDefinition.class)
public class AMImageEntryTableReferenceDefinition
	implements TableReferenceDefinition<AMImageEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AMImageEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				CTSContentTable.INSTANCE
			).innerJoinON(
				AMImageEntryTable.INSTANCE,
				CTSContentTable.INSTANCE.repositoryId.eq(0L)
			).innerJoinON(
				DLFileVersionTable.INSTANCE,
				DLFileVersionTable.INSTANCE.fileVersionId.eq(
					AMImageEntryTable.INSTANCE.fileVersionId
				).and(
					CTSContentTable.INSTANCE.path.eq(
						DSLFunctionFactoryUtil.concat(
							new Scalar<>("adaptive"),
							new Scalar<>(StringPool.SLASH),
							AMImageEntryTable.INSTANCE.configurationUuid,
							new Scalar<>(StringPool.SLASH),
							DSLFunctionFactoryUtil.castText(
								DLFileVersionTable.INSTANCE.groupId),
							new Scalar<>(StringPool.SLASH),
							DSLFunctionFactoryUtil.castText(
								DLFileVersionTable.INSTANCE.repositoryId),
							new Scalar<>(StringPool.SLASH),
							DSLFunctionFactoryUtil.castText(
								DLFileVersionTable.INSTANCE.fileEntryId),
							new Scalar<>(StringPool.SLASH),
							DSLFunctionFactoryUtil.castText(
								DLFileVersionTable.INSTANCE.fileVersionId),
							new Scalar<>(StringPool.SLASH)))
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AMImageEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			AMImageEntryTable.INSTANCE.fileVersionId,
			DLFileVersionTable.INSTANCE.fileVersionId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _amImageEntryPersistence;
	}

	@Override
	public AMImageEntryTable getTable() {
		return AMImageEntryTable.INSTANCE;
	}

	@Reference
	private AMImageEntryPersistence _amImageEntryPersistence;

}