/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.expando.kernel.model.ExpandoRowTable;
import com.liferay.expando.kernel.model.ExpandoTableTable;
import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class ExpandoRowTableReferenceDefinition
	implements TableReferenceDefinition<ExpandoRowTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ExpandoRowTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ExpandoRowTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			ExpandoRowTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			ExpandoRowTable.INSTANCE.tableId, ExpandoTableTable.INSTANCE.tableId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _expandoRowPersistence;
	}

	@Override
	public ExpandoRowTable getTable() {
		return ExpandoRowTable.INSTANCE;
	}

	@Reference
	private ExpandoRowPersistence _expandoRowPersistence;

}