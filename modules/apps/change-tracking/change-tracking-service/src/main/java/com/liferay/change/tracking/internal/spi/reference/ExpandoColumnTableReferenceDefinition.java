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

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnTable;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.service.persistence.ExpandoColumnPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ExpandoColumnTableReferenceDefinition
	implements TableReferenceDefinition<ExpandoColumnTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ExpandoColumnTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			ExpandoColumnTable.INSTANCE.columnId, ExpandoColumn.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ExpandoColumnTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ExpandoColumnTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ExpandoColumnTable.INSTANCE.tableId,
			ExpandoTableTable.INSTANCE.tableId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _expandoColumnPersistence;
	}

	@Override
	public ExpandoColumnTable getTable() {
		return ExpandoColumnTable.INSTANCE;
	}

	@Reference
	private ExpandoColumnPersistence _expandoColumnPersistence;

}