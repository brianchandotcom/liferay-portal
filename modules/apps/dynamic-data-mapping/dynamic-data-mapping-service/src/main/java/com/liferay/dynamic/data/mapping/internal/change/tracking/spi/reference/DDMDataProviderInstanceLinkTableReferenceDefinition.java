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

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMDataProviderInstanceLinkPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMDataProviderInstanceLinkTableReferenceDefinition
	implements TableReferenceDefinition<DDMDataProviderInstanceLinkTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMDataProviderInstanceLinkTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMDataProviderInstanceLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			DDMDataProviderInstanceLinkTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMDataProviderInstanceLinkTable.INSTANCE.dataProviderInstanceId,
			DDMDataProviderInstanceTable.INSTANCE.dataProviderInstanceId
		).singleColumnReference(
			DDMDataProviderInstanceLinkTable.INSTANCE.structureId,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmDataProviderInstanceLinkPersistence;
	}

	@Override
	public DDMDataProviderInstanceLinkTable getTable() {
		return DDMDataProviderInstanceLinkTable.INSTANCE;
	}

	@Reference
	private DDMDataProviderInstanceLinkPersistence
		_ddmDataProviderInstanceLinkPersistence;

}