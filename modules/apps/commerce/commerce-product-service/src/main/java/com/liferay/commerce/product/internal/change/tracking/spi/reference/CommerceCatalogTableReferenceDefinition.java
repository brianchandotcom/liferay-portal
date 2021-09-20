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

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceCatalogTable;
import com.liferay.commerce.product.service.persistence.CommerceCatalogPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CommerceCatalogTableReferenceDefinition
	implements TableReferenceDefinition<CommerceCatalogTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommerceCatalogTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			CommerceCatalogTable.INSTANCE.commerceCatalogId,
			CommerceCatalog.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommerceCatalogTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommerceCatalogTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceCatalogPersistence;
	}

	@Override
	public CommerceCatalogTable getTable() {
		return CommerceCatalogTable.INSTANCE;
	}

	@Reference
	private CommerceCatalogPersistence _commerceCatalogPersistence;

}