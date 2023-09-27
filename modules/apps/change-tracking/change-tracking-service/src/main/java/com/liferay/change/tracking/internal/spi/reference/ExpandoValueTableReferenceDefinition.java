/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.expando.kernel.model.ExpandoColumnTable;
import com.liferay.expando.kernel.model.ExpandoRowTable;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.model.ExpandoValueTable;
import com.liferay.expando.kernel.service.persistence.ExpandoValuePersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ExpandoValueTableReferenceDefinition
	implements TableReferenceDefinition<ExpandoValueTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ExpandoValueTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ExpandoValueTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ExpandoValueTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ExpandoValueTable.INSTANCE.tableId,
			ExpandoTableTable.INSTANCE.tableId
		).singleColumnReference(
			ExpandoValueTable.INSTANCE.columnId,
			ExpandoColumnTable.INSTANCE.columnId
		).singleColumnReference(
			ExpandoValueTable.INSTANCE.rowId, ExpandoRowTable.INSTANCE.rowId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _expandoValuePersistence;
	}

	@Override
	public ExpandoValueTable getTable() {
		return ExpandoValueTable.INSTANCE;
	}

	@Reference
	private ExpandoValuePersistence _expandoValuePersistence;

}