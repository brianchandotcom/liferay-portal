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