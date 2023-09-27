/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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