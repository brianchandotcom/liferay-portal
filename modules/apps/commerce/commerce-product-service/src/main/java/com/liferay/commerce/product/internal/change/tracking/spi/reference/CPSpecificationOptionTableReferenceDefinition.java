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
import com.liferay.commerce.product.model.CPOptionCategoryTable;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CPSpecificationOptionTable;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CPSpecificationOptionTableReferenceDefinition
	implements TableReferenceDefinition<CPSpecificationOptionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPSpecificationOptionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			CPSpecificationOptionTable.INSTANCE.CPSpecificationOptionId,
			CPSpecificationOption.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPSpecificationOptionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPSpecificationOptionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			CPSpecificationOptionTable.INSTANCE.CPOptionCategoryId,
			CPOptionCategoryTable.INSTANCE.CPOptionCategoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpSpecificationOptionPersistence;
	}

	@Override
	public CPSpecificationOptionTable getTable() {
		return CPSpecificationOptionTable.INSTANCE;
	}

	@Reference
	private CPSpecificationOptionPersistence _cpSpecificationOptionPersistence;

}