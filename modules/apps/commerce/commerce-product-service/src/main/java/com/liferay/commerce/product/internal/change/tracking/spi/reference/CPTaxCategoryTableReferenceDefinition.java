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
import com.liferay.commerce.product.model.CPTaxCategoryTable;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CPTaxCategoryTableReferenceDefinition
	implements TableReferenceDefinition<CPTaxCategoryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPTaxCategoryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPTaxCategoryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPTaxCategoryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpTaxCategoryPersistence;
	}

	@Override
	public CPTaxCategoryTable getTable() {
		return CPTaxCategoryTable.INSTANCE;
	}

	@Reference
	private CPTaxCategoryPersistence _cpTaxCategoryPersistence;

}