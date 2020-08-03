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

package com.liferay.message.boards.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBCategoryTable;
import com.liferay.message.boards.service.persistence.MBCategoryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.ratings.kernel.model.RatingsStatsTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBCategoryTableReferenceDefinition
	implements TableReferenceDefinition<MBCategoryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBCategoryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			MBCategoryTable.INSTANCE.categoryId, MBCategory.class
		).classNameReference(
			MBCategoryTable.INSTANCE.categoryId,
			RatingsStatsTable.INSTANCE.classPK, MBCategory.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBCategoryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBCategoryTable.INSTANCE
		).parentColumnReference(
			MBCategoryTable.INSTANCE.categoryId,
			MBCategoryTable.INSTANCE.parentCategoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbCategoryPersistence;
	}

	@Override
	public MBCategoryTable getTable() {
		return MBCategoryTable.INSTANCE;
	}

	@Reference
	private MBCategoryPersistence _mbCategoryPersistence;

}