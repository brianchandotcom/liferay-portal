/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = TableReferenceDefinition.class)
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