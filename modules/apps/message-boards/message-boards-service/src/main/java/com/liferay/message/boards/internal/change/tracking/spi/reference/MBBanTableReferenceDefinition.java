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
import com.liferay.message.boards.model.MBBanTable;
import com.liferay.message.boards.service.persistence.MBBanPersistence;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class MBBanTableReferenceDefinition
	implements TableReferenceDefinition<MBBanTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<MBBanTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<MBBanTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			MBBanTable.INSTANCE
		).singleColumnReference(
			MBBanTable.INSTANCE.banUserId, UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _mbBanPersistence;
	}

	@Override
	public MBBanTable getTable() {
		return MBBanTable.INSTANCE;
	}

	@Reference
	private MBBanPersistence _mbBanPersistence;

}